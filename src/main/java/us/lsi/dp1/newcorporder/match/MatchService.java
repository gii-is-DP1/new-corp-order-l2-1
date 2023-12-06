package us.lsi.dp1.newcorporder.match;

import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.player.Player;

import java.util.Optional;

@Service
public class MatchService {

    // TODO: synchronize
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public MatchAssignmentResponse quick(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.findRandomPublicMatch(mode, maxPlayers)
            .orElseGet(() -> matchRepository.createNewMatch(mode, MatchVisibility.PUBLIC, maxPlayers));

        return this.join(player, match);
    }

    public MatchAssignmentResponse join(Player player, String matchCode) {
        Match match = matchRepository.getByMatchCode(matchCode)
            .orElseThrow(() -> new IllegalArgumentException("match with code %s does not exist".formatted(matchCode)));

        return this.join(player, match);
    }

    public MatchAssignmentResponse createPrivateMatch(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.createNewMatch(mode, MatchVisibility.PRIVATE, maxPlayers);

        return this.join(player, match);
    }

    public Optional<Match> findByCode(String code) {
        return matchRepository.getByMatchCode(code);
    }

    private MatchAssignmentResponse join(Player player, Match match) {
        match.addPlayer(MatchPlayer.create(player.getId()));
        return new MatchAssignmentResponse(match.getCode());
    }
}
