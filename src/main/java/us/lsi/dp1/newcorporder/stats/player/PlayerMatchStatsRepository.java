package us.lsi.dp1.newcorporder.stats.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.MatchResult;

import java.util.List;

@Repository
public interface PlayerMatchStatsRepository extends CrudRepository<PlayerMatchStats, Integer> {

    Page<PlayerMatchStats> findByPlayerOrderByMatchStatsEndTimeDesc(Pageable pageable, Player player);

    int countByPlayer(Player player);

    int countByPlayerAndResult(Player player, MatchResult result);

    record PlayerMetrics(double averagePlayedMatches, long maxPlayedMatches, long minPlayedMatches) {}

    @Query("""
        SELECT new us.lsi.dp1.newcorporder.stats.player.PlayerMatchStatsRepository$PlayerMetrics(
            AVG(matches), MAX(matches), MIN(matches)
        ) FROM (
            SELECT
                player.id AS playerId,
                COUNT(playerMatchStats) AS matches
            FROM Player player JOIN PlayerMatchStats playerMatchStats
            ON player = playerMatchStats.player GROUP BY player
        )
        """)
    PlayerMetrics calculatePlayerMetrics();

    record ActionMetrics(long timesPlotted, long timesInfiltrated, long timesTakenOver) {}

    @Query("""
        SELECT new us.lsi.dp1.newcorporder.stats.player.PlayerMatchStatsRepository$ActionMetrics(
            SUM(playerMatchStats.timesPlotted),
            SUM(playerMatchStats.timesInfiltrated),
            SUM(playerMatchStats.timesTakenOver)
        ) FROM PlayerMatchStats playerMatchStats
        """)
    ActionMetrics calculateActionMetrics();

    @Override
    List<PlayerMatchStats> findAll();

    List<PlayerMatchStats> findByPlayerId(int playerId);
}
