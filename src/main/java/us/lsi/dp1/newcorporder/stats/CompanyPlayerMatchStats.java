package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.model.BaseEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "company_player_match_stats")
@Entity
public class CompanyPlayerMatchStats extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull private CompanyType companyType;

    @NotNull private Integer abilityUsed;
}
