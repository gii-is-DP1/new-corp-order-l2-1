package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.PlotRequest;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.PlotResponse;

import static us.lsi.dp1.newcorporder.match.Match.MAX_SHARES_IN_HAND;
import static us.lsi.dp1.newcorporder.match.Match.SHARES_IN_OPEN_DISPLAY;

@Getter
public class PlotTurn extends Turn {

    public enum State implements TurnState {SELECTING_FIRST_SHARE, SELECTING_SECOND_SHARE, DISCARDING_SHARES_FROM_HAND, NONE}

    private State state = State.SELECTING_FIRST_SHARE;

    public PlotTurn(Match match) {
        super(Action.PLOT, match);
    }

    @Builder
    public PlotTurn(Match match, State currentState) {
        this(match);
        this.state = currentState;
    }

    @Override
    public ConsultantType getChosenConsultant() {
        return null;
    }

    @Override
    public PlotResponse onPlotRequest(PlotRequest plotRequest) {
        Preconditions.checkState(state == State.SELECTING_FIRST_SHARE
                                 || state == State.SELECTING_SECOND_SHARE,
            "illegal turn state");

        Conglomerate share = this.takeShare(plotRequest);
        turnSystem.getCurrentPlayer().addShareToHand(share);
        state = this.getNextState();

        if (state == State.NONE) {
            this.endTurn();
        }

        return PlotResponse.builder()
            .shareTaken(share)
            .nextState(state)
            .openDisplay(match.getGeneralSupply().getOpenDisplay())
            .build();
    }

    private Conglomerate takeShare(PlotRequest plotRequest) {
        return switch (plotRequest.getSource()) {
            case DECK -> match.getGeneralSupply().takeConglomerateShareFromDeck();
            case OPEN_DISPLAY -> {
                Preconditions.checkArgument(plotRequest.getConglomerate() != null,
                    "conglomerate must be specified to take a share from the open display");

                match.getGeneralSupply().takeConglomerateShareFromOpenDisplay(plotRequest.getConglomerate());
                yield plotRequest.getConglomerate();
            }
        };
    }

    private State getNextState() {
        // if just took the first share, the next turn state is SELECTING_SECOND_SHARE
        if (state == State.SELECTING_FIRST_SHARE) {
            return State.SELECTING_SECOND_SHARE;
        }

        // if the player has more than MAX_SHARES_IN_HAND shares in his hand, the next turn state is DISCARD_SHARES_FROM_HAND
        if (turnSystem.getCurrentPlayer().getHand().size() > MAX_SHARES_IN_HAND) {
            return State.DISCARDING_SHARES_FROM_HAND;
        }

        return State.NONE;
    }

    @Override
    public DiscardShareResponse onDiscardShareRequest(DiscardShareRequest discardShareRequest) {
        Preconditions.checkState(state == State.DISCARDING_SHARES_FROM_HAND,
            "cannot discard a share on your turn state");

        DiscardShareResponse response = super.onDiscardShareRequest(discardShareRequest);
        this.endTurn();
        response.setNextState(this.state);

        return response;
    }

    @VisibleForTesting
    void endTurn() {
        // now, refill the missing shares in the open display
        int currentSharesInOpenDisplay = match.getGeneralSupply().getOpenDisplay().size();
        if (currentSharesInOpenDisplay < SHARES_IN_OPEN_DISPLAY) {
            try {
                match.getGeneralSupply().revealConglomerateSharesToOpenDisplay(SHARES_IN_OPEN_DISPLAY - currentSharesInOpenDisplay);
            } catch (IllegalStateException e) {
                turnSystem.activateFinalRound();
            }
        }

        turnSystem.getCurrentPlayer().addTimePlotted();
        state = this.getNextState();
        turnSystem.passTurn();
    }

    @VisibleForTesting
    void setState(State state) {
        this.state = state;
    }
}
