package us.lsi.dp1.newcorporder.stats.matchStats;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStatsRepository;

import java.util.List;
import java.util.Optional;


@Service
public class MatchStatsService {
    @Autowired
    private final MatchStatsRepository matchStatsRepository;

    public MatchStatsService(MatchStatsRepository matchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
    }

    @Transactional(readOnly = true)
    public List<MatchStats> findAll() {
        return matchStatsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MatchStats findById(Integer id) {
        return matchStatsRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MatchStats", "id", id));
    }

    @Transactional
    public MatchStats save(MatchStats matchStats) throws DataAccessException {
        return matchStatsRepository.save(matchStats);
    }

    public MatchStats generateMatchStats(Match match) {return MatchStats.createMatchStats(match);}

    @Transactional
    public MatchStats updateMatchStats(MatchStats updatedMatchStats, Integer idToUpdate) {
        MatchStats toUpdate = findById(idToUpdate);
        BeanUtils.copyProperties(updatedMatchStats, toUpdate, "id");
        matchStatsRepository.save(toUpdate);
        return toUpdate;
    }

    @Transactional
    public void deleteMatchStats(Integer id) {
        MatchStats toDelete = findById(id);
        matchStatsRepository.delete(toDelete);
    }

    @Transactional(readOnly = true)
    public List<MatchStats> getMatchStatsByMode(MatchMode mode) {return matchStatsRepository.findByMode(mode);}
    @Transactional(readOnly = true)
    public Optional<MatchStats> getMatchStatsById(Integer id) {
        return matchStatsRepository.findById(id);
    }

}
