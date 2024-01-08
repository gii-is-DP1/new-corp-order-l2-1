package us.lsi.dp1.newcorporder.stats.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Multiset;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStats;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerMatchStatsView {

    public static PlayerMatchStatsView reduced(PlayerMatchStats playerMatchStats) {
        return PlayerMatchStatsView.of(playerMatchStats)
            .build();
    }

    public static PlayerMatchStatsView expanded(PlayerMatchStats playerMatchStats) {
        return PlayerMatchStatsView.of(playerMatchStats)
            .matchCode(playerMatchStats.getMatchStats().getCode())
            .result(playerMatchStats.getResult())
            .build();
    }

    private static PlayerMatchStatsViewBuilder<?, ?> of(PlayerMatchStats playerMatchStats) {
        return PlayerMatchStatsView.builder()
            .user(UserView.reduced(playerMatchStats.getPlayer().getUser()))
            .victoryPoints(playerMatchStats.getTotalVictoryPoints())
            .timesPlotted(playerMatchStats.getTimesPlotted())
            .timesInfiltrated(playerMatchStats.getTimesInfiltrated())
            .timesTakenOver(playerMatchStats.getTimesTakenOver())
            .abilityUses(playerMatchStats.getAbilityUses())
            .shareUses(playerMatchStats.getShareUses())
            .agentUses(playerMatchStats.getAgentUses())
            .consultantUses(playerMatchStats.getConsultantUses());
    }

    private UserView user;

    // Only in expanded mode
    private String matchCode;
    private MatchResult result;

    private Integer victoryPoints;
    private Integer timesPlotted;
    private Integer timesInfiltrated;
    private Integer timesTakenOver;

    private Multiset<CompanyType> abilityUses;
    private Multiset<Conglomerate> shareUses;
    private Multiset<Conglomerate> agentUses;
    private Multiset<ConsultantType> consultantUses;

}
