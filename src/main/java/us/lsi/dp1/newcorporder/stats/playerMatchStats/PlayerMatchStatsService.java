package us.lsi.dp1.newcorporder.stats.playerMatchStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.consultantPlayerMatchStats.ConsultantPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStatsRepository;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStatsRepository;

import java.util.*;

@Service
public class PlayerMatchStatsService {

    @Autowired
    private static PlayerMatchStatsRepository playerMatchStatsRepository;
    private final MatchStatsRepository matchStatsRepository;

    public PlayerMatchStatsService(MatchStatsRepository matchStatsRepository, PlayerMatchStatsRepository playerMatchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
        this.playerMatchStatsRepository=playerMatchStatsRepository;
    }

    public Optional<MatchStats> getMatchStatsById(Integer id) {return matchStatsRepository.findById(id);}

    public Set<PlayerMatchStats> getPlayerMatchStats(Integer matchId) {return playerMatchStatsRepository.findByMatchId(matchId);}

    public static PlayerMatchStats createPlayerMatchStats(Match match, MatchPlayer player) {
        List<MatchPlayer> winners = match.getWinners();

        MatchStats matchStats = null;//FIXME: es un bucle infinito
        MatchResult result;
        if (winners.contains(player)) {
            if (winners.size() > 1)
                result = MatchResult.TIED;
            else
                result = MatchResult.WON;
        } else {
            result = MatchResult.LOST;
        }
        Integer totalVP = match.calculateVictoryPoints().count(player);
        Integer timesPlotted = player.calculateTimesPlotted();
        Integer timesInfiltrated = player.calculateTimesInfiltrated();
        Integer timesTakenOver = player.calculateTimesTakenOver();
        Set<CompanyPlayerMatchStats> companyStats = new HashSet<>();
        for (CompanyType companyType : CompanyType.values()) {
            Integer abilityUsed = null; // TODO: calculateAbilityUsed(match, player, companyType);
            companyStats.add(new CompanyPlayerMatchStats(companyType, abilityUsed));
        }

        Set<ConglomeratePlayerMatchStats> conglomerateStats = new HashSet<>();
        for (Conglomerate conglomerate : Conglomerate.values()) {
            Integer shares = player.getHeadquarter().getTotalConglomeratesShares(conglomerate);
            Integer agents = player.getHeadquarter().getAgentsCaptured(conglomerate);
            conglomerateStats.add(new ConglomeratePlayerMatchStats(conglomerate, shares, agents));
        }
        Set<ConsultantPlayerMatchStats> consultantStats = new HashSet<>();
        for (ConsultantType consultantType : ConsultantType.values()) {
            Integer used = null; // TODO: calculateConsultantUsed(match, player, consultantType);
            consultantStats.add(new ConsultantPlayerMatchStats(consultantType, used));
        }

        PlayerMatchStats playerMatchStats = new PlayerMatchStats(
        matchStats, result, totalVP, timesPlotted, timesInfiltrated, timesTakenOver,
        companyStats, conglomerateStats, consultantStats);
        playerMatchStatsRepository.save(playerMatchStats);

        return new PlayerMatchStats(matchStats, result, totalVP, timesPlotted, timesInfiltrated, timesTakenOver, companyStats, conglomerateStats, consultantStats);
    }





}
