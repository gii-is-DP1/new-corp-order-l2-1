package us.lsi.dp1.newcorporder.user.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.friendship.payload.FriendshipView;
import us.lsi.dp1.newcorporder.notification.Notification;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {

    public static UserView reduced(User user) {
        return UserView.of(user).build();
    }

    public static UserView expanded(User user, User viewer) {
        UserViewBuilder builder = UserView.of(user)
            .authority(user.getAuthority().getName())
            .firstSeen(user.getFirstSeen())
            .lastSeen(user.getLastSeen())
            .friends(user.getFriendships().stream().map(FriendshipView::of).toList());

        if (user.equals(viewer) || viewer.hasAnyAuthority("ADMIN")) {
            builder
                .email(user.getEmail())
                .sentFriendshipRequests(user.getSentFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .receivedFriendshipRequests(user.getReceivedFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .notifications(user.getNotifications());
        }

        return builder.build();
    }

    private static UserViewBuilder of(User user) {
        return UserView.builder()
            .username(user.getUsername())
            .picture(user.getPicture());
    }

    private String username;
    private String email;
    private String authority;
    private String picture;

    private Instant firstSeen;
    private Instant lastSeen;

    private List<FriendshipView> friends;
    private List<FriendshipView> sentFriendshipRequests;
    private List<FriendshipView> receivedFriendshipRequests;

    private Set<Notification> notifications;

    @JsonInclude
    public String getPicture() {
        return picture;
    }
}
