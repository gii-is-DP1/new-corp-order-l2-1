package us.lsi.dp1.newcorporder.match.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.stats.MatchStats;

@Data
@Builder
public class MatchResponse {

    public static MatchResponse of(Match match) {
        return MatchResponse.builder()
            .code(match.getCode())
            .mode(match.getMode())
            .players(match.getPlayers().size())
            .maxPlayers(match.getMaxPlayers())
            .state(match.getState())
            .build();
    }

    public static MatchResponse of(MatchStats matchStats) {
        return MatchResponse.builder()
            .code(matchStats.getCode())
            .mode(matchStats.getMode())
            .players(matchStats.getPlayerMatchStats().size())
            .maxPlayers(matchStats.getMaxPlayers())
            .state(MatchState.FINISHED)
            .build();
    }

    private final String code;
    private final MatchMode mode;
    private final int players;
    private final int maxPlayers;
    private final MatchState state;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getPlayers() {
        return players;
    }
}
