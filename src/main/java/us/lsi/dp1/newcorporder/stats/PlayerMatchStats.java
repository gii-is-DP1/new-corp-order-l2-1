package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "player_match_stats")
@Entity
public class PlayerMatchStats extends BaseEntity {

    public PlayerMatchStats() {}
    public PlayerMatchStats(MatchStats matchStats, MatchResult result, Integer totalVP, Integer timesPlotted,
                            Integer timesInfiltrated, Integer timesTakenOver, Set<CompanyPlayerMatchStats> companyStats,
                            Set<ConglomeratePlayerMatchStats> conglomerateStats, Set<ConsultantPlayerMatchStats> consultantStats) {
        this.matchStats = matchStats;
        this.result = result;
        this.totalVP = totalVP;
        this.timesPlotted = timesPlotted;
        this.timesInfiltrated = timesInfiltrated;
        this.timesTakenOver = timesTakenOver;
        this.companyStats = companyStats;
        this.conglomerateStats = conglomerateStats;
        this.consultantStats = consultantStats;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_stats_id")
    @NotNull private MatchStats matchStats;

    @Enumerated(EnumType.STRING)
    @NotNull private MatchResult result;

    @NotNull private Integer totalVP;

    @NotNull private Integer timesPlotted;

    @NotNull private Integer timesInfiltrated;

    @NotNull private Integer timesTakenOver;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_match_stats_id")
    private Set<CompanyPlayerMatchStats> companyStats;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_match_stats_id")
    private Set<ConglomeratePlayerMatchStats> conglomerateStats;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_match_stats_id")
    private Set<ConsultantPlayerMatchStats> consultantStats;

}
