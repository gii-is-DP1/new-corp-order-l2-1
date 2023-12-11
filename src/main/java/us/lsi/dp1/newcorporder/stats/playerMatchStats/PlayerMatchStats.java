package us.lsi.dp1.newcorporder.stats.playerMatchStats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.stats.AbilityCalculator;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.consultantPlayerMatchStats.ConsultantPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStats;

import java.util.HashSet;
import java.util.List;
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

    public static PlayerMatchStats createPlayerMatchStats(Match match, MatchPlayer player) {
        List<MatchPlayer> winners = match.getWinners();

        MatchResult result;
        if (winners.contains(player)) {
            result = (winners.size() > 1) ? MatchResult.TIED : MatchResult.WON;
        } else {
            result = MatchResult.LOST;
        }

        Integer totalVP = match.calculateVictoryPoints().count(player);
        Integer timesPlotted = player.calculateTimesPlotted();
        Integer timesInfiltrated = player.calculateTimesInfiltrated();
        Integer timesTakenOver = player.calculateTimesTakenOver();

        Set<CompanyPlayerMatchStats> companyStats = new HashSet<>();
        for (CompanyType companyType : CompanyType.values()) {
            Integer abilityUsed = AbilityCalculator.calculateAbilityUsed(companyType);
            companyStats.add(new CompanyPlayerMatchStats(companyType, abilityUsed));
        }

        Set<ConglomeratePlayerMatchStats> conglomerateStats = new HashSet<>();
        for (Conglomerate conglomerate : Conglomerate.values()) {
            Integer shares;
            shares = player.getHeadquarter().getTotalConglomeratesShares(conglomerate);
            Integer agents = player.getHeadquarter().getAgentsCaptured(conglomerate);
            conglomerateStats.add(new ConglomeratePlayerMatchStats(conglomerate, shares, agents));
        }

        Set<ConsultantPlayerMatchStats> consultantStats = new HashSet<>();
        for (ConsultantType consultantType : ConsultantType.values()) {
            Integer used = null; // TODO: calculateConsultantUsed(match, player, consultantType);
            consultantStats.add(new ConsultantPlayerMatchStats(consultantType, used));
        }

        return new PlayerMatchStats(
            null, result, totalVP, timesPlotted, timesInfiltrated, timesTakenOver,
            companyStats, conglomerateStats, consultantStats);
    }



}
