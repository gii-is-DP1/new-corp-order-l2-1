package us.lsi.dp1.newcorporder.friendship;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

@RestController
@RequestMapping("/api/v1/users/friendships")
public class FriendshipController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    public FriendshipController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @PostMapping("/requests/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void requestFriendship(@PathVariable String username) {
        User receiver = userService.findUser(username);
        RestPreconditions.checkNotNull(receiver, "User", "user", username);

        friendshipService.requestFriendship(userService.findCurrentUser(), receiver);
    }

    @PostMapping("/requests/{username}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptFriendship(@PathVariable String username) {
        User sender = userService.findUser(username);
        RestPreconditions.checkNotNull(sender, "User", "user", username);

        friendshipService.acceptFriendship(userService.findCurrentUser(), sender);
    }

    @DeleteMapping("/requests/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void denyFriendship(@PathVariable String username) {
        User sender = userService.findUser(username);
        RestPreconditions.checkNotNull(sender, "User", "user", username);

        friendshipService.denyFriendship(userService.findCurrentUser(), sender);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@PathVariable String username) {
        User friend = userService.findUser(username);
        RestPreconditions.checkNotNull(friend, "User", "user", username);

        friendshipService.removeFriend(userService.findCurrentUser(), friend);
    }
}
