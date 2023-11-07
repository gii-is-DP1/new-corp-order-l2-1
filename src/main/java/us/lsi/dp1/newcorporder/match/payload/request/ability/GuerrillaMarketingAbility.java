package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

@Data
public class GuerrillaMarketingAbility implements CompanyAbility {

    private Headquarter hq;
    private Conglomerate conglomerateToRotate;
    private Boolean rotatesTwoCards;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.GUERRILLA_MARKETING;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(match.getTurnSystem().getCurrentPlayer().getHeadquarter() != hq, "headquarter must be different than yours!");
        hq.rotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
    }
}
