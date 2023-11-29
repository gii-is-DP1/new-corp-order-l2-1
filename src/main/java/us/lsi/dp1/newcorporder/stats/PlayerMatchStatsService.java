package us.lsi.dp1.newcorporder.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

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

}
