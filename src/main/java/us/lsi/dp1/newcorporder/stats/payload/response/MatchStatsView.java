package us.lsi.dp1.newcorporder.stats.payload.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.MatchStats;

import java.time.Instant;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class MatchStatsView {

    public static MatchStatsView create(MatchStats matchStats) {
        return MatchStatsView.builder()
            .code(matchStats.getCode())
            .mode(matchStats.getMode())
            .maxPlayers(matchStats.getMaxPlayers())
            .startTime(matchStats.getStartTime())
            .endTime(matchStats.getEndTime())
            .winners(matchStats.getPlayerMatchStats().stream()
                .filter(stats -> stats.getResult() != MatchResult.LOST)
                .map(stats -> stats.getPlayer().getUser().getUsername())
                .toList())
            .players(matchStats.getPlayerMatchStats().stream().map(PlayerMatchStatsView::reduced).toList())
            .build();
    }

    private String code;
    private MatchMode mode;
    private Integer maxPlayers;
    private Instant startTime;
    private Instant endTime;
    private List<String> winners;

    private List<PlayerMatchStatsView> players;

}
