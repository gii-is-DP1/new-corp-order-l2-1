package us.lsi.dp1.newcorporder.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.*;

@Service
public class PlayerMatchStatsService {

    private final MatchStatsService matchStatsService;

    @Autowired
    public PlayerMatchStatsService(MatchStatsService matchStatsService) {
        this.matchStatsService = matchStatsService;
    }

    public Optional<MatchStats> getMatchStatsById(Integer matchId) {
        return matchStatsService.getMatchStatsById(matchId);
    }

    public Set<PlayerMatchStats> getPlayerMatchStats(Integer matchId) {
        Optional<MatchStats> matchStatsOptional = matchStatsService.getMatchStatsById(matchId);
        return matchStatsOptional.map(MatchStats::getPlayerMatchStats).orElse(Collections.emptySet());
    }

    public static PlayerMatchStats createPlayerMatchStats(Match match, MatchPlayer player) {
        List<MatchPlayer> winners = match.getWinners();

        MatchStats matchStats = null;//FIXME: es un bucle infinito

        MatchResult result;      //TODO: se ocupa Juan
        if (winners.contains(player)) {
            if (winners.size() > 1)
                result = MatchResult.TIED;
            else
                result = MatchResult.WON;
        } else {
            result = MatchResult.LOST;
        }
        Integer totalVP = match.calculateVictoryPoints().count(player);
        Integer timesPlotted = null;    //TODO
        Integer timesInfiltrated = null;//TODO
        Integer timesTakenOver = null;   //TODO
        Set<CompanyPlayerMatchStats> companyStats = new HashSet<>();
        for (CompanyType companyType : CompanyType.values()) {
            Integer abilityUsed = 0;     //TODO
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
            Integer used = null;    //TODO
            consultantStats.add(new ConsultantPlayerMatchStats(consultantType, used));
        }

        return new PlayerMatchStats(matchStats, result, totalVP, timesPlotted, timesInfiltrated, timesTakenOver, companyStats, conglomerateStats, consultantStats);
    }

}
