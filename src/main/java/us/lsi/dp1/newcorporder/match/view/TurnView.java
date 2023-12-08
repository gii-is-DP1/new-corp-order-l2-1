package us.lsi.dp1.newcorporder.match.view;

import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@Getter
@Builder
public class TurnView {

    private final Integer player;
    private final boolean finalRound;

    public static TurnView of(Match match) {
        return TurnView.builder()
            .player(getTurnPlayer(match))
            .finalRound(match.getTurnSystem().getLastPlayerBeforeMatchEnds() != null)
            .build();
    }

    private static Integer getTurnPlayer(Match match) {
        MatchPlayer player = match.getTurnSystem().getCurrentPlayer();
        return player != null ? player.getPlayerId() : null;
    }
}
