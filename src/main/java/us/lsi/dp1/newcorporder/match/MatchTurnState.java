package us.lsi.dp1.newcorporder.match;

public enum MatchTurnState {

    SELECTING_ACTION,

    // PLOT
    SELECTING_FIRST_SHARE,
    SELECTING_SECOND_SHARE,
    REMOVING_SHARES_FROM_HAND,

    //TAKE OVER
    SELECTING_CONSULTANT,
    TAKING_OVER,
    POST_TAKING_OVER,


}
