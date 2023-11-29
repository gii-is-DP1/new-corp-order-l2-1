package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "match_stats")
@Entity
public class MatchStats extends BaseEntity {

    public MatchStats() {}
    public MatchStats(MatchMode mode, Instant startedAt, Instant endedAt) {
        this.mode = mode;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.playerMatchStats = new HashSet<>();
    }

    public void addPlayerMatchStats(PlayerMatchStats playerMatchStats) {
        this.playerMatchStats.add(playerMatchStats);
    }

    @Enumerated(EnumType.STRING)
    @NotNull private MatchMode mode;

    @NotNull private Instant startedAt;

    @NotNull private Instant endedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchStats")
    private Set<PlayerMatchStats> playerMatchStats;
}
