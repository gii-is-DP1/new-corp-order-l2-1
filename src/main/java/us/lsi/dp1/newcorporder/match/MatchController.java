package us.lsi.dp1.newcorporder.match;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.Authenticated;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.player.Player;

@RestController
@RequestMapping("/api/v1/matches")
@SecurityRequirement(name = "bearerAuth")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchAssignmentResponse createPrivateMatch(@Authenticated Player player,
                                                      @RequestParam("mode") MatchMode mode,
                                                      @RequestParam("maxPlayers") int maxPlayers) {
        return matchService.createPrivateMatch(player, mode, maxPlayers);
    }

    @PostMapping("/quick")
    public MatchAssignmentResponse quickPlay(@Authenticated Player player,
                                             @RequestParam("mode") MatchMode mode,
                                             @RequestParam("maxPlayers") int maxPlayers) {
        return matchService.quick(player, mode, maxPlayers);
    }

    @PostMapping("/{match}/join")
    public MatchAssignmentResponse joinMatch(@Authenticated Player player, @FromPathVariable Match match) {
        return matchService.join(player, match);
    }

    @PostMapping("/{match}/leave")
    public void leaveMatch(@Authenticated Player player, @FromPathVariable Match match) {
        matchService.leave(player, match);
    }
}
