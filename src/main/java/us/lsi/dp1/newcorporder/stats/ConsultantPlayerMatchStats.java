package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.model.BaseEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "consultant_player_match_stats")
@Entity
public class ConsultantPlayerMatchStats extends BaseEntity {

    public ConsultantPlayerMatchStats() {}
    public ConsultantPlayerMatchStats(ConsultantType consultantType, Integer used) {
        this.consultantType = consultantType;
        this.used = used;
    }
    @Enumerated(EnumType.STRING)
    @NotNull private ConsultantType consultantType;

    @NotNull private Integer used;
}
