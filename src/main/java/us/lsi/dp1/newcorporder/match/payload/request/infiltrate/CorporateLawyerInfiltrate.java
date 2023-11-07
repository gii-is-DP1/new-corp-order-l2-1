package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

@Data
public class CorporateLawyerInfiltrate implements Infiltrate {

    private CompanyTileReference tile1;
    private Conglomerate conglomerateType1;
    private int conglomerateShares1;

    private CompanyTileReference tile2;
    private Conglomerate conglomerateType2;
    private int conglomerateShares2;

    @Override
    public int getConglomerateSharesUsed() {
        return conglomerateShares1 + conglomerateShares2;
    }

    @Override
    public void infiltrate(Match match, ConsultantRequest consultantRequests) {
        CompanyTile tile1 = match.getCompanyMatrix().getTile(this.tile1.getX(), this.tile1.getY());
        CompanyTile tile2 = match.getCompanyMatrix().getTile(this.tile2.getX(), this.tile2.getY());

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
