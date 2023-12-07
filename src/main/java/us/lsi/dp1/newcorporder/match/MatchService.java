package us.lsi.dp1.newcorporder.match;

import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.Optional;

@Service
public class MatchService {

    // TODO: synchronize
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public MatchAssignmentResponse quickPlay(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.findRandomPublicMatch(mode, maxPlayers)
            .orElseGet(() -> matchRepository.createNewMatch(mode, MatchVisibility.PUBLIC, maxPlayers));

        return this.join(player, match);
    }

    public MatchAssignmentResponse createPrivateMatch(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.createNewMatch(mode, MatchVisibility.PRIVATE, maxPlayers);

        return this.join(player, match);
    }

    public MatchAssignmentResponse join(Player player, Match match) {
        match.addPlayer(MatchPlayer.create(player.getId()));
        return new MatchAssignmentResponse(match.getCode());
    }

    public void leave(Player player, Match match) {
        match.removePlayer(match.getPlayer(player.getId()));
    }

    public void forceStart(Player player, Match match) {
        RestPreconditions.checkAccess(match.isHost(player));
        match.start();
    }

    public Optional<Match> findByCode(String code) {
        return matchRepository.getByMatchCode(code);
    }
}
