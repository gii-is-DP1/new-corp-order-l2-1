package us.lsi.dp1.newcorporder.match.configuration;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.player.PlayerService;

@Aspect
@Component
public class VerifyCurrentTurnAspect {

    private final PlayerService playerService;

    public VerifyCurrentTurnAspect(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Before("@annotation(us.lsi.dp1.newcorporder.match.VerifyCurrentTurn) && args(.., match)")
    public void verifyCurrentTurn(Match match) {
        Player player = playerService.getAuthenticatedPlayer();

        if (match.getState() != MatchState.PLAYING) {
            throw new IllegalStateException("The match has not started yet");
        }

        if (!match.getTurnSystem().getCurrentPlayer().getPlayerId().equals(player.getId())) {
            throw new IllegalStateException("It's not your turn");
        }
    }
}
