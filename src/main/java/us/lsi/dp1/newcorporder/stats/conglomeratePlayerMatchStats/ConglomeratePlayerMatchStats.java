package us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.model.BaseEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "conglomerate_player_match_stats")
@Entity
public class ConglomeratePlayerMatchStats extends BaseEntity {

    public ConglomeratePlayerMatchStats() {}
    public ConglomeratePlayerMatchStats(Conglomerate conglomerate, Integer shares, Integer agents) {
        this.conglomerate = conglomerate;
        this.shares = shares;
        this.agents = agents;
    }

    @Enumerated(EnumType.STRING)
    @NotNull private Conglomerate conglomerate;

    @NotNull private Integer shares;

    @NotNull private Integer agents;


}
