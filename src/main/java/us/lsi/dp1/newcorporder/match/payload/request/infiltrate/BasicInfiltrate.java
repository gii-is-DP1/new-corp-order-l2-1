package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

@Data
@Builder
public class BasicInfiltrate implements Infiltrate {

    private CompanyTileReference tile;
    private Conglomerate conglomerateType;
    private int numberOfShares;

    @Override
    public int getTotalNumberOfShares() {
        return numberOfShares;
    }

    @Override
    public void run(Match match, UseConsultantRequest useConsultantRequests) {
        CompanyTile tile = this.tile.fromMatch(match);

        Preconditions.checkState(useConsultantRequests.getConsultant() == null,
            "the infiltrate must be the same type as the consultant used");
        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, numberOfShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, numberOfShares);
        tile.addAgents(numberOfShares);
    }
}
