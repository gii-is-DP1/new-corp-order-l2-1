package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
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

    @Getter
    private final int maxPlayers;
    @Getter
    private final MatchMode matchMode;
    @Getter
    private final String inviteCode;

    @Getter
    private final GeneralSupply generalSupply;
    @Getter
    private final CompanyMatrix companyMatrix;

    @Getter
    private MatchState matchState = MatchState.WAITING;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();
    private final List<MatchPlayer> playOrder = new ArrayList<>();

    @Getter
    private MatchPlayer currentTurnPlayer;
    @Getter
    private MatchTurnState currentTurnState;

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
    // Infiltrate
    //

    /**
     * Action of infiltrate,
     * we use a number of conglomerate cards we have in hand and we place it down on the board to place
     * the same name of agent in a company tile that have the same type of the conglomerate cards we play
     *
     * @param conglomerateType       type of the conglomerate cards used.
     * @param conglomerateSharesUsed number of the conglomerate cards used.
     * @param x                      cord x of the Company Matrix
     * @param y                      cord y of the Company Matrix
     */
    private void basicInfiltrate(Conglomerate conglomerateType, int conglomerateSharesUsed, int x, int y) {
        this.currentTurnPlayer.useConglomerateShares(conglomerateType, conglomerateSharesUsed);
        this.currentTurnPlayer.getHeadquarter().addConglomerateShare(conglomerateType, conglomerateSharesUsed);
        this.companyMatrix.addAgentsSpecificTile(x, y, conglomerateSharesUsed, conglomerateType);
    }

    /**
     * Infiltrate without using any consultants.
     *
     * @param conglomerateType       type of the conglomerate cards used.
     * @param conglomerateSharesUsed number of the conglomerate cards used.
     * @param x                      cord x of the Company Matrix
     * @param y                      cord y of the Company Matrix
     */
    public void infiltrateNoConsultants(Conglomerate conglomerateType, int conglomerateSharesUsed, int x, int y) {
        basicInfiltrate(conglomerateType, conglomerateSharesUsed, x, y);
        if (conglomerateSharesUsed >= 3)
            this.currentTurnState = MatchTurnState.TAKE_A_CONSULTANT;
    }

    /**
     * Infiltrate with the ability of the "Media Advisor" (When the player infiltrates that turn the system will
     * allow him to place a conglomerate share of a different color. The system will consider that it has the same
     * color as the rest of the conglomerate shares that the player places)
     *
     * @param conglomerateType       type of the conglomerate cards used.
     * @param conglomerateSharesUsed number of the conglomerate cards used.
     * @param x                      cord x of the Company Matrix
     * @param y                      cord y of the Company Matrix
     * @param extraConglomerate      type of the extra card.
     */
    public void infiltrateWithMediaAdvisor(Conglomerate conglomerateType, int conglomerateSharesUsed, int x, int y,
                                           Conglomerate extraConglomerate) {
        this.currentTurnPlayer.useConglomerateShares(conglomerateType, conglomerateSharesUsed);
        this.currentTurnPlayer.useConglomerateShares(extraConglomerate, 1);
        this.currentTurnPlayer.getHeadquarter().addConglomerateShare(conglomerateType, conglomerateSharesUsed + 1);
        this.companyMatrix.addAgentsSpecificTile(x, y, conglomerateSharesUsed, conglomerateType);
        if (conglomerateSharesUsed + 1 >= 3)
            this.currentTurnState = MatchTurnState.TAKE_A_CONSULTANT;
    }

    /**
     * Infiltrate with the ability of the "Corporate lawyer" (When the player infiltrates that turn, the system will allow
     * him to place cards from two different conglomerates and place agents of both colors)
     *
     * @param conglomerateType1       type of the 1º conglomerate cards used.
     * @param conglomerateSharesUsed1 number of 1º the conglomerate cards used.
     * @param x1                      cord x of the Company Matrix
     * @param y1                      cord y of the Company Matrix
     * @param conglomerateType2       type of the 2º conglomerate cards used.
     * @param conglomerateSharesUsed2 number of 2º the conglomerate cards used.
     * @param x2                      cord x of the Company Matrix
     * @param y2                      cord y of the Company Matrix
     */
    public void infiltrateWithCorporateLawyer(Conglomerate conglomerateType1, int conglomerateSharesUsed1, int x1, int y1,
                                              Conglomerate conglomerateType2, int conglomerateSharesUsed2, int x2, int y2) {
        Preconditions.checkArgument(conglomerateType1 != conglomerateType2,
            "the conglomerate types used have to be different");
        basicInfiltrate(conglomerateType1, conglomerateSharesUsed1, x1, y1);
        basicInfiltrate(conglomerateType2, conglomerateSharesUsed2, x2, y2);
        if (conglomerateSharesUsed1 + conglomerateSharesUsed2 >= 3)
            this.currentTurnState = MatchTurnState.TAKE_A_CONSULTANT;
    }

    private void takeConsulant(ConsultantType consultantTaken, ConsultantType consultantUsed) {
        Preconditions.checkArgument(this.currentTurnState == MatchTurnState.TAKE_A_CONSULTANT,
            "You cannot take a consultant in " + this.currentTurnState);
        Preconditions.checkArgument(consultantTaken != consultantUsed,
            "You cannot take the same consultant that you play");
        this.generalSupply.takeConsultant(consultantTaken);
        if(consultantTaken != ConsultantType.NONE)
            this.currentTurnPlayer.getHeadquarter().addConsultant(consultantTaken);
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
        this.setTurnState(MatchTurnState.SELECTING_ACTION);
    }

    public MatchPlayer getMatchPlayer(int playerId) {
        return this.players.get(playerId);
    }

    public Collection<MatchPlayer> getMatchPlayers() {
        return this.players.values();
    }
}
