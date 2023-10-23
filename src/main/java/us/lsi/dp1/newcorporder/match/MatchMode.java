package us.lsi.dp1.newcorporder.match;

import lombok.Getter;

@Getter
public enum MatchMode {

    /**
     * A normal match, with 80 conglomerate shares in total.
     */
    NORMAL(20),

    /**
     * A quick mode, with 5 shares per conglomerate less: 60 conglomerate shares in total.
     */
    QUICK(15);

    private final int sharesPerConglomerateInDeck;

    MatchMode(int sharesPerConglomerateInDeck) {
        this.sharesPerConglomerateInDeck = sharesPerConglomerateInDeck;
    }
}
