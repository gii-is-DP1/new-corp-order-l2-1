package us.lsi.dp1.newcorporder.match.payload.request.ability;

import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

public class SocialMediaAbility implements CompanyAbility {

    private Headquarter hq;
    private Conglomerate conglomerateToRemove;

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        hq.removeConglomerates(conglomerateToRemove, 1);
    }
}
