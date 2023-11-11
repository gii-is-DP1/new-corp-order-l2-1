package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.CompanyAbilityRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.ability.CompanyAbility;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;

import java.util.List;

public class TakeOverTurn extends Turn {

    private enum State implements TurnState {SELECTING_CONSULTANT, TAKING_OVER, CHOOSING_ABILITY_PROPERTIES}

    private State currentState = State.SELECTING_CONSULTANT;
    private UseConsultantRequest useConsultantRequest;
    private TakeOverRequest takeOverRequest;

    public TakeOverTurn(Match match) {
        super(match);
    }

    @Override
    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()), "invalid consultant for a take over turn");

        useConsultantRequest = request;
        currentState = State.TAKING_OVER;

        return new UseConsultantResponse(currentState);
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

        Preconditions.checkState(source.getAgents() > request.getAgents(),
            "cannot take the company over because the source company does not have the requested number of agents");
        Preconditions.checkState(match.getCompanyMatrix().countTilesControlledBy(target.getCurrentConglomerate()) > 1,
            "can't take the company over because it's controlled by the only agent of his conglomerate left");

        if (source.getCurrentConglomerate() == target.getCurrentConglomerate()) {
            source.removeAgents(request.getAgents());
            target.addAgents(request.getAgents());
            turnSystem.passTurn();
        } else {
            if (!isTakeOverSuccessful(source, target)) {
                throw new IllegalArgumentException("unsuccessful attack");
            }

            target.takeOver(source.getCurrentConglomerate(), target.getAgents());
            turnSystem.getCurrentPlayer().getHeadquarter().captureAgent(target.getCurrentConglomerate());

            currentState = State.CHOOSING_ABILITY_PROPERTIES;
        }

        if (useConsultantRequest.getConsultant() == ConsultantType.DEAL_MAKER) {
            List<Conglomerate> shares = match.getGeneralSupply().takeConglomerateSharesFromDeck(2);
            shares.forEach(share -> turnSystem.getCurrentPlayer().addShareToHand(share));
        }
    }

    private boolean isTakeOverSuccessful(CompanyTile source, CompanyTile target) {
        boolean useMilitaryContractor = useConsultantRequest.getConsultant() == ConsultantType.MILITARY_CONTRACTOR;
        return target.getAgents() < source.getAgents() || (useMilitaryContractor && target.getAgents() <= source.getAgents());
    }

    @Override
    public void onCompanyAbilityRequest(CompanyAbilityRequest request) {
        checkState(State.CHOOSING_ABILITY_PROPERTIES);

        CompanyAbility ability = request.getCompanyAbility();
        if (ability != null) {
            Preconditions.checkState(ability.getCompanyType() == takeOverRequest.getTargetCompany().getCompany().getType(),
                "the chosen ability must belong to the taken company type");

            ability.activate(match, takeOverRequest);
        }

        turnSystem.passTurn();
    }

    private void checkState(State state) {
        Preconditions.checkState(currentState == state, "invalid action for the current state (%s)", state);
    }
}
