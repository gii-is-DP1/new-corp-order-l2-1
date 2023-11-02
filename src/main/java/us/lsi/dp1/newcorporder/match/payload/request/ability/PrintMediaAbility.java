package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

public class PrintMediaAbility implements CompanyAbility {

    private Headquarter otherHq;
    private Conglomerate ownConglomerate;
    private Boolean isOwnRotated;
    private Conglomerate otherConglomerate;
    private Boolean isOtherRotated;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.PRINT_MEDIA;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(match.getTurnSystem().getCurrentPlayer().getHeadquarter() != otherHq, "headquarters must be different!");
        otherHq.addConglomerate(ownConglomerate, isOwnRotated);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerate(otherConglomerate, isOtherRotated);
    }
}
