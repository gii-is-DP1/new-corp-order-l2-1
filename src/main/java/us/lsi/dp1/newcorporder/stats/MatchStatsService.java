package us.lsi.dp1.newcorporder.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.match.MatchMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchStatsService {

    private final List<MatchStats> matchStatsList;

    @Autowired
    public MatchStatsService(List<MatchStats> matchStatsList) {this.matchStatsList = matchStatsList;}

    @Transactional
    public Optional<MatchStats> getMatchStatsById(Long id) {return matchStatsList.stream().filter(matchStats -> false).findFirst();}

    @Transactional
    public MatchStats createMatchStats(MatchMode mode, Instant startedAt, Instant endedAt,
                                       Set<PlayerMatchStats> playerMatchStats,
                                       Set<ConsultantPlayerMatchStats> consultantPlayerMatchStats,
                                       Set<ConglomeratePlayerMatchStats> conglomeratePlayerMatchStats,
                                       Set<CompanyPlayerMatchStats> companyPlayerMatchStats) {
        MatchStats matchStats = new MatchStats();
        matchStats.setMode(mode);
        matchStats.setStartedAt(startedAt);
        matchStats.setEndedAt(endedAt);
        matchStats.setPlayerMatchStats(playerMatchStats);

        matchStatsList.add(matchStats);
        return matchStats;
    }

    public List<MatchStats> getAllMatchStats() {return new ArrayList<>(matchStatsList);}

    public List<MatchStats> getMatchStatsByMode(MatchMode mode) {
        return matchStatsList.stream().filter(stats -> stats.getMode() == mode).collect(Collectors.toList());}

    @Transactional
    public MatchStats updateMatchStats(MatchStats updatedMatchStats) {
        matchStatsList.removeIf(stats -> stats.getId().equals(updatedMatchStats.getId()));
        matchStatsList.add(updatedMatchStats);
        return updatedMatchStats;
    }

    @Transactional
    public void deleteMatchStatsById(Long id) {matchStatsList.removeIf(stats -> false);}

}
