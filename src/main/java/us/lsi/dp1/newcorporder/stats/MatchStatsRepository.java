package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchStatsRepository extends CrudRepository<MatchStats, Integer> {

    // for some reason jpa repositories do not accept Instant sorting, so manual query
    @Query("SELECT matchStats FROM MatchStats matchStats ORDER BY matchStats.startTime DESC")
    List<MatchStats> findAllSortedByStartTimeDesc(Pageable pageable);

    Optional<MatchStats> findByCode(String matchCode);

    boolean existsByCode(String matchCode);

    record MatchMetrics(double averageDuration, long minDuration, long maxDuration, double averageMaxPlayers) {}

    @Query("""
            SELECT new us.lsi.dp1.newcorporder.stats.MatchStatsRepository$MatchMetrics(
                AVG(duration), MIN(duration), MAX(duration), AVG(maxPlayers)
            ) FROM (
                SELECT
                CAST(DATEDIFF(second, matchStats.startTime, matchStats.endTime) AS LONG) AS duration,
                matchStats.maxPlayers AS maxPlayers
                FROM MatchStats matchStats
            )
        """)
    MatchMetrics calculateMatchMetrics();

}
