package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.player.Player;

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
    @NotNull private Player player;

    @Enumerated(EnumType.STRING)
    @NotNull private MatchResult result;

    @NotNull private Integer totalVictoryPoints;

    @NotNull private Integer timesPlotted;

    @NotNull private Integer timesInfiltrated;

    @NotNull private Integer timesTakenOver;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerMatchStats")
    private List<AbilityStats> abilityStats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerMatchStats")
    private List<ConglomerateStats> conglomerateStats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerMatchStats")
    private List<ConsultantStats> consultantStats;

}
