package us.lsi.dp1.newcorporder.match.companyAbility;

import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class SocialMediaAbility implements CompanyAbility {

    private Headquarter hq;
    private Conglomerate conglomerateToRemove;

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        hq.removeConglomerates(conglomerateToRemove, 1);
    }
}
