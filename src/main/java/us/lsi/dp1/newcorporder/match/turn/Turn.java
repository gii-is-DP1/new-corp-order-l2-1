package us.lsi.dp1.newcorporder.match.turn;

import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.*;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.TakeShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;

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
        throw new IllegalStateException("invalid move for the current action");
    }

    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onTakeOverRequest(TakeOverRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onCompanyAbilityRequest(CompanyAbilityRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onInfiltrateRequest(InfiltrateRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onTakeConsultantRequest(TakeConsultantRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

}
