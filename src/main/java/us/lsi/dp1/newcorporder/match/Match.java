package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import java.util.*;

public class Match {

    public static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;
    public static final int SHARES_IN_OPEN_DISPLAY = 4;
    public static final int MAX_SHARES_IN_HAND = 6;

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

    private Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        this.turnSystem = new TurnSystem(this);
    }

    public void init() {
        generalSupply.init(matchMode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        initPlayers();

        turnSystem.init(new ArrayList<>(players.values()));
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

    public void end() {
        this.matchState = MatchState.FINISHED;

        Multiset<MatchPlayer> victoryPoints = this.calculateVictoryPoints();
        MatchPlayer winner = victoryPoints.entrySet().stream()
            .max(Comparator.comparingInt(Multiset.Entry::getCount))
            .map(Multiset.Entry::getElement)
            .orElseThrow();
        //TODO generate and save stats
    }

    private List<Conglomerate> drawInitialHand() {
        return generalSupply.takeConglomerateSharesFromDeck(INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
    }

    public MatchPlayer getPlayer(int id) {
        return players.get(id);
    }

    private Collection<MatchPlayer> getPlayers() {
        return players.values();
    }

    private Multiset<MatchPlayer> calculateVictoryPoints() {
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

    private List<MatchPlayer> rankPlayerParticipation(Conglomerate conglomerateType) {
        return this.players.values().stream()
            .sorted(Comparator.<MatchPlayer>comparingInt(player -> player.getParticipationPoints(conglomerateType))
                .thenComparingInt(x -> x.getHeadquarter().getAgentsCaptured(conglomerateType))
                .reversed())
            .toList();
    }
}
