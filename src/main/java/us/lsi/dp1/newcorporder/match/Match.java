package us.lsi.dp1.newcorporder.match;

import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.*;

public class Match {
    private static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;

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

    private Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        turnSystem = new TurnSystem();
    }

    public void init() {
        generalSupply.init(matchMode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        initPlayers();
        playOrder.addAll(players.values());

        matchState = MatchState.PLAYING;
        turnSystem.init(playOrder);
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

    private Collection<MatchPlayer> getPlayers() {
        return players.values();
    }
}
