package us.lsi.dp1.newcorporder.stats.player;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.player.PlayerService;
import us.lsi.dp1.newcorporder.stats.Stat;
import us.lsi.dp1.newcorporder.stats.payload.response.PlayerMatchStatsView;
import us.lsi.dp1.newcorporder.stats.payload.response.PlayerRankingResponse;
import us.lsi.dp1.newcorporder.stats.payload.response.PlayerStatsResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerMatchStatsController {

    private final PlayerService playerService;
    private final PlayerMatchStatsService playerMatchStatsService;

    public PlayerMatchStatsController(PlayerService playerService, PlayerMatchStatsService playerMatchStatsService) {
        this.playerService = playerService;
        this.playerMatchStatsService = playerMatchStatsService;
    }

    @GetMapping("/{username}/lastMatches")
    @PageableAsQueryParam
    public List<PlayerMatchStatsView> getLastMatches(@PathVariable String username,
                                                     @PageableDefault Pageable pageable) {
        Player player = playerService.findByUsername(username);
        return playerMatchStatsService.getLastMatches(pageable, player).getContent();
    }

    @GetMapping("/{username}/stats")
    public PlayerStatsResponse getGeneralStats(@PathVariable String username) {
        Player player = playerService.findByUsername(username);
        return playerMatchStatsService.getStats(player);
    }

    @GetMapping("/ranking")
    public List<PlayerRankingResponse> getRanking(@RequestParam Stat stat,
                                                  @PageableDefault Pageable pageable) {
        return playerMatchStatsService.getRanking(stat, pageable);
    }
}
