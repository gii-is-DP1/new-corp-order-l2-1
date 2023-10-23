package us.lsi.dp1.newcorporder.match.player;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;

public class MatchPlayer {

    @Getter private final Integer playerId;
    @Getter private final Headquarter headquarter;

    private final Multiset<Conglomerate> hand = HashMultiset.create();

    public MatchPlayer(Integer playerId, Headquarter headquarter) {
        this.playerId = playerId;
        this.headquarter = headquarter;
    }
}
