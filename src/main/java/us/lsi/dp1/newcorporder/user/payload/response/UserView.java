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

    public static UserView minimal(User user) {
        return UserView.of(user, true, false);
    }

    public static UserView expanded(User user, User viewer) {
        return UserView.of(user, false, user.equals(viewer) || viewer.hasAnyAuthority("ADMIN"));
    }

    public static UserView of(User user, boolean minimal, boolean includeSensitiveData) {
        UserViewBuilder viewBuilder = UserView.builder()
            .username(user.getUsername())
            .picture(user.getPicture());

        if (!minimal) {
            viewBuilder
                .authority(user.getAuthority().getName())
                .firstSeen(user.getFirstSeen())
                .lastSeen(user.getLastSeen())
                .friends(user.getFriendships().stream().map(FriendshipView::of).toList());
        }

        if (includeSensitiveData) {
            viewBuilder
                .email(user.getEmail())
                .sentFriendshipRequests(user.getSentFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .receivedFriendshipRequests(user.getReceivedFriendshipRequests().stream().map(request -> FriendshipView.of(request, user)).toList())
                .notifications(user.getNotifications());
        }

        return viewBuilder.build();
    }

    private String username;
    private String email;
    private String authority;
    private Integer picture;

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
}
