package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.PostTakeOverRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

import java.util.List;

public class TakeOverTurn extends Turn {

    private enum State {SELECTING_CONSULTANT, TAKING_OVER, POST_TAKING_OVER}

    private State currentState = State.SELECTING_CONSULTANT;
    private ConsultantRequest consultantRequest;
    private TakeOverRequest takeOverRequest;

    public TakeOverTurn(Match match) {
        super(match);
    }

    @Override
    public void onConsultantRequest(ConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()), "invalid consultant for a take over turn");

        consultantRequest = request;
        currentState = State.TAKING_OVER;
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null ||
               consultant == ConsultantType.DEAL_MAKER ||
               consultant == ConsultantType.MILITARY_CONTRACTOR;
    }

    @Override
    public void onTakeOverRequest(TakeOverRequest request) {
        checkState(State.TAKING_OVER);
        takeOverRequest = request;

        rotateCards(request);
        takeOver(request);
    }

    private void rotateCards(TakeOverRequest request) {
        Conglomerate type = request.getSourceCompany().getCurrentConglomerate();
        int quantityToRotate = request.getAgents();
        turnSystem.getCurrentPlayer().getHeadquarter().rotateConglomerates(type, quantityToRotate);
    }

    private void takeOver(TakeOverRequest request) {
        CompanyTile source = request.getSourceCompany();
        CompanyTile target = request.getTargetCompany();

        Preconditions.checkState(match.getCompanyMatrix().countTilesControlledBy(target.getCurrentConglomerate()) > 1,
            "can't take the company over because it's controlled by the only agent of his conglomerate left");

        // when we implement responses, this could be moved to other smaller method
        if (source.getCurrentConglomerate() == target.getCurrentConglomerate()) {
            addAgentsTo(target);
        } else if (isTakeOverSuccessful(source, target)) {
            target.takeOver(source.getCurrentConglomerate(), target.getAgents());
            turnSystem.getCurrentPlayer().getHeadquarter().captureAgent(target.getCurrentConglomerate());

            currentState = State.POST_TAKING_OVER;
            return;
        } else {
            addAgentsTo(source);
        }

        if (consultantRequest.getConsultant() == ConsultantType.DEAL_MAKER) {
            List<Conglomerate> shares = match.getGeneralSupply().takeConglomerateSharesFromDeck(2);
            shares.forEach(share -> turnSystem.getCurrentPlayer().addShareToHand(share));
        }

        if (request.getAgents() >= 3) {
            currentState = State.POST_TAKING_OVER;
            return;
        }
        turnSystem.passTurn();
    }

    private void addAgentsTo(CompanyTile target) {
        target.addAgents(takeOverRequest.getAgents());
    }

    private boolean isTakeOverSuccessful(CompanyTile source, CompanyTile target) {
        boolean useMilitaryContractor = consultantRequest.getConsultant() == ConsultantType.MILITARY_CONTRACTOR;
        return target.getAgents() < source.getAgents() || (useMilitaryContractor && target.getAgents() <= source.getAgents());
    }

    @Override
    public void onPostTakeOverRequest(PostTakeOverRequest request) {
        checkState(State.POST_TAKING_OVER);
        if (request.getCompanyAbility() != null)
            request.getCompanyAbility().activate(match, takeOverRequest);

        if (request.getChosenConsultant() != null) {
            ConsultantType chosenConsultant = request.getChosenConsultant();
            turnSystem.getCurrentPlayer().getHeadquarter().addConsultant(chosenConsultant);
        }

        turnSystem.passTurn();
    }

    private void checkState(State state) {
        Preconditions.checkState(currentState == state, "invalid action for the current state (%s)", state);
    }
}
