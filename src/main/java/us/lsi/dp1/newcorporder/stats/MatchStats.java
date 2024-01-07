package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "match_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class MatchStats extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull private MatchMode mode;

    @NotNull private Instant startedAt;

    @NotNull private Instant endedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchStats")
    private Set<PlayerMatchStats> playerMatchStats;
}
