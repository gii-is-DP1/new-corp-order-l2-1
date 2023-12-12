package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

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
        Headquarter otherHq = match.getPlayer(this.playerId).getHeadquarter();
        Headquarter currentHq = match.getTurnSystem().getCurrentPlayer().getHeadquarter();

        this.check(match, otherHq, currentHq);

        otherHq.addConglomerate(ownConglomerate, isOwnRotated);
        currentHq.addConglomerate(otherConglomerate, isOtherRotated);

        otherHq.removeConglomerate(otherConglomerate, isOtherRotated);
        currentHq.removeConglomerate(ownConglomerate, isOwnRotated);
    }

    private void check(Match match, Headquarter otherHq, Headquarter currentHq) {
        Preconditions.checkArgument(!match.getTurnSystem().getCurrentPlayer().getPlayerId().equals(this.playerId),
            "cannot rotate your own shares!");

        if (this.isOwnRotated && currentHq.getUsedConglomerateShares(this.ownConglomerate) < 1) {
            throw new IllegalStateException("there are no used shares in your hq of the given conglomerate");
        }
        if (!this.isOwnRotated && currentHq.getConglomerateShares(this.ownConglomerate) < 1) {
            throw new IllegalStateException("there are no unused shares in your hq of the given conglomerate");
        }
        if (this.isOtherRotated && otherHq.getUsedConglomerateShares(this.otherConglomerate) < 1) {
            throw new IllegalStateException("there are no used shares in their hq of the given conglomerate");
        }
        if (!this.isOtherRotated && otherHq.getConglomerateShares(this.otherConglomerate) < 1) {
            throw new IllegalStateException("there are no unused shares in their hq of the given conglomerate");
        }
    }
}
