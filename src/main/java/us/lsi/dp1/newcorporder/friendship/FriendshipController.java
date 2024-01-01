package us.lsi.dp1.newcorporder.friendship;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.friendship.payload.FriendshipView;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/friendships")
public class FriendshipController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    public FriendshipController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @Operation(
        summary = "Get the friendships of the given user",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The user's friendships"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @GetMapping("/{username}")
    public List<FriendshipView> getFriendships(@PathVariable String username) {
        User user = userService.findUser(username);
        RestPreconditions.checkNotNull(user, "User", "user", username);

        return friendshipService.getFriendships(user);
    }

    @Operation(
        summary = "Remove the given friend",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Friend removed successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "You are not friends with the given user"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@PathVariable String username) {
        User friend = userService.findUser(username);
        RestPreconditions.checkNotNull(friend, "User", "user", username);

        friendshipService.removeFriend(userService.findCurrentUser(), friend);
    }

    @Operation(
        summary = "Send a friendship request to the given user",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Friendship request sent successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "The given user is already friends with you or there is already an outgoing or incoming friendship " +
                      "request with them."
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @PostMapping("/requests/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void requestFriendship(@PathVariable String username) {
        User receiver = userService.findUser(username);
        RestPreconditions.checkNotNull(receiver, "User", "user", username);

        friendshipService.requestFriendship(userService.findCurrentUser(), receiver);
    }

    @Operation(
        summary = "Accept the friend request from the given user",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Friend request accepted successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "The given user did not send you any friend request"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @PostMapping("/requests/{username}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptFriendship(@PathVariable String username) {
        User sender = userService.findUser(username);
        RestPreconditions.checkNotNull(sender, "User", "user", username);

        friendshipService.acceptFriendship(userService.findCurrentUser(), sender);
    }

    @Operation(
        summary = "Deny the friend request from the given user",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Friend request denied successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "The given user did not send you any friend request"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @DeleteMapping("/requests/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void denyFriendship(@PathVariable String username) {
        User sender = userService.findUser(username);
        RestPreconditions.checkNotNull(sender, "User", "user", username);

        friendshipService.denyFriendship(userService.findCurrentUser(), sender);
    }
}
