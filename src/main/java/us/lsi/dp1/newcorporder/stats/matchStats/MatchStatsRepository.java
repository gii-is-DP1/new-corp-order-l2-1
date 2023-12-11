package us.lsi.dp1.newcorporder.stats.matchStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStats;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchStatsRepository extends JpaRepository<MatchStats, Integer> {

    List<MatchStats> findByMode(MatchMode mode);

    MatchStats findByMatch(Match match);

    MatchStats save(MatchStats matchStats);

    @Override
    Optional<MatchStats> findById(Integer integer);

    List<MatchStats> findByEndedAtIsNotNull();

    List<MatchStats> findByModeAndEndedAtIsNotNull(MatchMode mode);

    @Query("SELECT ms FROM MatchStats ms WHERE ms.mode = :mode AND ms.endedAt IS NOT NULL ORDER BY ms.startedAt DESC")
    List<MatchStats> findFinishedMatchesByModeOrderByStartedAtDesc(MatchMode mode);

}
