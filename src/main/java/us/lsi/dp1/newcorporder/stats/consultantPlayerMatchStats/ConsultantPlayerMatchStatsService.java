package us.lsi.dp1.newcorporder.stats.consultantPlayerMatchStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultantPlayerMatchStatsService {

    private final ConsultantPlayerMatchStatsRepository statsRepository;

    @Autowired
    public ConsultantPlayerMatchStatsService(ConsultantPlayerMatchStatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public List<ConsultantPlayerMatchStats> getStatsByConsultantType(ConsultantType consultantType) {
        return statsRepository.findByConsultantType(consultantType);
    }

    public Long getCountByConsultantType(ConsultantType consultantType) {
        return statsRepository.countByConsultantType(consultantType);
    }

    public List<ConsultantPlayerMatchStats> getStatsByConsultantTypeAndUsedGreaterThan(ConsultantType consultantType, Integer usedThreshold) {
        return statsRepository.findByConsultantTypeAndUsedGreaterThan(consultantType, usedThreshold);
    }

    public Optional<ConsultantPlayerMatchStats> getStatsById(Integer id) {
        return statsRepository.findById(id);
    }

    public List<ConsultantPlayerMatchStats> getStatsOrderedByUsedDesc() {
        return statsRepository.findByOrderByUsedDesc();
    }


}
