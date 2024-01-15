package us.lsi.dp1.newcorporder.stats.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

@Data
@SuperBuilder
@NoArgsConstructor
public class PlayerRankingResponse {

    private UserView user;
    private long amount;

    public PlayerRankingResponse(String username, Integer picture, long amount) {
        this.user = UserView.builder().username(username).picture(picture).build();
        this.amount = amount;
    }
}
