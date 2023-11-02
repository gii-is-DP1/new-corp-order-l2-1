package us.lsi.dp1.newcorporder.match;

import us.lsi.dp1.newcorporder.payload.request.ConsultantRequest;

public abstract class Move {
    protected final TurnSystem turnSystem;
    protected Match match;

    public abstract void useConsultant(ConsultantRequest request);

    public Move(Match match) {
        this.turnSystem = match.getTurnSystem();
        this.match = match;
    }
}
