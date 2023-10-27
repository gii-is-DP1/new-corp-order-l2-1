package us.lsi.dp1.newcorporder.match;

import lombok.Getter;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

public class TurnSystem
{
    @Getter private MatchPlayer currentPlayer;
    @Getter private MatchTurnState currentState;
    @Getter private Headquarter currentHq;

    private List<MatchPlayer> players;
    private int currentPlayerIndex = 0;
    public void init(List<MatchPlayer> players)
    {
        this.players = players;
    }

    public void passTurn()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        MatchPlayer currentPlayer = players.get(currentPlayerIndex);
        passTurnTo(currentPlayer);
    }

    private void passTurnTo(MatchPlayer player) {
        currentPlayer = player;
        currentHq = currentPlayer.getHeadquarter();
        setState(MatchTurnState.SELECTING_ACTION);
    }

    public void setState(MatchTurnState nextTurnState) {
        currentState = nextTurnState;
    }
}
