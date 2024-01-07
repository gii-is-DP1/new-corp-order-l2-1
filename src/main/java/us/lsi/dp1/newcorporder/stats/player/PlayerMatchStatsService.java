package us.lsi.dp1.newcorporder.stats.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.payload.response.PlayerMatchStatsView;
import us.lsi.dp1.newcorporder.stats.payload.response.PlayerStatsResponse;

@Service
public class PlayerMatchStatsService {

    private final PlayerMatchStatsRepository playerMatchStatsRepository;

    public PlayerMatchStatsService(PlayerMatchStatsRepository playerMatchStatsRepository) {
        this.playerMatchStatsRepository = playerMatchStatsRepository;
    }

    public Page<PlayerMatchStatsView> getLastMatches(Pageable pageable, Player player) {
        return playerMatchStatsRepository.findByPlayerOrderByMatchStatsEndTimeDesc(pageable, player)
            .map(PlayerMatchStatsView::expanded);
    }

    public PlayerStatsResponse getStats(Player player) {
        int totalMatches = this.countMatches(player);
        int wins = this.countMatchesByResult(player, MatchResult.WON);
        int ties = this.countMatchesByResult(player, MatchResult.TIED);

        return PlayerStatsResponse.builder()
            .totalMatches(totalMatches)
            .wins(wins)
            .ties(ties)
            .loses(totalMatches - wins - ties)
            .build();
    }

    public int countMatches(Player player) {
        return playerMatchStatsRepository.countByPlayer(player);
    }

    public int countMatchesByResult(Player player, MatchResult result) {
        return playerMatchStatsRepository.countByPlayerAndResult(player, result);
    }
}
