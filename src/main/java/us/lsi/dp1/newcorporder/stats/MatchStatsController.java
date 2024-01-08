package us.lsi.dp1.newcorporder.stats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.lsi.dp1.newcorporder.stats.payload.response.GameMetricsResponse;
import us.lsi.dp1.newcorporder.stats.payload.response.MatchStatsView;

@RestController
@RequestMapping("/api/v1")
public class MatchStatsController {

    private final MatchStatsService matchStatsService;

    public MatchStatsController(MatchStatsService matchStatsService) {
        this.matchStatsService = matchStatsService;
    }

    @GetMapping("/matches/{match}/stats")
    public MatchStatsView getStats(@PathVariable String match) {
        return matchStatsService.getStats(match);
    }

    @GetMapping("/metrics")
    public GameMetricsResponse getMetrics() {
        return matchStatsService.getMetrics();
    }
}
