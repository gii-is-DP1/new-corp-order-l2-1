package us.lsi.dp1.newcorporder.match;

import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.MatchStatsRepository;
import us.lsi.dp1.newcorporder.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MatchRepository {

    private final MatchStatsRepository matchStatsRepository;

    private final Map<String, Match> matches = new ConcurrentHashMap<>();

    public MatchRepository(MatchStatsRepository matchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
    }

    public Collection<Match> getMatches() {
        return this.matches.values();
    }

    public Optional<Match> findRandomPublicMatch(Player player, MatchMode mode, int maxPlayers) {
        return this.matches.values().stream()
            .filter(match -> match.getState().equals(MatchState.WAITING))
            .filter(match -> match.getVisibility().equals(MatchVisibility.PUBLIC))
            .filter(match -> match.getPlayer(player.getId()) == null)
            .filter(match -> match.getPlayers().size() < match.getMaxPlayers())
            .filter(match -> match.getMode().equals(mode))
            .filter(match -> match.getMaxPlayers() == maxPlayers)
            .findFirst();
    }

    public void registerMatch(Match match) {
        this.matches.put(match.getCode(), match);
    }

    public Optional<Match> getByMatchCode(String matchCode) {
        return Optional.ofNullable(this.matches.get(matchCode));
    }

    public String buildValidMatchCode() {
        String matchCode = null;
        while (matchCode == null || getByMatchCode(matchCode).isPresent() || matchStatsRepository.existsByCode(matchCode)) {
            matchCode = RandomUtils.getRandomMatchCode();
        }
        return matchCode;
    }
}
