package us.lsi.dp1.newcorporder.stats;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
        summary = "Get the stats of the given match",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The match's stats"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Match not found"
    )
    @GetMapping("/matches/{match}/stats")
    public MatchStatsView getStats(@PathVariable("match") String match) {
        return matchStatsService.getStats(match);
    }

    @Operation(
        summary = "Get the metrics of the game",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The game's metrics"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Full authentication is required to access this resource"
    )
    @GetMapping("/metrics")
    public GameMetricsResponse getMetrics() {
        return matchStatsService.getMetrics();
    }

}
