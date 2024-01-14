package us.lsi.dp1.newcorporder.match.view;

import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.Set;

@Data
@Builder
public class MatchSummary {

    private final Multiset<MatchPlayer> victoryPoints;
    private final Set<MatchPlayer> winners;

}
