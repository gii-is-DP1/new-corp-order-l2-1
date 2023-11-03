package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

public class MediaAdvisorInfiltrate implements Infiltrate {

    private Conglomerate conglomerateType;
    private int conglomerateShares;
    private CompanyTile tile;

    private Conglomerate extraConglomerate;

    @Override
    public ConsultantType getConsultant() {
        return ConsultantType.MEDIA_ADVISOR;
    }

    @Override
    public int getConglomerateSharesUsed() {
        return conglomerateShares + 1;
    }

    @Override
    public void infiltrate(Match match, ConsultantRequest consultantRequests) {
        Preconditions.checkArgument(conglomerateType != extraConglomerate,
            "your extra conglomerate share cannot be the same type as your main conglomerate shares");
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(extraConglomerate, 1);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares + 1);
        Preconditions.checkArgument(tile.getCurrentConglomerate() != conglomerateType,
            "you cannot add agents to a box that has agents from a different conglomerate");
        tile.addAgents(conglomerateShares + 1);
    }

}
