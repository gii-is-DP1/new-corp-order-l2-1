package us.lsi.dp1.newcorporder.stats.consultantPlayerMatchStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import java.util.List;
import java.util.Optional;

public interface ConsultantPlayerMatchStatsRepository extends JpaRepository<ConsultantPlayerMatchStats, Integer> {

    List<ConsultantPlayerMatchStats> findByConsultantType(ConsultantType consultantType);

    Long countByConsultantType(ConsultantType consultantType);
    @Query("SELECT c FROM ConsultantPlayerMatchStats c WHERE c.consultantType = :consultantType AND c.used > :usedThreshold")
    List<ConsultantPlayerMatchStats> findByConsultantTypeAndUsedGreaterThan(
        @Param("consultantType") ConsultantType consultantType,
        @Param("usedThreshold") Integer usedThreshold
    );
    Optional<ConsultantPlayerMatchStats> findById(Integer id);

    List<ConsultantPlayerMatchStats> findByOrderByUsedDesc();

    //
}
