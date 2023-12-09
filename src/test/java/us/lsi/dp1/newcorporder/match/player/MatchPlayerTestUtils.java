package us.lsi.dp1.newcorporder.match.player;

import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.user.User;

public class MatchPlayerTestUtils {

    public static MatchPlayer playerWithId(int playerId) {
        Player player = Player.builder()
            .id(playerId)
            .user(User.builder().build())
            .build();
        return MatchPlayer.create(player);
    }
}
