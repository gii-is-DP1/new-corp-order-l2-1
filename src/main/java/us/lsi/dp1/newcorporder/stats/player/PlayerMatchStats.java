package us.lsi.dp1.newcorporder.stats.player;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.MatchStats;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "player_match_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class PlayerMatchStats extends BaseEntity {

    public static PlayerMatchStats create(Match match, MatchSummary matchSummary, Player player) {
        MatchPlayer matchPlayer = match.getPlayer(player.getId());

        return PlayerMatchStats.builder()
            .player(player)
            .result(matchSummary.getWinners().contains(matchPlayer)
                ? matchSummary.getWinners().size() > 1 ? MatchResult.TIED : MatchResult.WON
                : MatchResult.LOST)
            .totalVictoryPoints(matchSummary.getVictoryPoints().count(matchPlayer))
            .timesPlotted(matchPlayer.getTimesPlotted())
            .timesInfiltrated(matchPlayer.getTimesInfiltrated())
            .timesTakenOver(matchPlayer.getTimesTakenOver())
            .abilityStats(AbilityStats.create(matchPlayer))
            .conglomerateStats(ConglomerateStats.create(matchPlayer))
            .consultantStats(ConsultantStats.create(matchPlayer))
            .build();
    }

    @ManyToOne
    @NotNull private MatchStats matchStats;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull private Player player;

    @Enumerated(EnumType.STRING)
    @NotNull private MatchResult result;

    @NotNull private Integer totalVictoryPoints;

    @NotNull private Integer timesPlotted;

    @NotNull private Integer timesInfiltrated;

    @NotNull private Integer timesTakenOver;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "playerMatchStats")
    private List<AbilityStats> abilityStats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "playerMatchStats")
    private List<ConglomerateStats> conglomerateStats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "playerMatchStats")
    private List<ConsultantStats> consultantStats;

    public Multiset<CompanyType> getAbilityUses() {
        return this.abilityStats.stream()
            .collect(Multisets.toMultiset(AbilityStats::getType, AbilityStats::getTimesUsed, HashMultiset::create));
    }

    public Multiset<Conglomerate> getShareUses() {
        return this.conglomerateStats.stream()
            .collect(Multisets.toMultiset(ConglomerateStats::getConglomerate, ConglomerateStats::getSharesUsed, HashMultiset::create));
    }

    public Multiset<Conglomerate> getAgentUses() {
        return this.conglomerateStats.stream()
            .collect(Multisets.toMultiset(ConglomerateStats::getConglomerate, ConglomerateStats::getAgentsUsed, HashMultiset::create));
    }

    public Multiset<ConsultantType> getConsultantUses() {
        return this.consultantStats.stream()
            .collect(Multisets.toMultiset(ConsultantStats::getType, ConsultantStats::getTimesUsed, HashMultiset::create));
    }
}
