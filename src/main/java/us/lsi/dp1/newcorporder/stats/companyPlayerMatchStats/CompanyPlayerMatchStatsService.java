package us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStatsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyPlayerMatchStatsService {

    private final CompanyPlayerMatchStatsRepository statsRepository;

    @Autowired
    public CompanyPlayerMatchStatsService(CompanyPlayerMatchStatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public List<CompanyPlayerMatchStats> getStatsByCompanyType(CompanyType companyType) {return statsRepository.findByCompanyType(companyType);}

    public Long getCountByCompanyType(CompanyType companyType) {return statsRepository.countByCompanyType(companyType);}

    public List<CompanyPlayerMatchStats> getStatsByCompanyTypeAndAbilityUsedGreaterThan(CompanyType companyType, Integer abilityUsedThreshold) {
        return statsRepository.findByCompanyTypeAndAbilityUsedGreaterThan(companyType, abilityUsedThreshold);
    }

    public Optional<CompanyPlayerMatchStats> getStatsById(Long id) {return statsRepository.findById(id);}

    public List<CompanyPlayerMatchStats> getStatsOrderedByAbilityUsedDesc() {return statsRepository.findByOrderByAbilityUsedDesc();}


}
