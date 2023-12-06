package us.lsi.dp1.newcorporder.match;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.player.PlayerService;

@Aspect
@Component
public class VerifyCurrentTurnAspect {

    private final PlayerService playerService;

    public VerifyCurrentTurnAspect(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Before("@annotation(VerifyCurrentTurn) && args(match)")
    public void verify(Match match) {
        Player player = playerService.getAuthenticatedPlayer();

        if (!match.getTurnSystem().getCurrentPlayer().getPlayerId().equals(player.getId())) {
            throw new IllegalStateException("not your current turn");
        }
    }
}
