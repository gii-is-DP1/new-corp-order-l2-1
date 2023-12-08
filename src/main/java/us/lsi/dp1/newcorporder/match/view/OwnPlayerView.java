package us.lsi.dp1.newcorporder.match.view;

import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

@Getter
@Builder
public class OwnPlayerView {

    private final Multiset<Conglomerate> hand;
    private final List<CompanyType> secretObjectives;
    private final Headquarter headquarter;

    public static OwnPlayerView of(MatchPlayer player) {
        return OwnPlayerView.builder()
            .hand(player.getHand())
            .secretObjectives(player.getSecretObjectives())
            .headquarter(player.getHeadquarter())
            .build();
    }
}
