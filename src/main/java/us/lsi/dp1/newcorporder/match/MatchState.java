package us.lsi.dp1.newcorporder.match;

public enum MatchState {

    /**
     * Waiting for players or to start, match not initialized yet.
     */
    WAITING,

    /**
     * Match initialized and running, waiting for players to make a move.
     */
    PLAYING,

    /**
     * Match ended, waiting for players to leave.
     */
    ENDED
}
