package us.lsi.dp1.newcorporder.stats;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.player.PlayerService;
import us.lsi.dp1.newcorporder.stats.MatchStatsRepository.MatchMetrics;
import us.lsi.dp1.newcorporder.stats.payload.response.GameMetricsResponse;
import us.lsi.dp1.newcorporder.stats.payload.response.MatchStatsView;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStatsRepository;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStatsRepository.PlayerMetrics;

import java.util.List;

@Service
public class MatchStatsService {

    private final PlayerService playerService;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final MatchStatsRepository matchStatsRepository;

    public MatchStatsService(PlayerService playerService, PlayerMatchStatsRepository playerMatchStatsRepository, MatchStatsRepository matchStatsRepository) {
        this.playerService = playerService;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
        this.matchStatsRepository = matchStatsRepository;
    }

    public List<MatchStats> findLast(Pageable pageable) {
        return matchStatsRepository.findAllSortedByStartTimeDesc(pageable);
    }

    public MatchStats createMatchStats(Match match, MatchSummary matchSummary) {
        List<PlayerMatchStats> playerStats = match.getPlayers().stream()
            .map(player -> PlayerMatchStats.create(match, matchSummary, playerService.findById(player.getPlayerId())))
            .toList();

        return MatchStats.create(match, playerStats);
    }

    public MatchStatsView getStats(String matchCode) {
        return matchStatsRepository.findByCode(matchCode)
            .map(MatchStatsView::create)
            .orElseThrow(() -> new ResourceNotFoundException("match", "code", matchCode));
    }

    public GameMetricsResponse getMetrics() {
        MatchMetrics matchMetrics = matchStatsRepository.calculateMatchMetrics();
        PlayerMetrics playerMetrics = playerMatchStatsRepository.calculatePlayerMetrics();
        PlayerMatchStatsRepository.ActionMetrics actionMetrics = playerMatchStatsRepository.calculateActionMetrics();

        return GameMetricsResponse.builder()
            .averageDuration(matchMetrics.averageDuration())
            .minDuration(matchMetrics.minDuration())
            .maxDuration(matchMetrics.maxDuration())
            .averageMaxPlayers(matchMetrics.averageMaxPlayers())
            .averagePlayedMatches(playerMetrics.averagePlayedMatches())
            .minPlayedMatches(playerMetrics.minPlayedMatches())
            .maxPlayedMatches(playerMetrics.maxPlayedMatches())
            .timesPlotted(actionMetrics.timesPlotted())
            .timesInfiltrated(actionMetrics.timesInfiltrated())
            .timesTakenOver(actionMetrics.timesTakenOver())
            .build();
    }
    public List<MatchStatsView> getAllStats() {
        return matchStatsRepository.findAll().stream()
            .map(MatchStatsView::create)
            .toList();
    }



}
