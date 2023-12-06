package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.PlotRequest;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.PlotResponse;

import static us.lsi.dp1.newcorporder.match.Match.MAX_SHARES_IN_HAND;
import static us.lsi.dp1.newcorporder.match.Match.SHARES_IN_OPEN_DISPLAY;

public class PlotTurn extends Turn {

    public enum State implements TurnState {SELECTING_FIRST_SHARE, SELECTING_SECOND_SHARE, DISCARDING_SHARES_FROM_HAND, NONE}

    private State currentState = State.SELECTING_FIRST_SHARE;

    public PlotTurn(Match match) {
        super(match);
    }

    @Override
    public PlotResponse onPlotRequest(PlotRequest plotRequest) {
        Preconditions.checkState(currentState == State.SELECTING_FIRST_SHARE
                                 || currentState == State.SELECTING_SECOND_SHARE,
            "illegal turn state");

        Conglomerate share = this.takeShare(plotRequest);
        turnSystem.getCurrentPlayer().addShareToHand(share);
        currentState = this.getNextState();

        if (currentState == State.NONE) {
            this.endTurn();
        }

        return PlotResponse.builder()
            .shareTaken(share)
            .nextState(currentState)
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
        if (currentState == State.SELECTING_FIRST_SHARE) {
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
        Preconditions.checkState(currentState == State.DISCARDING_SHARES_FROM_HAND,
            "cannot discard a share on your turn state");

        DiscardShareResponse response = super.onDiscardShareRequest(discardShareRequest);
        this.endTurn();
        response.setNextState(this.currentState);

        return response;
    }

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

        currentState = this.getNextState();
        turnSystem.passTurn();
    }

    // for testing purposes
    void setCurrentState(State state) {
        this.currentState = state;
    }
}
