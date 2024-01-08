package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchStatsRepository extends CrudRepository<MatchStats, Integer> {

    Optional<MatchStats> findByCode(String matchCode);
    @Override
    List<MatchStats> findAll();

}
