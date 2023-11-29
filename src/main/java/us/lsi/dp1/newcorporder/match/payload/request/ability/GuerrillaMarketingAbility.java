package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@Data
public class GuerrillaMarketingAbility implements CompanyAbility {

    @NotNull private Integer playerId;
    @NotNull private Conglomerate conglomerateToRotate;
    @NotNull private Boolean rotatesTwoCards;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.GUERRILLA_MARKETING;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(!match.getTurnSystem().getCurrentPlayer().getPlayerId().equals(this.playerId),
            "cannot rotate your own shares!");
        match.getPlayer(this.playerId).getHeadquarter().rotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
    }
}
