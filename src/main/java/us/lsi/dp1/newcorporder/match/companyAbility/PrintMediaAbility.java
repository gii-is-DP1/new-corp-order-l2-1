package us.lsi.dp1.newcorporder.match.companyAbility;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class PrintMediaAbility implements CompanyAbility {

    private Headquarter otherHq;
    private Conglomerate ownConglomerate;
    private Boolean isOwnRotated;
    private Conglomerate otherConglomerate;
    private Boolean isOtherRotated;

    public void check(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(match.getTurnSystem().getCurrentHq() != otherHq, "headquarters must be different!");
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        check(match, takeOverRequest);
        otherHq.addConglomerate(ownConglomerate, isOwnRotated);
        match.getTurnSystem().getCurrentHq().addConglomerate(otherConglomerate, isOtherRotated);
    }
}
