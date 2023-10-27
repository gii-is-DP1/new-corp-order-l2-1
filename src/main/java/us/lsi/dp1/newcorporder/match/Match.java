package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
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
    @Getter private final TurnSystem turnSystem;

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
        turnSystem = new TurnSystem();
    }

    /**
     * Initializes the match.
     */
    public void init() {
        this.generalSupply.init(this.matchMode, this.players.size());
        this.companyMatrix.init(this.players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        this.initPlayers();
        this.playOrder.addAll(this.players.values());

        this.matchState = MatchState.PLAYING;
        turnSystem.init(playOrder);
    }

    private void initPlayers() {
        ConsultantInitializer consultantInitializer = new ConsultantInitializer(players.size());

        for (MatchPlayer matchPlayer : this.getMatchPlayers()) {
            List<Conglomerate> initialHand = drawInitialHand();
            ConsultantType initialConsultant = consultantInitializer.getRandomUniqueConsultant();
            matchPlayer.init(initialConsultant, initialHand);
        }
    }
    private List<Conglomerate> drawInitialHand()
    {
        return this.generalSupply.takeConglomerateSharesFromDeck(INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
    }
    public MatchPlayer getMatchPlayer(int playerId) {
        return this.players.get(playerId);
    }

    public Collection<MatchPlayer> getMatchPlayers() {
        return this.players.values();
    }
}
