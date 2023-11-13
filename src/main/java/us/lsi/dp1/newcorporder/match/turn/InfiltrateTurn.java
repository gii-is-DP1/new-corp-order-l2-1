package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;

public class InfiltrateTurn extends Turn {

    private enum State implements TurnState {SELECTING_CONSULTANT, INFILTRATE, TAKING_CONSULTANT, NONE}

    private State currentState = InfiltrateTurn.State.SELECTING_CONSULTANT;
    private UseConsultantRequest useConsultantRequest;

    public InfiltrateTurn(Match match) {
        super(match);
    }

    @Override
    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        long numDifferentConglomerates = turnSystem.getCurrentPlayer().getHand().entrySet().size();

        Preconditions.checkArgument(request.getConsultant() == ConsultantType.MEDIA_ADVISOR && numDifferentConglomerates > 1,
            "you cannot use the Consultant 'Media Advisor' if you only have one type of conglomerate share in hand");
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()),
            "invalid consultant for an infiltrate turn");

        this.useConsultantRequest = request;
        this.currentState = State.INFILTRATE;
        this.turnSystem.getCurrentPlayer().getHeadquarter().removeConsultant(request.getConsultant());

        return new UseConsultantResponse(currentState);
    }

    @Override
    public InfiltrateResponse onInfiltrateRequest(InfiltrateRequest request) {
        checkState(State.INFILTRATE);

        Infiltrate infiltrate = request.getInfiltrate();
        infiltrate.run(match, useConsultantRequest);

        if (infiltrate.getTotalNumberOfShares() >= 3) {
            currentState = State.TAKING_CONSULTANT;
        } else {
            this.endTurn();
        }

        return new InfiltrateResponse(currentState);
    }

    @Override
    public void onTakeConsultantRequest(TakeConsultantRequest request) {
        checkState(State.TAKING_CONSULTANT);
        Preconditions.checkArgument(request.getConsultant() != useConsultantRequest.getConsultant(), "you cannot take the same consultant you used to infiltrate the company");

        match.getGeneralSupply().takeConsultant(request.getConsultant());
        turnSystem.getCurrentPlayer().getHeadquarter().addConsultant(request.getConsultant());
    }

    @Override
    public DiscardShareResponse onDiscardShareRequest(DiscardShareRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    @Override
    public void endTurn() {
        this.currentState = State.NONE;
        turnSystem.passTurn();
    }

    @Override
    public TurnState getCurrentState() {
        return currentState;
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null || consultant == ConsultantType.MEDIA_ADVISOR || consultant == ConsultantType.CORPORATE_LAWYER;
    }

    private void checkState(InfiltrateTurn.State state) {
        Preconditions.checkState(currentState == state, "invalid action for the current state (%s)", state);
    }
}
