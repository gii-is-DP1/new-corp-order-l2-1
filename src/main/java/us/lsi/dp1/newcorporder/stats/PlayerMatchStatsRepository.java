package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats,Integer> {

    List<PlayerMatchStats> findAll();

    Set<PlayerMatchStats> findByMatchId(Integer integer);




}

