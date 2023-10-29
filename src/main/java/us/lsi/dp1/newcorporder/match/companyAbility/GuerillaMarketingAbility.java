package us.lsi.dp1.newcorporder.match.companyAbility;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class GuerillaMarketingAbility implements CompanyAbility {
    private Headquarter hq;
    private Conglomerate conglomerateToRotate;
    private Boolean rotatesTwoCards;

    @Override
    public void check(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(match.getTurnSystem().getCurrentHq() != hq, "headquarter must be different than yours!");
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        hq.rotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
    }
}
