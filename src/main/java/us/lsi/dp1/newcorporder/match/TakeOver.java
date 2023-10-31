package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.payload.request.CompanyAbilityRequest;
import us.lsi.dp1.newcorporder.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class TakeOver implements Move {
    private final TurnSystem turnSystem;
    private final Match match;

    private ConsultantRequest consultantRequest;
    private TakeOverRequest takeOverRequest;

    public TakeOver(Match match) {
        this.turnSystem = match.getTurnSystem();
        this.match = match;
    }

    @Override
    public void useConsultant(ConsultantRequest request) {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_CONSULTANT, "must be in the current state!");
        consultantRequest = request;
        checkConsultantToActivate(request.getConsultant());
        turnSystem.setState(MatchTurnState.TAKING_OVER);
    }

    public void takeOver(TakeOverRequest request) {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.TAKING_OVER, "must be in the current state!");
        takeOverRequest = request;

        rotateCards(request.getSourceCompany().getCurrentConglomerate(), request.getAgents());
        chooseCompanyToTakeOver(request.getSourceCompany(), request.getTargetCompany());
    }

    public void activateCompanyAbility(CompanyAbilityRequest request) {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.ACTIVATING_COMPANY_ABILITY, "must be in the current state!");
        if (request.getCompanyAbility() != null) {
            request.getCompanyAbility().check(match, takeOverRequest);
            request.getCompanyAbility().activate(match, takeOverRequest);
        }
        turnSystem.passTurn();
    }

    private void checkConsultantToActivate(ConsultantType consultant) throws IllegalStateException {
        Preconditions.checkArgument(isValidConsultant(consultant), "must be a valid consultant");

    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null ||
            consultant == ConsultantType.DEAL_MAKER ||
            consultant == ConsultantType.MILITARY_CONTRACTOR;
    }

    private void rotateCards(Conglomerate type, int quantityToRotate) {
        turnSystem.getCurrentHq().rotateConglomerates(type, quantityToRotate);
    }

    private void chooseCompanyToTakeOver(CompanyTile sourceCompany, CompanyTile targetCompany) {
        if (targetCompany.getCurrentConglomerate() == sourceCompany.getCurrentConglomerate())
            targetCompany.addAgents(takeOverRequest.getAgents());
        else if (targetCompany.getAgents() < sourceCompany.getAgents() ||
            (consultantRequest.getConsultant() == ConsultantType.MILITARY_CONTRACTOR && targetCompany.getAgents() <= sourceCompany.getAgents())) {
            targetCompany.takeOver(sourceCompany.getCurrentConglomerate(), targetCompany.getAgents());
            turnSystem.getCurrentHq().addAgents(targetCompany.getCurrentConglomerate(), 1);
            turnSystem.setState(MatchTurnState.ACTIVATING_COMPANY_ABILITY);
            return;
        } else {
            sourceCompany.addAgents(takeOverRequest.getAgents());
        }
        if (consultantRequest.getConsultant() == ConsultantType.DEAL_MAKER) {
            Conglomerate share = match.getGeneralSupply().takeConglomerateShareFromDeck();
            //TODO use addShareToHand method
        }
        turnSystem.passTurn();
    }
}
