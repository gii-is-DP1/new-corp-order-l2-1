package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

public class TurnSystem {

    public static TurnSystem create() {
        return new TurnSystem();
    }

    private Match match;
    @Getter private Turn currentTurn;
    @Getter private MatchPlayer currentPlayer;
    @Getter @Nullable private MatchPlayer lastPlayerBeforeMatchEnds = null;

    private List<MatchPlayer> players;

    private TurnSystem() {
    }

    public void init(Match match, List<MatchPlayer> players) {
        this.match = match;
        this.players = players;
        this.currentPlayer = players.get(0);
    }

    public void selectAction(Action action) {
        Preconditions.checkState(this.currentTurn != null, "turn in progress");
        Preconditions.checkState(action != Action.PLOT || this.match.getGeneralSupply().getOpenDisplay().size() > 1,
            "cannot select plot action because there are not enough shares in the open display");

        switch (action) {
            case PLOT -> this.currentTurn = new PlotTurn(match);
            case INFILTRATE -> this.currentTurn = new InfiltrateTurn(match);
            case TAKE_OVER -> this.currentTurn = new TakeOverTurn(match);
        }
    }

    public void passTurn() {
        if (lastPlayerBeforeMatchEnds == currentPlayer) {
            this.match.end();
            return;
        }

        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        MatchPlayer currentPlayer = players.get(currentPlayerIndex);
        passTurnTo(currentPlayer);
    }

    private void passTurnTo(MatchPlayer player) {
        currentPlayer = player;
        currentTurn = null;
    }

    public void activateFinalRound() {
        Preconditions.checkState(this.lastPlayerBeforeMatchEnds == null,
            "final round is already activated");
        this.lastPlayerBeforeMatchEnds = this.currentPlayer;
    }

    // for testing purposes
    void setCurrentPlayer(MatchPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
