package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;

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
        long numDiferentConglomerates = turnSystem.getCurrentPlayer().getHand().size();
        Preconditions.checkArgument(request.getConsultant() == ConsultantType.MEDIA_ADVISOR && numDiferentConglomerates > 1,
            "you cannot use the Consultant 'Media Advisor' if you only have one type of conglomerate share in hand");
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()), "invalid consultant for a infiltrate turn");
        consultantRequest = request;
        currentState = State.INFILTRATE;
    }


    @Override
    public void onInfiltrateRequest(InfiltrateRequest request) {
        checkState(State.INFILTRATE);
        Infiltrate infiltrate = request.getInfiltrate();
        Preconditions.checkState(infiltrate.getConsultant() == consultantRequest.getConsultant(),
            "the infiltrate must be the same type as the consultant used");
        infiltrate.infiltrate(match, consultantRequest);

        if(infiltrate.getConglomerateSharesUsed() >= 3){
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