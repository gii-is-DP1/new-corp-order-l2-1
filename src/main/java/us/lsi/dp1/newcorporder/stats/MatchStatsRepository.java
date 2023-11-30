package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.player.Player;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchStatsRepository extends JpaRepository<MatchStats, Integer> {

    List<MatchStats> findByMode(MatchMode mode);

    @Override
    Optional<MatchStats> findById(Integer integer);


}
