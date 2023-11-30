package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.MatchMode;

import java.util.List;

@Repository
public interface MatchStatsRepository extends JpaRepository<MatchStats, Integer> {

    List<MatchStats> findByMode(MatchMode mode);


}
