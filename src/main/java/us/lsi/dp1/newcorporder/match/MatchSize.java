package us.lsi.dp1.newcorporder.match;

import lombok.Getter;

@Getter
public enum MatchSize {

    /**
     * Two players
     */
    COUPLE(4, 3),

    /**
     * Three or four players
     */
    GROUP(4, 4);

    private final int rows;
    private final int columns;

    MatchSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
