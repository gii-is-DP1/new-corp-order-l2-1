package us.lsi.dp1.newcorporder.match.view;

import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@Data
@Builder
public class OpponentView {

    public static OpponentView of(MatchPlayer player, MatchState state) {
        OpponentViewBuilder builder = OpponentView.builder()
            .playerId(player.getPlayerId())
            .username(player.getUsername())
            .picture(player.getPicture());

        if (state != MatchState.WAITING) {
            builder
                .handSize(player.getHand().size())
                .headquarter(player.getHeadquarter());
        }
        return builder.build();
    }

    private final int playerId;
    private final String username;
    private final String picture;
    private final boolean online;

    private final int handSize;
    private final Headquarter headquarter;
}
