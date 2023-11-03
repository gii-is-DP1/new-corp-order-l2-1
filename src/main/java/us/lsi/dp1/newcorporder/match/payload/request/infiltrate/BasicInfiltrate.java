package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

public class BasicInfiltrate implements Infiltrate {
    private Conglomerate conglomerateType;
    private int conglomerateShares;
    private CompanyTile tile;

    @Override
    public void infiltrate(Match match, ConsultantRequest consultantRequests) {
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares);
        Preconditions.checkArgument(tile.getCurrentConglomerate() != conglomerateType,
            "you cannot add agents to a box that has agents from a different conglomerate");
        tile.addAgents(conglomerateShares);
    }
}
