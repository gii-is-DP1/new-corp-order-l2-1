package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;
import us.lsi.dp1.newcorporder.player.Player;

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
    @Getter @Setter private final CompanyMatrix companyMatrix;
    @Getter private final TurnSystem turnSystem;

    @Getter private MatchState matchState = MatchState.WAITING;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    @Builder
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

        Multiset<MatchPlayer> victoryPoints = this.calculateVictoryPoints();
        List<MatchPlayer> winners = getWinners();
        //TODO generate and save stats
    }

    public MatchPlayer getPlayer(int id) {
        return players.get(id);
    }

    public Collection<MatchPlayer> getPlayers() {
        return players.values();
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

    public List<MatchPlayer> getWinners() {
        Multiset<MatchPlayer> victoryPoints = this.calculateVictoryPoints();
        int maxVictoryPoints = 0;
        List<MatchPlayer> winners = new ArrayList<>();
        for (MatchPlayer player : victoryPoints.elementSet()) {
            if (victoryPoints.count(player) >= maxVictoryPoints) {
                maxVictoryPoints = victoryPoints.count(player);
                if (victoryPoints.count(player) > maxVictoryPoints)
                    winners.clear();
                winners.add(player);
            }
        }
        return winners;
    }

}
