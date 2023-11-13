package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.*;
import us.lsi.dp1.newcorporder.match.payload.response.*;

import static us.lsi.dp1.newcorporder.match.Match.MAX_SHARES_IN_HAND;

public abstract class Turn {

    protected Match match;
    protected TurnSystem turnSystem;

    public Turn(Match match) {
        this.match = match;
        this.turnSystem = match.getTurnSystem();
    }

    public TakeShareResponse onTakeShareRequest(TakeShareRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public DiscardShareResponse onDiscardShareRequest(DiscardShareRequest request) {
        Preconditions.checkState(turnSystem.getCurrentPlayer().getHand().size() - request.getSharesToDiscard().size() == MAX_SHARES_IN_HAND,
            "you have to discard the necessary number of shares to have exactly %d left on your hand",
            MAX_SHARES_IN_HAND);

        // for each given conglomerate and number of shares, discard them from the player's hand
        request.getSharesToDiscard().forEachEntry(turnSystem.getCurrentPlayer()::discardSharesFromHand);
        this.endTurn();

        return DiscardShareResponse.builder()
            .openDisplay(match.getGeneralSupply().getOpenDisplay())
            .nextState(this.getCurrentState())
            .build();
    }

    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public InfiltrateResponse onInfiltrateRequest(InfiltrateRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onTakeConsultantRequest(TakeConsultantRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public TakeOverResponse onTakeOverRequest(TakeOverRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public CompanyAbilityResponse onCompanyAbilityRequest(CompanyAbilityRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public abstract void endTurn();

    public abstract TurnState getCurrentState();
}
