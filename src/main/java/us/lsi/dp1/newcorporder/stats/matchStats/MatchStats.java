package us.lsi.dp1.newcorporder.stats.matchStats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStatsService;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "match_stats")
@Entity
public class MatchStats extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull private MatchMode mode;

    @NotNull private Instant startedAt;

    @NotNull private Instant endedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchStats")
    private Set<PlayerMatchStats> playerMatchStats;



    public MatchStats() {}
    public MatchStats(MatchMode mode, Instant startedAt, Instant endedAt) {
        this.mode = mode;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.playerMatchStats = new HashSet<>();
    }


    public static MatchStats createMatchStats(Match match ) {
        MatchMode mode = match.getMatchMode();
        Instant startedAt = match.determineStartTime();
        Instant endedAt = Instant.now();
        MatchStats matchStats = new MatchStats(mode, startedAt, endedAt);
        for (MatchPlayer player : match.getPlayers()) {
            PlayerMatchStats playerMatchStats = PlayerMatchStats.createPlayerMatchStats(match, player);
            matchStats.addPlayerMatchStats(playerMatchStats);
        }
        return matchStats;
    }

        public void addPlayerMatchStats (PlayerMatchStats playerMatchStats){
            this.playerMatchStats.add(playerMatchStats);
        }

    }
