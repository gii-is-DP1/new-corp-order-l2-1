package us.lsi.dp1.newcorporder.match;

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
