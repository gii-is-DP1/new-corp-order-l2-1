package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.payload.request.CompanyAbility;
import us.lsi.dp1.newcorporder.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class TakeOver extends Move {
    private ConsultantRequest consultantRequest;
    private TakeOverRequest takeOverRequest;

    public TakeOver(Match match) {
        super(match);
    }

    @Override
    public void useConsultant(ConsultantRequest request) {
        checkState(MatchTurnState.SELECTING_CONSULTANT);
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()), "must be a valid consultant");
        consultantRequest = request;

        turnSystem.setState(MatchTurnState.TAKING_OVER);
    }

    public void takeOver(TakeOverRequest request) {
        checkState(MatchTurnState.TAKING_OVER);
        takeOverRequest = request;

        rotateCards(request);
        MatchTurnState nextState = chooseCompanyToTakeOver(request.getSourceCompany(), request.getTargetCompany());
        setStateOrPassTurn(nextState);
    }

    public void activateCompanyAbility(CompanyAbility request) {
        checkState(MatchTurnState.ACTIVATING_COMPANY_ABILITY);
        if (request != null)
            request.activate(match, takeOverRequest);

        turnSystem.passTurn();
    }

    private void setStateOrPassTurn(MatchTurnState nextState) {
        if (nextState == MatchTurnState.SELECTING_ACTION)
            turnSystem.passTurn();
        else turnSystem.setState(nextState);
    }

    private void checkState(MatchTurnState rightState) {
        Preconditions.checkState(turnSystem.getCurrentState() == rightState, "must be in the current state!");
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null ||
            consultant == ConsultantType.DEAL_MAKER ||
            consultant == ConsultantType.MILITARY_CONTRACTOR;
    }

    private void rotateCards(TakeOverRequest request) {
        Conglomerate type = request.getSourceCompany().getCurrentConglomerate();
        int quantityToRotate = request.getAgents();
        rotateCards(type, quantityToRotate);
    }

    private void rotateCards(Conglomerate type, int quantityToRotate) {
        turnSystem.getCurrentHq().rotateConglomerates(type, quantityToRotate);
    }

    private MatchTurnState chooseCompanyToTakeOver(CompanyTile source, CompanyTile target) {
        if (haveSameConglomerate(source, target))
            moveAgentsTo(target);
        else if (isTakeOverSuccessful(source, target)) {
            takeOver(source, target);
            return MatchTurnState.ACTIVATING_COMPANY_ABILITY;
        } else moveAgentsTo(source);

        if (consultantRequest.getConsultant() == ConsultantType.DEAL_MAKER) {
            Conglomerate share = match.getGeneralSupply().takeConglomerateShareFromDeck();
            //TODO use addShareToHand method
        }
        return MatchTurnState.SELECTING_ACTION;
    }

    private boolean haveSameConglomerate(CompanyTile a, CompanyTile b) {
        return a.getCurrentConglomerate() == b.getCurrentConglomerate();
    }

    private void moveAgentsTo(CompanyTile target) {
        target.addAgents(takeOverRequest.getAgents());
    }

    private boolean isTakeOverSuccessful(CompanyTile source, CompanyTile target) {
        boolean useMilitaryContractor = consultantRequest.getConsultant() == ConsultantType.MILITARY_CONTRACTOR;
        return target.getAgents() < source.getAgents() || (useMilitaryContractor && target.getAgents() <= source.getAgents());
    }

    private void takeOver(CompanyTile source, CompanyTile target) {
        target.takeOver(source.getCurrentConglomerate(), target.getAgents());
        turnSystem.getCurrentHq().captureAgent(target.getCurrentConglomerate());
    }
}