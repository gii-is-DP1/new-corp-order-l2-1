package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

public class TakeOver {
    private final TurnSystem turnSystem;
    private final Match match;
    public TakeOver(Match match)
    {
        this.turnSystem = match.getTurnSystem();
        this.match = match;
    }

    public enum TakeOverConsultant {NONE, MILITARY_CONTRACTOR, DEAL_MAKER}
    private TakeOverConsultant choosenTakeOverConsultant;
    private int takeOverAgents;
    private Conglomerate sourceConglomerate;
    private CompanyTile takenOverCompany;


    public void chooseConsultantToActivate(TakeOverConsultant choosenConsultant) throws IllegalStateException
    {
        if(turnSystem.getCurrentState() != MatchTurnState.SELECTING_CONSULTANT)
            throw new IllegalStateException();

        choosenTakeOverConsultant = choosenConsultant;
        turnSystem.setState(MatchTurnState.ROTATING_CARDS);
    }

    public void rotateCards(Conglomerate type, int quantityToRotate)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.ROTATING_CARDS, "must be in the current state!");
        turnSystem.getCurrentHq().rotateConglomerates(type, quantityToRotate);

        sourceConglomerate = type;
        takeOverAgents = quantityToRotate;

        turnSystem.setState(MatchTurnState.CHOOSING_COMPANY_TAKEOVER);
    }

    public void chooseCompanyToTakeOver(CompanyTile sourceCompany, CompanyTile targetCompany)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.CHOOSING_COMPANY_TAKEOVER, "must be in the current state!");
        if(targetCompany.getCurrentConglomerate() == sourceConglomerate)
            targetCompany.addAgents(takeOverAgents);
        else if(targetCompany.getAgents() < sourceCompany.getAgents() ||
            (choosenTakeOverConsultant == TakeOverConsultant.MILITARY_CONTRACTOR && targetCompany.getAgents() <= sourceCompany.getAgents()))
        {
            targetCompany.takeOver(sourceConglomerate,targetCompany.getAgents());
            turnSystem.getCurrentHq().addAgents(targetCompany.getCurrentConglomerate(),1);
            takenOverCompany = targetCompany;
            turnSystem.setState(MatchTurnState.ACTIVATING_COMPANY_ABILITY);
        }
        else{
            sourceCompany.addAgents(takeOverAgents);
        }
        if(choosenTakeOverConsultant == TakeOverConsultant.DEAL_MAKER)
        {
            Conglomerate share = match.getGeneralSupply().takeConglomerateShareFromDeck();
            //TODO use addShareToHand method
        }
    }

    public void activateCompanyAbility(Boolean wantsToActivateCompanyAbility)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.ACTIVATING_COMPANY_ABILITY, "must be in the current state!");
        if(!wantsToActivateCompanyAbility) return;

        switch (takenOverCompany.getCompany().getType())
        {
            case BROADCAST_NETWORK -> {
                turnSystem.setState(MatchTurnState.SELECTING_COMPANIES_FOR_BROADCAST_NETWORK_ABILITY);
            }
            case PRINT_MEDIA -> {
                turnSystem.setState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_PRINT_MEDIA_ABILITY);
            }
            case GUERRILLA_MARKETING -> {
                turnSystem.setState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_GUERRILLA_MARKETING_ABILITY);
            }
            case AMBIENT_ADVERTISING -> {
                turnSystem.setState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_AMBIENT_ADVERTISING_ABILITY);
            }
            case ONLINE_MARKETING -> {
                turnSystem.setState(MatchTurnState.SELECTING_COMPANIES_FOR_ONLINE_MARKETING_ABILITY);
            }
            case SOCIAL_MEDIA -> {
                turnSystem.setState(MatchTurnState.SELECTING_CONGLOMERATE_FOR_SOCIAL_MEDIA_ABILITY);
            }
            default -> throw new IllegalStateException("Unexpected value: " + takenOverCompany);
        }
    }

    public void selectCompaniesForBroadcastNetworkAbility(CompanyTile source, CompanyTile firstTarget, CompanyTile secondTarget, int agentsToMove)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_COMPANIES_FOR_BROADCAST_NETWORK_ABILITY, "must be in the current state!");
        Preconditions.checkArgument(!(agentsToMove == 1 && secondTarget != null), "can't have second target with just one agent!");
        Preconditions.checkArgument(agentsToMove == 1 || agentsToMove == 2, "must move one or two agents");

        if(secondTarget == null)
            firstTarget.addAgents(agentsToMove);
        else
        {
            firstTarget.addAgents(1);
            secondTarget.addAgents(1);
        }

        source.removeAgents(agentsToMove);

        turnSystem.passTurn();
    }

    public void selectConglomeratesForGuerillaMarketingAbility(Headquarter hq, Conglomerate conglomerateToRotate, Boolean rotatesTwoCards)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_CONGLOMERATES_FOR_GUERRILLA_MARKETING_ABILITY, "must be in the current state!");
        Preconditions.checkArgument(turnSystem.getCurrentHq() != hq, "headquarter must be different than yours!");
        hq.rotateConglomerates(conglomerateToRotate,rotatesTwoCards ? 2 : 1);
        turnSystem.passTurn();
    }

    public void selectConglomeratesForPrintMediaAbility(Conglomerate ownConglomerate, Boolean isOwnRotated, Headquarter otherHq, Conglomerate otherConglomerate, Boolean isOtherRotated)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_CONGLOMERATES_FOR_PRINT_MEDIA_ABILITY, "must be in the current state!");
        Preconditions.checkArgument(turnSystem.getCurrentHq() != otherHq, "headquarters must be different!");

        otherHq.AddConglomerate(ownConglomerate, isOwnRotated);
        turnSystem.getCurrentHq().AddConglomerate(otherConglomerate, isOtherRotated);
        turnSystem.passTurn();
    }

    public void selectConglomeratesForAmbientAdvertisingAbility(Headquarter hq, Conglomerate conglomerateToRotate, Boolean rotatesTwoCards)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_CONGLOMERATES_FOR_AMBIENT_ADVERTISING_ABILITY, "must be in the current state!");
        turnSystem.getCurrentHq().unrotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
        turnSystem.passTurn();
    }

    public void selectCompaniesForSocialMediaAbility(Headquarter hq, Conglomerate conglomerateToRemove)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_CONGLOMERATE_FOR_SOCIAL_MEDIA_ABILITY, "must be in the current state!");
        hq.removeConglomerates(conglomerateToRemove, 1);
        turnSystem.passTurn();
    }

    public void selectCompaniesForOnlineMarketingAbility(CompanyTile a, CompanyTile b)
    {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_COMPANIES_FOR_ONLINE_MARKETING_ABILITY, "must be in the current state!");

        int aAgents = a.getAgents();
        Conglomerate aConglomerate = a.getCurrentConglomerate();

        a.setAgents(b.getAgents());
        a.setCurrentConglomerate(b.getCurrentConglomerate());

        b.setAgents(aAgents);
        b.setCurrentConglomerate(aConglomerate);
        turnSystem.passTurn();
    }
}
