package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "consultant_player_match_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class ConsultantPlayerMatchStats extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull private ConsultantType consultantType;

    @NotNull private Integer used;
}
