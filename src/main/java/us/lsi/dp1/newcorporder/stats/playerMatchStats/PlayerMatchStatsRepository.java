package us.lsi.dp1.newcorporder.stats.playerMatchStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStats;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats,Integer> {

    List<PlayerMatchStats> findAll();

    Set<PlayerMatchStats> findByMatchId(Integer integer);




}

