package us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStats;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyPlayerMatchStatsRepository extends JpaRepository<CompanyPlayerMatchStats, Integer> {

    List<CompanyPlayerMatchStats> findByCompanyType(CompanyType companyType);

    Long countByCompanyType(CompanyType companyType);

    @Query("SELECT c FROM CompanyPlayerMatchStats c WHERE c.companyType = :companyType AND c.abilityUsed > :abilityUsedThreshold")
    List<CompanyPlayerMatchStats> findByCompanyTypeAndAbilityUsedGreaterThan(
        @Param("companyType") CompanyType companyType,
        @Param("abilityUsedThreshold") Integer abilityUsedThreshold
    );

    Optional<CompanyPlayerMatchStats> findById(Integer id);

    List<CompanyPlayerMatchStats> findByOrderByAbilityUsedDesc();


}

