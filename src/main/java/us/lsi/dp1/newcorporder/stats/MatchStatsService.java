package us.lsi.dp1.newcorporder.stats;

import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.player.PlayerService;
import us.lsi.dp1.newcorporder.stats.payload.response.MatchStatsView;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStats;

import java.util.List;

@Service
public class MatchStatsService {

    private final PlayerService playerService;
    private final MatchStatsRepository matchStatsRepository;

    public MatchStatsService(PlayerService playerService, MatchStatsRepository matchStatsRepository) {
        this.playerService = playerService;
        this.matchStatsRepository = matchStatsRepository;
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

    public List<MatchStatsView> getAllStats() {
        return matchStatsRepository.findAll().stream()
            .map(MatchStatsView::create)
            .toList();
    }

    public List<MatchStatsView> getAllStatsByPlayer(Integer playerId) {
        return matchStatsRepository.findAll().stream()
            .filter(matchStats -> matchStats.getPlayerMatchStats().stream()
                .anyMatch(playerMatchStats -> playerMatchStats.getPlayer().getId().equals(playerId)))
            .map(MatchStatsView::create)
            .toList();
    }



}
