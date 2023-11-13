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
        int agentsToMove = takeOverRequest.getAgents();

        this.check(match, agentsToMove);

        if (this.secondTarget == null)
            firstTarget.addAgents(agentsToMove);
        else {
            CompanyTile secondTarget = this.secondTarget.fromMatch(match);
            firstTarget.addAgents(1);
            secondTarget.addAgents(1);
        }

        sourceCompany.removeAgents(agentsToMove);
    }

    private void check(Match match, int agentsToMove) {
        Preconditions.checkArgument(!(agentsToMove == 1 && this.secondTarget != null), "can't have second target with just one agent!");
        Preconditions.checkArgument(agentsToMove == 1 || agentsToMove == 2, "must move one or two agents");
    }
}
