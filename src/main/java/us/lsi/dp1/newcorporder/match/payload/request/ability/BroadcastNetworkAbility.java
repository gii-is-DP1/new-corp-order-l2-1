package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

public class BroadcastNetworkAbility implements CompanyAbility {

    private CompanyTile firstTarget;
    private CompanyTile secondTarget;
    private CompanyTile sourceCompany;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.BROADCAST_NETWORK;
    }

    private void check(Match match, TakeOverRequest takeOverRequest) {
        int agentsToMove = takeOverRequest.getAgents();
        Preconditions.checkArgument(!(agentsToMove == 1 && secondTarget != null), "can't have second target with just one agent!");
        Preconditions.checkArgument(agentsToMove == 1 || agentsToMove == 2, "must move one or two agents");
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        check(match, takeOverRequest);
        int agentsToMove = takeOverRequest.getAgents();
        if (secondTarget == null)
            firstTarget.addAgents(agentsToMove);
        else {
            firstTarget.addAgents(1);
            secondTarget.addAgents(1);
        }

        sourceCompany.removeAgents(agentsToMove);
    }
}
