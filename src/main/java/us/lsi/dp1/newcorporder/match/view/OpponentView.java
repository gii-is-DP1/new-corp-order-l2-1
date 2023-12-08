package us.lsi.dp1.newcorporder.match.view;

import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@Getter
@Builder
public class OpponentView {

    private final int playerId;
    private final String username;
    private final String picture;
    private final boolean online;

    private final int handSize;
    private final Headquarter headquarter;

    public static OpponentView of(MatchPlayer player) {
        return OpponentView.builder()
            .playerId(player.getPlayerId())
            .username(player.getUsername())
            .picture(player.getPicture())
            .handSize(player.getHand().size())
            .headquarter(player.getHeadquarter())
            .build();
    }
}
