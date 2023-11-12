package us.lsi.dp1.newcorporder.match.payload.request.ability;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

@Data
public class AmbientAdvertisingAbility implements CompanyAbility {

    @NotNull private Integer playerId;
    @NotNull private Conglomerate conglomerateToRotate;
    @NotNull private Boolean rotatesTwoCards;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.AMBIENT_ADVERTISING;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        TurnSystem turnSystem = match.getTurnSystem();
        match.getPlayer(this.playerId).getHeadquarter().unrotateConglomerates(conglomerateToRotate, rotatesTwoCards ? 2 : 1);
    }
}
