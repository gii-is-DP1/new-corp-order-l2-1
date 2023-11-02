package us.lsi.dp1.newcorporder.match.turn;

import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.CompanyAbilityRequest;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

public abstract class Turn {

    protected Match match;
    protected TurnSystem turnSystem;

    public Turn(Match match) {
        this.match = match;
        this.turnSystem = match.getTurnSystem();
    }

    public void onConsultantRequest(ConsultantRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onTakeOverRequest(TakeOverRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    public void onCompanyAbilityRequest(CompanyAbilityRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }
}
