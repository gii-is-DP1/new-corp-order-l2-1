package us.lsi.dp1.newcorporder.match;

import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import java.util.*;

public class Match {

    public static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;
    public static final int MAX_SHARES_IN_HAND = 6;
    public static final int SHARES_IN_OPEN_DISPLAY = 4;

    public static Match create(int maxPlayers, MatchMode matchMode) {
        GeneralSupply generalSupply = GeneralSupply.create();
        CompanyMatrix companyMatrix = CompanyMatrix.create();
        TurnSystem turnSystem = TurnSystem.create();
        String inviteCode = "foo"; // TODO make code random

        return new Match(maxPlayers, matchMode, inviteCode, generalSupply, companyMatrix, turnSystem);
    }

    @Getter private final int maxPlayers;
    @Getter private final MatchMode matchMode;
    @Getter private final String inviteCode;

    @Getter private final GeneralSupply generalSupply;
    @Getter private final CompanyMatrix companyMatrix;
    @Getter private final TurnSystem turnSystem;

    @Getter private MatchState matchState = MatchState.WAITING;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix, TurnSystem turnSystem) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        this.turnSystem = turnSystem;
    }

    public void addPlayer(MatchPlayer player) {
        this.players.put(player.getPlayerId(), player);
    }

    public void init() {
        generalSupply.init(matchMode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        initPlayers();

        turnSystem.init(this, new ArrayList<>(players.values()));
        matchState = MatchState.PLAYING;
    }

    private void initPlayers() {
        ConsultantInitializer consultantInitializer = new ConsultantInitializer(players.size());

        for (MatchPlayer player : getPlayers())
            initPlayer(player, consultantInitializer);
    }

    private void initPlayer(MatchPlayer player, ConsultantInitializer consultantInitializer) {
        List<Conglomerate> initialHand = drawInitialHand();
        ConsultantType initialConsultant = consultantInitializer.getRandomUniqueConsultant();
        player.init(initialConsultant, initialHand);
    }

    private List<Conglomerate> drawInitialHand() {
        return generalSupply.takeConglomerateSharesFromDeck(INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
    }

    public void end() {
        this.matchState = MatchState.FINISHED;
        //TODO calculate VP, assign a winner
        //TODO generate and save stats
    }

    public MatchPlayer getPlayer(int id) {
        return players.get(id);
    }

    public Collection<MatchPlayer> getPlayers() {
        return players.values();
    }
}
