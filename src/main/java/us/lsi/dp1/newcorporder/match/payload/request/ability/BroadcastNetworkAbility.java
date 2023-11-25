package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@Data
public class BroadcastNetworkAbility implements CompanyAbility {

    @NotNull private CompanyTileReference firstTarget;
    @Nullable private CompanyTileReference secondTarget;
    @NotNull private CompanyTileReference sourceCompany;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.BROADCAST_NETWORK;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        CompanyTile firstTarget = this.firstTarget.fromMatch(match);
        CompanyTile sourceCompany = this.sourceCompany.fromMatch(match);

        Preconditions.checkState(firstTarget.getCurrentConglomerate() == sourceCompany.getCurrentConglomerate(),
            "the source company and targets must have agents of the same conglomerate type");

        firstTarget.addAgents(1);
        if (this.secondTarget != null) {
            CompanyTile secondTarget = this.secondTarget.fromMatch(match);

            Preconditions.checkState(secondTarget.getCurrentConglomerate() == sourceCompany.getCurrentConglomerate(),
                "the source company and targets must have agents of the same conglomerate type");

            secondTarget.addAgents(1);
        }
        sourceCompany.removeAgents(this.secondTarget == null ? 1 : 2);
    }
}
