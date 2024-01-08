package us.lsi.dp1.newcorporder.stats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.lsi.dp1.newcorporder.stats.payload.response.MatchStatsView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchStatsController {

    private final MatchStatsService matchStatsService;

    public MatchStatsController(MatchStatsService matchStatsService) {
        this.matchStatsService = matchStatsService;
    }

    @GetMapping("/{match}/stats")
    public MatchStatsView getStats(@PathVariable String match) {
        return matchStatsService.getStats(match);
    }

    @GetMapping("/user/{userid}/stats")
    public List<MatchStatsView> getStatsByPlayer(@PathVariable Integer userid) {
        return matchStatsService.getAllStatsByPlayer(userid);
    }

}
