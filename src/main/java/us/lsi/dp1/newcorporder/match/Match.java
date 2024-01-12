package us.lsi.dp1.newcorporder.match;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantInitializer;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.player.Player;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Match {

    public static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;
    public static final int MAX_SHARES_IN_HAND = 6;
    public static final int SHARES_IN_OPEN_DISPLAY = 4;

    public static Match create(int maxPlayers, MatchMode matchMode, MatchVisibility visibility, String inviteCode) {
        GeneralSupply generalSupply = GeneralSupply.create();
        CompanyMatrix companyMatrix = CompanyMatrix.create();
        TurnSystem turnSystem = TurnSystem.create();

        return new Match(maxPlayers, matchMode, visibility, inviteCode, generalSupply, companyMatrix, turnSystem);
    }

    @Getter private final String code;
    @Getter private final MatchVisibility visibility;
    @Getter private final MatchMode mode;
    @Getter private final int maxPlayers;
    @Getter private MatchState state = MatchState.WAITING;

    @Getter @Setter private MatchPlayer host;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    @Getter private final GeneralSupply generalSupply;
    @Getter private final CompanyMatrix companyMatrix;
    @Getter private final TurnSystem turnSystem;

    @Getter private final Instant creationTime = Instant.now();
    @Getter private Instant startTime;
    @Getter private Instant endTime;

    @Builder
    Match(int maxPlayers, MatchMode mode, MatchVisibility visibility, String code, GeneralSupply generalSupply,
          CompanyMatrix companyMatrix, TurnSystem turnSystem) {
        this.code = code;
        this.maxPlayers = maxPlayers;
        this.mode = mode;
        this.visibility = visibility;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        this.turnSystem = turnSystem;
    }

    public void start() {
        Preconditions.checkState(state == MatchState.WAITING, "match already started");
        Preconditions.checkState(players.size() > 1, "not enough players to start the match");

        generalSupply.init(mode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);
        initPlayers();

        turnSystem.init(this, new ArrayList<>(players.values()));
        state = MatchState.PLAYING;
        startTime = Instant.now();
    }

    public MatchSummary end() {
        this.state = MatchState.FINISHED;
        this.endTime = Instant.now();

        Multiset<MatchPlayer> victoryPoints = this.calculateVictoryPoints();
        Set<MatchPlayer> winners = this.getWinners(victoryPoints);

        return MatchSummary.builder()
            .victoryPoints(victoryPoints)
            .winners(winners)
            .build();
    }

    public void addPlayer(MatchPlayer player) {
        Preconditions.checkState(this.state == MatchState.WAITING, "match already started");
        Preconditions.checkState(this.players.size() < this.maxPlayers, "match is full");
        this.players.put(player.getPlayerId(), player);
    }

    public MatchPlayer getPlayer(int id) {
        return this.players.get(id);
    }

    public boolean isHost(Player player) {
        return this.host == null || Objects.equals(player.getId(), this.host.getPlayerId());
    }

    public void removePlayer(MatchPlayer player) {
        this.players.remove(player.getPlayerId());

        if (player.equals(this.host)) {
            this.host = this.players.get(0);
        }
    }

    public List<MatchPlayer> getPlayers() {
        return Optional.ofNullable(this.turnSystem.getPlayers()).orElseGet(() -> new ArrayList<>(this.players.values()));
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

    public Multiset<MatchPlayer> calculateVictoryPoints() {
        Multiset<MatchPlayer> points = HashMultiset.create();

        for (Conglomerate conglomerate : Conglomerate.values()) {
            List<MatchPlayer> participationRanking = rankPlayerParticipation(conglomerate)
                .subList(0, players.size() > 2 ? 2 : 1);

            int numTilesControlled = companyMatrix.countTilesControlledBy(conglomerate);

            for (int i = 0; i < participationRanking.size(); i++) {
                MatchPlayer player = participationRanking.get(i);
                points.add(player, (2 - i) * numTilesControlled);

                for (CompanyType companyType : player.getSecretObjectives()) {
                    int numTilesControlledOfCompanyType = companyMatrix.countTilesControlledByWithCompany(conglomerate, companyType);
                    points.add(player, 2 * numTilesControlledOfCompanyType);
                }
            }
        }

        for (MatchPlayer player : this.players.values()) {
            points.add(player, player.getHeadquarter().getConsultantsVP());
        }

        return points;
    }

    @VisibleForTesting
    List<MatchPlayer> rankPlayerParticipation(Conglomerate conglomerateType) {
        return this.players.values().stream()
            .sorted(Comparator.<MatchPlayer>comparingInt(player -> player.getParticipationPoints(conglomerateType))
                .thenComparingInt(x -> x.getHeadquarter().getAgentsCaptured(conglomerateType))
                .reversed())
            .toList();
    }

    public Set<MatchPlayer> getWinners(Multiset<MatchPlayer> victoryPoints) {
        int maximumVictoryPoints = victoryPoints.entrySet().stream()
            .mapToInt(Multiset.Entry::getCount)
            .max()
            .orElseThrow();

        return victoryPoints.entrySet().stream()
            .filter(entry -> entry.getCount() == maximumVictoryPoints)
            .map(Multiset.Entry::getElement)
            .collect(Collectors.toSet());
    }
}
