package us.lsi.dp1.newcorporder.match.view;

import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@Data
@Builder
public class OpponentView {

    public static OpponentView of(MatchPlayer player) {
        return OpponentView.builder()
            .playerId(player.getPlayerId())
            .username(player.getUsername())
            .picture(player.getPicture())
            .handSize(player.getHand().size())
            .headquarter(player.getHeadquarter())
            .build();
    }

    private final int playerId;
    private final String username;
    private final Integer picture;
    private final boolean online;

    private final int handSize;
    private final Headquarter headquarter;
}
