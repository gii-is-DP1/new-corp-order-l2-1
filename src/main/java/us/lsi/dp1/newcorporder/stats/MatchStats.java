package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "match_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class MatchStats extends BaseEntity {

    public static MatchStats create(Match match, List<PlayerMatchStats> playerStats) {
        return MatchStats.builder()
            .mode(match.getMode())
            .maxPlayers(match.getMaxPlayers())
            .startTime(match.getStartTime())
            .endTime(match.getEndTime())
            .playerMatchStats(playerStats)
            .build();
    }

    @Enumerated(EnumType.STRING)
    @NotNull private MatchMode mode;

    @NotNull private Integer maxPlayers;

    @NotNull private Instant startTime;

    @NotNull private Instant endTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchStats")
    private List<PlayerMatchStats> playerMatchStats;
}
