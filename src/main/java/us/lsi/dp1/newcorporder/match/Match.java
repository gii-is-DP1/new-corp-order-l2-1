package us.lsi.dp1.newcorporder.match;

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
import us.lsi.dp1.newcorporder.player.Player;

import java.util.*;

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
    @Getter private final MatchMode matchMode;
    @Getter private final int maxPlayers;
    @Getter private MatchState matchState = MatchState.WAITING;

    @Getter @Setter private MatchPlayer host;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    @Getter private final GeneralSupply generalSupply;
    @Getter private final CompanyMatrix companyMatrix;
    @Getter private final TurnSystem turnSystem;

    @Builder
    Match(int maxPlayers, MatchMode matchMode, MatchVisibility visibility, String code, GeneralSupply generalSupply,
          CompanyMatrix companyMatrix, TurnSystem turnSystem) {
        this.code = code;
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.visibility = visibility;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        this.turnSystem = turnSystem;
    }

    public void start() {
        Preconditions.checkState(matchState == MatchState.WAITING, "match was already started");
        Preconditions.checkState(players.size() > 1, "not enough players to start the match");

        generalSupply.init(matchMode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);
        initPlayers();

        turnSystem.init(this, new ArrayList<>(players.values()));
        matchState = MatchState.PLAYING;
    }

    public void end() {
        this.matchState = MatchState.FINISHED;

        Multiset<MatchPlayer> victoryPoints = this.calculateVictoryPoints();
        MatchPlayer winner = victoryPoints.entrySet().stream()
            .max(Comparator.comparingInt(Multiset.Entry::getCount))
            .map(Multiset.Entry::getElement)
            .orElseThrow();
        //TODO generate and save stats
    }

    public void addPlayer(MatchPlayer player) {
        Preconditions.checkState(this.players.size() < this.maxPlayers, "match is full");
        this.players.put(player.getPlayerId(), player);
    }

    public MatchPlayer getPlayer(int id) {
        return this.players.get(id);
    }

    public boolean isHost(Player player) {
        return Objects.equals(player.getId(), this.host.getPlayerId());
    }

    public void removePlayer(MatchPlayer player) {
        this.players.remove(player.getPlayerId());

        if (player.equals(this.host)) {
            this.host = this.players.get(0);
        }
    }

    public Collection<MatchPlayer> getPlayers() {
        return players.values();
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

    public List<MatchPlayer> rankPlayerParticipation(Conglomerate conglomerateType) {
        return this.players.values().stream()
            .sorted(Comparator.<MatchPlayer>comparingInt(player -> player.getParticipationPoints(conglomerateType))
                .thenComparingInt(x -> x.getHeadquarter().getAgentsCaptured(conglomerateType))
                .reversed())
            .toList();
    }
}
