package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.*;

public class Match {

    private static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;

    /**
     * Creates a new match for the given configuration
     *
     * @param maxPlayers the max number of players that the match would hold
     * @param matchMode  the mode of the match
     * @return the new match
     */
    public static Match create(int maxPlayers, MatchMode matchMode) {
        GeneralSupply generalSupply = GeneralSupply.create();
        CompanyMatrix companyMatrix = CompanyMatrix.create();
        String inviteCode = "foo"; // TODO make code random

        return new Match(maxPlayers, matchMode, inviteCode, generalSupply, companyMatrix);
    }

    @Getter private final int maxPlayers;
    @Getter private final MatchMode matchMode;
    @Getter private final String inviteCode;

    @Getter private final GeneralSupply generalSupply;
    @Getter private final CompanyMatrix companyMatrix;

    @Getter private MatchState matchState = MatchState.WAITING;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();
    private final List<MatchPlayer> playOrder = new ArrayList<>();

    @Getter private MatchPlayer currentTurnPlayer;
    @Getter private MatchTurnState currentTurnState;
    private Headquarter currentHq;

    private Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
    }

    /**
     * Initializes the match.
     */
    public void init() {
        this.generalSupply.init(this.matchMode, this.players.size());
        this.companyMatrix.init(this.players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        this.initPlayers();
        this.playOrder.addAll(this.players.values());

        this.changeTurn(this.playOrder.get(0));
        this.matchState = MatchState.PLAYING;
    }

    private void initPlayers() {
        List<ConsultantType> consultantTypes = Lists.newArrayList(ConsultantType.values());

        // if there are less than 3 players, CORPORATE_LAWYER is not used
        if (players.size() < 3) {
            consultantTypes.remove(ConsultantType.CORPORATE_LAWYER);
        }

        // init every player giving them a different consultant and the first 4 cards of the deck
        for (MatchPlayer matchPlayer : this.getMatchPlayers()) {
            List<Conglomerate> initialHand = this.generalSupply.takeConglomerateSharesFromDeck(INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);

            ConsultantType initialConsultant = consultantTypes.get(0);
            consultantTypes.remove(initialConsultant);

            matchPlayer.init(initialConsultant, initialHand);
        }
    }

    //
    // Turn system
    //

    private void setTurnState(MatchTurnState nextTurnState) {
        this.currentTurnState = nextTurnState;
    }

    private void nextTurn() {
        int currentTurnIndex = this.playOrder.indexOf(this.currentTurnPlayer);
        this.changeTurn(this.playOrder.get(currentTurnIndex < this.playOrder.size() - 1 ? currentTurnIndex + 1 : 0));
    }

    private void changeTurn(MatchPlayer player) {
        this.currentTurnPlayer = player;
        currentHq = currentTurnPlayer.getHeadquarter();
        this.setTurnState(MatchTurnState.SELECTING_ACTION);
    }

    public MatchPlayer getMatchPlayer(int playerId) {
        return this.players.get(playerId);
    }

    public Collection<MatchPlayer> getMatchPlayers() {
        return this.players.values();
    }

    enum TakeOverConsultant {NONE, MILITARY_CONTRACTOR, DEAL_MAKER}
    private TakeOverConsultant choosenTakeOverConsultant;
    private int takeOverAgents;
    private Conglomerate sourceConglomerate;

    private CompanyTile takenOverCompany;
    public void chooseConsultantToActivate(TakeOverConsultant choosenConsultant) throws IllegalStateException
    {
        if(currentTurnState != MatchTurnState.SELECTING_CONSULTANT)
            throw new IllegalStateException();

        choosenTakeOverConsultant = choosenConsultant;
        setTurnState(MatchTurnState.ROTATING_CARDS);
    }

    public void rotateCards(Conglomerate type, int quantityToRotate)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.ROTATING_CARDS, "must be in the current state!");
        currentTurnPlayer.getHeadquarter().rotateConglomerates(type, quantityToRotate);

        takeOverAgents = quantityToRotate;

        setTurnState(MatchTurnState.CHOOSING_COMPANY_TAKEOVER);
    }

    public void chooseCompanyToTakeOver(CompanyTile sourceCompany, CompanyTile targetCompany)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.CHOOSING_COMPANY_TAKEOVER, "must be in the current state!");
        if(targetCompany.getCurrentConglomerate() == sourceConglomerate)
                targetCompany.addAgents(takeOverAgents);
        else if(targetCompany.getAgents() < sourceCompany.getAgents() ||
                (choosenTakeOverConsultant == TakeOverConsultant.MILITARY_CONTRACTOR && targetCompany.getAgents() <= sourceCompany.getAgents()))
            {
                targetCompany.takeOver(sourceConglomerate,targetCompany.getAgents());
                currentTurnPlayer.getHeadquarter().addAgents(targetCompany.getCurrentConglomerate(),1);
                takenOverCompany = targetCompany;
                setTurnState(MatchTurnState.ACTIVATING_COMPANY_ABILITY);
                // TODO company ability
            }
        else{
            sourceCompany.addAgents(takeOverAgents);
        }
        if(choosenTakeOverConsultant == TakeOverConsultant.DEAL_MAKER)
        {
            Conglomerate share = this.generalSupply.takeConglomerateShareFromDeck();
            //TODO use addShareToHand method
        }
    }

    public void activateCompanyAbility(Boolean wantsToActivateCompanyAbility)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.ACTIVATING_COMPANY_ABILITY, "must be in the current state!");
        if(!wantsToActivateCompanyAbility) return;

        switch (takenOverCompany.getCompany().getType())
        {
            case BROADCAST_NETWORK -> {
                setTurnState(MatchTurnState.SELECTING_COMPANIES_FOR_BROADCAST_NETWORK_ABILITY);
            }
            case PRINT_MEDIA -> {
                setTurnState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_PRINT_MEDIA_ABILITY);
            }
            case GUERRILLA_MARKETING -> {
                setTurnState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_GUERRILLA_MARKETING_ABILITY);
            }
            case AMBIENT_ADVERTISING -> {
                setTurnState(MatchTurnState.SELECTING_CONGLOMERATES_FOR_AMBIENT_ADVERTISING_ABILITY);
            }
            case ONLINE_MARKETING -> {
                setTurnState(MatchTurnState.SELECTING_COMPANIES_FOR_ONLINE_MARKETING_ABILITY);
            }
            case SOCIAL_MEDIA -> {
                setTurnState(MatchTurnState.SELECTING_CONGLOMERATE_FOR_SOCIAL_MEDIA_ABILITY);
            }
            default -> throw new IllegalStateException("Unexpected value: " + takenOverCompany);
        }
    }

    public void selectCompaniesForBroadcastNetworkAbility(CompanyTile source, CompanyTile firstTarget, CompanyTile secondTarget, int agentsToMove)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_COMPANIES_FOR_BROADCAST_NETWORK_ABILITY, "must be in the current state!");
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

        nextTurn();
    }

    public void selectConglomeratesForGuerillaMarketingAbility(Headquarter hq, Conglomerate conglomerateToRotate, Boolean rotatesTwoCards)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_CONGLOMERATES_FOR_GUERRILLA_MARKETING_ABILITY, "must be in the current state!");
        Preconditions.checkArgument(currentHq != hq, "headquarter must be different than yours!");
        hq.rotateConglomerates(conglomerateToRotate,rotatesTwoCards ? 2 : 1);
        nextTurn();
    }

    public void selectConglomeratesForPrintMediaAbility(Conglomerate ownConglomerate, Boolean isOwnRotated, Headquarter otherHq, Conglomerate otherConglomerate, Boolean isOtherRotated)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_CONGLOMERATES_FOR_PRINT_MEDIA_ABILITY, "must be in the current state!");
        Preconditions.checkArgument(currentHq != otherHq, "headquarters must be different!");

        otherHq.AddConglomerate(ownConglomerate, isOwnRotated);
        currentHq.AddConglomerate(otherConglomerate, isOtherRotated);
        nextTurn();
    }

    public void selectConglomeratesForAmbientAdvertisingAbility(Headquarter hq, Conglomerate conglomerateToRotate, Boolean rotatesTwoCards)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_CONGLOMERATES_FOR_AMBIENT_ADVERTISING_ABILITY, "must be in the current state!");
        currentHq.unrotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
        nextTurn();
    }

    public void selectCompaniesForSocialMediaAbility(Headquarter hq, Conglomerate conglomerateToRemove)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_CONGLOMERATE_FOR_SOCIAL_MEDIA_ABILITY, "must be in the current state!");
        hq.removeConglomerates(conglomerateToRemove, 1);
        nextTurn();
    }

    public void selectCompaniesForOnlineMarketingAbility(CompanyTile a, CompanyTile b)
    {
        Preconditions.checkState(currentTurnState == MatchTurnState.SELECTING_COMPANIES_FOR_ONLINE_MARKETING_ABILITY, "must be in the current state!");

        int aAgents = a.getAgents();
        Conglomerate aConglomerate = a.getCurrentConglomerate();

        a.setAgents(b.getAgents());
        a.setCurrentConglomerate(b.getCurrentConglomerate());

        b.setAgents(aAgents);
        b.setCurrentConglomerate(aConglomerate);
        nextTurn();
    }

}
