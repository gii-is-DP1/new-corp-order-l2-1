package us.lsi.dp1.newcorporder.match;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.Authenticated;
import us.lsi.dp1.newcorporder.bind.FromPathVariable;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.match.view.MatchView;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@SecurityRequirement(name = "bearerAuth")
@ApiResponse(
    responseCode = "401",
    description = "Authentication information is missing or invalid"
)
@Tag(name = "Match", description = "The Match API")
public class MatchController {

    private final UserService userService;
    private final MatchService matchService;

    public MatchController(UserService userService, MatchService matchService) {
        this.userService = userService;
        this.matchService = matchService;
    }

    @Operation(
        summary = "Create a private match",
        description = "Create a private match",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The created private match"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchAssignmentResponse createPrivateMatch(@Authenticated Player player,
                                                      @RequestParam("mode") MatchMode mode,
                                                      @RequestParam("maxPlayers") int maxPlayers) {
        return matchService.createPrivateMatch(player, mode, maxPlayers);
    }

    @Operation(
        summary = "Invite a friend to the given match",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Invitation sent"
    )
    @PostMapping("/{match}/invite")
    @ResponseStatus(HttpStatus.CREATED)
    public void inviteFriend(@Authenticated Player player,
                             @RequestParam("target") List<String> friends,
                             @FromPathVariable Match match) {
        friends.forEach(friendUsername -> {
            User friend = userService.findUser(friendUsername);
            RestPreconditions.checkNotNull(friend, "User", "username", friendUsername);

            matchService.inviteFriend(player.getUser(), friend, match);
        });
    }

    @Operation(
        summary = "Select quick play",
        description = "Select quick play",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The selected quick play"
    )
    @PostMapping("/quick")
    public MatchAssignmentResponse quickPlay(@Authenticated Player player,
                                             @RequestParam("mode") MatchMode mode,
                                             @RequestParam("maxPlayers") int maxPlayers) {
        return matchService.quickPlay(player, mode, maxPlayers);
    }

    @Operation(
        summary = "Get a match",
        description = "Get a match",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The requested match view"
    )
    @GetMapping("/{match}")
    public MatchView getMatch(@Authenticated Player player, @FromPathVariable Match match) {
        return matchService.getMatchView(player, match);
    }

    @Operation(
        summary = "Join a match",
        description = "Join a match",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The joined match"
    )
    @PostMapping("/{match}/join")
    public MatchAssignmentResponse joinMatch(@Authenticated Player player, @FromPathVariable Match match) {
        return matchService.join(player, match);
    }

    @Operation(
        summary = "Leave a match",
        description = "Leave a match",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The left match"
    )
    @PostMapping("/{match}/leave")
    public void leaveMatch(@Authenticated Player player, @FromPathVariable Match match) {
        matchService.leave(player, match);
    }

    @Operation(
        summary = "Force a match to start",
        description = "Force a match to start",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The started match"
    )
    @PostMapping("/{match}/start")
    public void forceStartMatch(@Authenticated Player player, @FromPathVariable Match match) {
        matchService.forceStart(player, match);
    }
}
