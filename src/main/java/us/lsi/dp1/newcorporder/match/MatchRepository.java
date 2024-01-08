package us.lsi.dp1.newcorporder.match;

import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MatchRepository {

    private final Map<String, Match> matches = new ConcurrentHashMap<>();

    public Optional<Match> findRandomPublicMatch(Player player, MatchMode mode, int maxPlayers) {
        System.out.println("XOXOXOXOXOXOXOXOXOXOXOXOXOX");
        for (Match match: matches.values() ) {
            System.out.println(match.getCode());
        }
        System.out.println("WOWOWOWOWOWOWOWOWOWOWOW");
        return this.matches.values().stream()
            .filter(match -> match.getState().equals(MatchState.WAITING))
            .filter(match -> match.getVisibility().equals(MatchVisibility.PUBLIC))
            .filter(match -> match.getPlayer(player.getId()) == null)
            .filter(match -> match.getPlayers().size() < match.getMaxPlayers())
            .filter(match -> match.getMode().equals(mode))
            .filter(match -> match.getMaxPlayers() == maxPlayers)
            .findFirst();
    }

    public Optional<Match> getByMatchCode(String matchCode) {
        return Optional.ofNullable(this.matches.get(matchCode));
    }

    public Match createNewMatch(MatchMode mode, MatchVisibility visibility, int maxPlayers) {
        String matchCode = this.buildValidMatchCode();

        Match match = Match.create(maxPlayers, mode, visibility, matchCode);
        this.matches.put(matchCode, match);

        return match;
    }

    private String buildValidMatchCode() {
        String matchCode = null;
        while (matchCode == null || getByMatchCode(matchCode).isPresent()) {
            matchCode = RandomUtils.getRandomMatchCode();
        }
        return matchCode;
    }
}
