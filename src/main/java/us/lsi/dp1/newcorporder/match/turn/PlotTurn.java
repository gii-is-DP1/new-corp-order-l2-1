package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeShareRequest;

import static us.lsi.dp1.newcorporder.match.Match.MAX_SHARES_IN_HAND;
import static us.lsi.dp1.newcorporder.match.Match.SHARES_IN_OPEN_DISPLAY;

public class PlotTurn extends Turn {

    private enum State {SELECTING_FIRST_SHARE, SELECTING_SECOND_SHARE, DISCARDING_SHARES_FROM_HAND}

    private State currentState = State.SELECTING_FIRST_SHARE;

    public PlotTurn(Match match) {
        super(match);
    }

    @Override
    public void onTakeShareRequest(TakeShareRequest takeShareRequest) {
        Preconditions.checkState(currentState == State.SELECTING_FIRST_SHARE
                || currentState == State.SELECTING_SECOND_SHARE,
            "illegal turn state");

        Conglomerate share = switch (takeShareRequest.getSource()) {
            case DECK -> match.getGeneralSupply().takeConglomerateShareFromDeck();
            case OPEN_DISPLAY -> {
                Preconditions.checkNotNull(takeShareRequest.getConglomerate(),
                    "conglomerate must be specified to take a share from the open display");

                match.getGeneralSupply().takeConglomerateShareFromOpenDisplay(takeShareRequest.getConglomerate());
                yield takeShareRequest.getConglomerate();
            }
        };

        turnSystem.getCurrentPlayer().addShareToHand(share);
        this.nextState();
    }

    @Override
    public void onDiscardShareRequest(DiscardShareRequest discardShareRequest) {
        Preconditions.checkState(currentState == State.DISCARDING_SHARES_FROM_HAND,
            "cannot discard a share on your turn state");
        Preconditions.checkState(turnSystem.getCurrentPlayer().getHand().size() - discardShareRequest.getSharesToDiscard().size() == MAX_SHARES_IN_HAND,
            "you have to discard the necessary number of shares to have exactly %d left on your hand",
            MAX_SHARES_IN_HAND);

        // for each given conglomerate and number of shares, discard them from the player's hand
        discardShareRequest.getSharesToDiscard().forEachEntry(turnSystem.getCurrentPlayer()::discardSharesFromHand);

        this.nextState();
    }

    private void nextState() {
        // if just took the first share, the next turn state is SELECTING_SECOND_SHARE
        if (currentState == State.SELECTING_FIRST_SHARE) {
            this.currentState = State.SELECTING_SECOND_SHARE;
            return;
        }

        // if the player has more than MAX_SHARES_IN_HAND shares in his hand, the next turn state is DISCARD_SHARES_FROM_HAND
        if (turnSystem.getCurrentPlayer().getHand().size() > MAX_SHARES_IN_HAND) {
            currentState = State.DISCARDING_SHARES_FROM_HAND;
            return;
        }

        // now, refill the missing shares in the open display
        int currentSharesInOpenDisplay = match.getGeneralSupply().getOpenDisplay().size();
        if (currentSharesInOpenDisplay < SHARES_IN_OPEN_DISPLAY) {
            try {
                match.getGeneralSupply().revealConglomerateSharesToOpenDisplay(SHARES_IN_OPEN_DISPLAY - currentSharesInOpenDisplay);
            } catch (IllegalStateException e) {
                this.turnSystem.setLastPlayerBeforeMatchEnds(this.turnSystem.getCurrentPlayer());
            }
        }

        turnSystem.passTurn();
    }

}
