package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

public class CorporateLawyerInfiltrate implements Infiltrate {

    private Conglomerate conglomerateType1;
    private int conglomerateShares1;
    private CompanyTile tile1;
    private Conglomerate conglomerateType2;
    private int conglomerateShares2;
    private CompanyTile tile2;


    @Override
    public int getConglomerateSharesUsed() {
        return conglomerateShares1 + conglomerateShares2;
    }

    @Override
    public void infiltrate(Match match, ConsultantRequest consultantRequests) {
        Preconditions.checkState(consultantRequests.getConsultant() == ConsultantType.CORPORATE_LAWYER,
            "the infiltrate must be the same type as the consultant used");
        Preconditions.checkArgument(conglomerateType1 != conglomerateType2,
            "the conglomerate shares selected have to be of different type");
        basicInfiltrate(match, conglomerateType1, conglomerateShares1, tile1);
        basicInfiltrate(match, conglomerateType2, conglomerateShares2, tile2);
    }

    private void basicInfiltrate(Match match, Conglomerate conglomerateType, int conglomerateShares, CompanyTile tile) {
        Preconditions.checkArgument(tile.getCurrentConglomerate() != conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares);
        tile.addAgents(conglomerateShares);
    }


}
