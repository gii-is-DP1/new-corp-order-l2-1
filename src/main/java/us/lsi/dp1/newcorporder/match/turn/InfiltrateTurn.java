package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;

public class InfiltrateTurn extends Turn {

    enum State {SELECTING_CONSULTANT, INFILTRATE, TAKING_CONSULTANT}

    private State currentState = InfiltrateTurn.State.SELECTING_CONSULTANT;
    private ConsultantRequest consultantRequest;

    public InfiltrateTurn(Match match) {
        super(match);
    }

    @Override
    public void onConsultantRequest(ConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        //TODO: hacer precondition para que compruebe q se tiene al menos 2 tipos diferentes de conglomerate shares
        // si se usa al Media Advisor
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()), "invalid consultant for a infiltrate turn");
        consultantRequest = request;
        currentState = State.INFILTRATE;
    }


    @Override
    public void onInfiltrateRequest(InfiltrateRequest request) {
        checkState(State.INFILTRATE);
        turnSystem.getCurrentPlayer().discardSharesFromHand(request.getConglomerateType(), request.getConglomerateShares());
        turnSystem.getCurrentPlayer().getHeadquarter().addConglomerates(request.getConglomerateType(), request.getConglomerateShares());
        assert request.getTile() != null;   //FIXME: make in a better way
        Preconditions.checkArgument(request.getTile().getCurrentConglomerate() != request.getConglomerateType(),
            "you cannot add agents to a box that has agents from a different conglomerate");
        request.getTile().addAgents(request.getConglomerateShares());

        if (request.getConglomerateShares() >= 3) {
            currentState = State.TAKING_CONSULTANT;
        }
    }

    @Override
    public void onTakeConsultantRequest(TakeConsultantRequest request) {
        checkState(State.TAKING_CONSULTANT);
        Preconditions.checkArgument(request.getConsultant() != consultantRequest.getConsultant(),
            "you cannot take the same consultant you used to infiltrate the company");
        match.getGeneralSupply().takeConsultant(request.getConsultant());
        turnSystem.getCurrentPlayer().getHeadquarter().addConsultant(request.getConsultant());
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null || consultant == ConsultantType.MEDIA_ADVISOR || consultant == ConsultantType.CORPORATE_LAWYER;
    }

    private void checkState(InfiltrateTurn.State state) {
        Preconditions.checkState(currentState == state, "invalid action for the current state (%s)", state);
    }


}
