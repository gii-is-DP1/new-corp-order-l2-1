package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@Data
public class PrintMediaAbility implements CompanyAbility {

    @NotNull private Integer playerId;
    @NotNull private Conglomerate ownConglomerate;
    @NotNull private Boolean isOwnRotated;
    @NotNull private Conglomerate otherConglomerate;
    @NotNull private Boolean isOtherRotated;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.PRINT_MEDIA;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        Preconditions.checkArgument(!match.getTurnSystem().getCurrentPlayer().getPlayerId().equals(this.playerId),
            "cannot rotate your own shares!");

        match.getPlayer(this.playerId).getHeadquarter().addConglomerate(ownConglomerate, isOwnRotated);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerate(otherConglomerate, isOtherRotated);
    }
}
