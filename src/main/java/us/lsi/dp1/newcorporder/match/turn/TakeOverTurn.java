package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseCompanyAbilityRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.ability.CompanyAbility;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.TakeOverResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseCompanyAbilityResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;

import java.util.List;

@Getter
public class TakeOverTurn extends Turn {
    private enum State implements TurnState {SELECTING_CONSULTANT, TAKING_OVER, CHOOSING_ABILITY_PROPERTIES, DISCARDING_SHARES_FROM_HAND, NONE}

    private State state = State.SELECTING_CONSULTANT;
    private UseConsultantRequest useConsultantRequest;
    private TakeOverRequest takeOverRequest;

    public TakeOverTurn(Match match) {
        super(Action.TAKE_OVER, match);
    }

    @Override
    public ConsultantType getChosenConsultant() {
        return this.useConsultantRequest.getConsultant();
    }

    @Override
    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()),
            "invalid consultant for a take over turn");
        checkValidConsultant(request.getConsultant());

        this.useConsultantRequest = request;
        this.state = State.TAKING_OVER;
        this.turnSystem.getCurrentPlayer().getHeadquarter().removeConsultant(request.getConsultant());
        this.turnSystem.getCurrentPlayer().addConsultantUse(request.getConsultant());

        return new UseConsultantResponse(state);
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null ||
               consultant == ConsultantType.DEALMAKER ||
               consultant == ConsultantType.MILITARY_CONTRACTOR;
    }

    private void checkValidConsultant(ConsultantType consultantType) {
        if (consultantType == ConsultantType.DEALMAKER && match.getGeneralSupply().getConglomerateSharesLeftInDeck() < 2) {
            throw new IllegalStateException("there are not enough shares left in deck to use the deal maker consultant");
        }
    }

    @Override
    public TakeOverResponse onTakeOverRequest(TakeOverRequest request) {
        checkState(State.TAKING_OVER);
        this.takeOverRequest = request;

        CompanyTile source = request.getSourceCompany().fromMatch(match);
        CompanyTile target = request.getTargetCompany().fromMatch(match);
        int agents = request.getAgents();

        Preconditions.checkState(checkOrthogonalTile(request.getSourceCompany(), request.getTargetCompany()),
            "cannot take over a non-ortogonal tile");
        Preconditions.checkState(source.getAgents() > request.getAgents(),
            "cannot take over the company because the source company does not have the requested number of agents");
        Preconditions.checkState(match.getCompanyMatrix().countTilesControlledBy(target.getCurrentConglomerate()) > 1,
            "can't take over the company because it's controlled by the only agent of their conglomerate left");

        if (source.getCurrentConglomerate() == target.getCurrentConglomerate()) {
            this.takeOverCompanyWithSameConglomerate(source, target, agents);
        } else {
            this.takeOverCompanyWithDifferentConglomerate(source, target, agents);
        }

        return TakeOverResponse.builder()
            .hand(turnSystem.getCurrentPlayer().getHand())
            .sourceConglomerate(source.getCurrentConglomerate())
            .nextState(state)
            .build();
    }

    private boolean checkOrthogonalTile(CompanyTileReference tile1, CompanyTileReference tile2) {
        int xJump = Math.abs(tile1.getX() - tile2.getX());
        int yJump = Math.abs(tile1.getY() - tile2.getY());
        return (xJump == 0 && yJump == 1) || (xJump == 1 && yJump == 0);
    }

    private void takeOverCompanyWithSameConglomerate(CompanyTile source, CompanyTile target, int agents) {
        this.rotateCards(source.getCurrentConglomerate(), agents);
        source.removeAgents(agents);
        target.addAgents(agents);

        this.endTurn();
    }

    private void takeOverCompanyWithDifferentConglomerate(CompanyTile source, CompanyTile target, int agents) {
        if (!canTakeOver(source, target)) {
            throw new IllegalArgumentException("unsuccessful attack");
        }

        this.rotateCards(source.getCurrentConglomerate(), agents);
        target.takeOver(source.getCurrentConglomerate(), target.getAgents());
        this.turnSystem.getCurrentPlayer().getHeadquarter().captureAgent(target.getCurrentConglomerate());

        this.state = State.CHOOSING_ABILITY_PROPERTIES;
    }

    private boolean canTakeOver(CompanyTile source, CompanyTile target) {
        boolean useMilitaryContractor = useConsultantRequest.getConsultant() == ConsultantType.MILITARY_CONTRACTOR;
        return target.getAgents() < source.getAgents() || (useMilitaryContractor && target.getAgents() <= source.getAgents());
    }

    private void rotateCards(Conglomerate conglomerate, int amount) {
        this.turnSystem.getCurrentPlayer().getHeadquarter().rotateConglomerates(conglomerate, amount);
        this.turnSystem.getCurrentPlayer().addAgentUses(conglomerate, amount);
    }

    @Override
    public UseCompanyAbilityResponse onUseCompanyAbilityRequest(UseCompanyAbilityRequest request) {
        checkState(State.CHOOSING_ABILITY_PROPERTIES);

        CompanyAbility ability = request.getCompanyAbility();
        if (ability != null) {
            Preconditions.checkState(ability.getCompanyType() == takeOverRequest.getTargetCompany().fromMatch(match).getCompany().getType(),
                "the chosen ability must belong to the taken company type");
            ability.activate(match, takeOverRequest);
            turnSystem.getCurrentPlayer().addAbilityUse(ability.getCompanyType());
        }

        this.endTurn();
        return UseCompanyAbilityResponse.builder()
            .hand(turnSystem.getCurrentPlayer().getHand())
            .nextState(state)
            .build();
    }

    @Override
    public DiscardShareResponse onDiscardShareRequest(DiscardShareRequest discardShareRequest) {
        Preconditions.checkState(state == State.DISCARDING_SHARES_FROM_HAND,
            "cannot discard a share on your turn state");

        DiscardShareResponse response = super.onDiscardShareRequest(discardShareRequest);
        this.endTurn();
        response.setNextState(this.state);

        return response;
    }

    private void endTurn() {
        if (this.isStateValidForDealMaker() && useConsultantRequest.getConsultant() == ConsultantType.DEALMAKER) {
            List<Conglomerate> shares = match.getGeneralSupply().takeConglomerateSharesFromDeck(2);
            shares.forEach(share -> turnSystem.getCurrentPlayer().addShareToHand(share));

            if (turnSystem.getCurrentPlayer().getHand().size() > Match.MAX_SHARES_IN_HAND) {
                state = State.DISCARDING_SHARES_FROM_HAND;
                return;
            }
        }

        this.turnSystem.getCurrentPlayer().addTimeTakenOver();
        this.state = State.NONE;
        this.turnSystem.passTurn();
    }

    private boolean isStateValidForDealMaker() {
        return state == State.TAKING_OVER || state == State.CHOOSING_ABILITY_PROPERTIES;
    }

    private void checkState(State state) {
        Preconditions.checkState(this.state == state, "invalid action for the current state (%s)", state);
    }
}
