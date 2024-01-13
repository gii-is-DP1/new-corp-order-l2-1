package us.lsi.dp1.newcorporder.friendship.payload;

import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.friendship.Friendship;
import us.lsi.dp1.newcorporder.friendship.FriendshipRequest;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

import java.time.Instant;

@Data
@Builder
public class FriendshipView {

    public static FriendshipView of(Friendship friendship) {
        return of(friendship, false);
    }

    public static FriendshipView of(Friendship friendship, boolean online) {
        return FriendshipView.builder()
            .since(friendship.getSince())
            .user(UserView.reduced(friendship.getFriend(), online))
            .build();
    }

    public static FriendshipView of(FriendshipRequest friendshipRequest, User user) {
        User receiver = friendshipRequest.getReceiver();

        return FriendshipView.builder()
            .since(friendshipRequest.getSentAt())
            .user(UserView.reduced(user.equals(receiver) ? friendshipRequest.getSender() : receiver))
            .build();
    }

    private Instant since;
    private UserView user;

}
