package us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStatsRepository;

import java.util.List;

@Service
public class ConglomeratePlayerMatchStatsService {

    private final ConglomeratePlayerMatchStatsRepository repository;

    @Autowired
    public ConglomeratePlayerMatchStatsService(ConglomeratePlayerMatchStatsRepository repository) {
        this.repository = repository;
    }

    public List<ConglomeratePlayerMatchStats> getStatsByConglomerate(Conglomerate conglomerate) {
        return repository.findByConglomerate(conglomerate);
    }

    public List<ConglomeratePlayerMatchStats> getStatsBySharesGreaterThan(int minShares) {
        return repository.findBySharesGreaterThan(minShares);
    }

    public List<ConglomeratePlayerMatchStats> getStatsByAgentsGreaterThan(int minAgents) {
        return repository.findByAgentsGreaterThan(minAgents);
    }
    public List<ConglomeratePlayerMatchStats> getStatsByConglomerateAndSharesGreaterThan(Conglomerate conglomerate, int minShares) {
        return repository.findByConglomerateAndSharesGreaterThan(conglomerate, minShares);
    }




}
