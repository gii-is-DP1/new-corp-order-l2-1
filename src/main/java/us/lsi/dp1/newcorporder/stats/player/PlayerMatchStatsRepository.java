package us.lsi.dp1.newcorporder.stats.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.MatchResult;

@Repository
public interface PlayerMatchStatsRepository extends CrudRepository<PlayerMatchStats, Integer> {

    Page<PlayerMatchStats> findByPlayerOrderByMatchStatsEndTimeDesc(Pageable pageable, Player player);

    int countByPlayer(Player player);

    int countByPlayerAndResult(Player player, MatchResult result);

}
