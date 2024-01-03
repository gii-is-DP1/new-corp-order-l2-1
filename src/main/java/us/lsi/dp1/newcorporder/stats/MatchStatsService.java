package us.lsi.dp1.newcorporder.stats;

import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.view.MatchSummary;
import us.lsi.dp1.newcorporder.player.PlayerService;

import java.util.List;

@Service
public class MatchStatsService {

    private final PlayerService playerService;

    public MatchStatsService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public MatchStats createMatchStats(Match match, MatchSummary matchSummary) {
        List<PlayerMatchStats> playerStats = match.getPlayers().stream()
            .map(player -> PlayerMatchStats.create(match, matchSummary, playerService.findById(player.getPlayerId())))
            .toList();

        return MatchStats.create(match, playerStats);
    }
}
