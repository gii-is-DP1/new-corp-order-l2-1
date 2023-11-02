package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

public class TurnSystem {

    private final Match match;
    @Getter private MatchPlayer currentPlayer;
    @Getter @Nullable private Turn currentTurn;

    private List<MatchPlayer> players;

    public TurnSystem(Match match) {
        this.match = match;
    }

    public void init(List<MatchPlayer> players) {
        this.players = players;
        currentPlayer = players.get(0);
    }

    public void passTurn() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        MatchPlayer currentPlayer = players.get(currentPlayerIndex);
        passTurnTo(currentPlayer);
    }

    public void selectAction(Action action) {
        Preconditions.checkState(this.currentTurn != null, "turn in progress");

        switch (action) {
            case PLOT -> this.currentTurn = new PlotTurn(match);
            case INFILTRATE -> this.currentTurn = null; // TODO
            case TAKE_OVER -> this.currentTurn = new TakeOverTurn(match);
        }
    }

    private void passTurnTo(MatchPlayer player) {
        currentPlayer = player;
        currentTurn = null;
    }
}
