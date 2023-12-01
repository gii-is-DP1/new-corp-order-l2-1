package us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStats;

import java.util.List;
@Repository
public interface ConglomeratePlayerMatchStatsRepository extends JpaRepository<ConglomeratePlayerMatchStats, Integer> {

    List<ConglomeratePlayerMatchStats> findByConglomerate(Conglomerate conglomerate);

    @Query("SELECT c FROM ConglomeratePlayerMatchStats c WHERE c.shares > :minShares")
    List<ConglomeratePlayerMatchStats> findBySharesGreaterThan(int minShares);

    List<ConglomeratePlayerMatchStats> findByAgentsGreaterThan(int minAgents);

    @Query("SELECT c FROM ConglomeratePlayerMatchStats c WHERE c.conglomerate = :conglomerate AND c.shares > :minShares")
    List<ConglomeratePlayerMatchStats> findByConglomerateAndSharesGreaterThan(Conglomerate conglomerate, int minShares);

//

}
