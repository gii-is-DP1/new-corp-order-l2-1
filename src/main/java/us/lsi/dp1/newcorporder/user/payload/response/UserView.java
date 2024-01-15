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
        return reduced(user, false);
    }

    public static UserView reduced(User user, boolean online) {
        return UserView.of(user, online).build();
    }

    public static UserView expanded(User user, User viewer) {
        return expanded(user, viewer, false);
    }

    public static UserView expanded(User user, User viewer, boolean online) {
        UserViewBuilder builder = UserView.of(user, online)
            .firstSeen(user.getFirstSeen())
            .lastSeen(user.getLastSeen())
            .friends(user.getFriendships().stream().map(FriendshipView::of).toList());

        if (user.equals(viewer) || viewer.hasAnyAuthority("ADMIN")) {
            builder
                .authority(user.getAuthority().getName())
                .email(user.getEmail())
                .sentFriendshipRequests(user.getSentFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .receivedFriendshipRequests(user.getReceivedFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .notifications(user.getNotifications());
        }

        return builder.build();
    }

    private static UserViewBuilder of(User user, boolean online) {
        return UserView.builder()
            .username(user.getUsername())
            .picture(user.getPicture())
            .online(online);
    }

    private String username;
    private String email;
    private String authority;
    private Integer picture;
    private boolean online;

    private Instant firstSeen;
    private Instant lastSeen;

    private List<FriendshipView> friends;
    private List<FriendshipView> sentFriendshipRequests;
    private List<FriendshipView> receivedFriendshipRequests;

    private Set<Notification> notifications;

    @JsonInclude()
    public Integer getPicture() {
              return picture;
    }
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean isOnline() {
        return online;
    }

}
