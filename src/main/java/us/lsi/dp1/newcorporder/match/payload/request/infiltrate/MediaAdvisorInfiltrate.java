package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

@Data
public class MediaAdvisorInfiltrate implements Infiltrate {

    private CompanyTileReference tile;
    private Conglomerate conglomerateType;
    private int numberOfShares;

    private Conglomerate extraConglomerate;

    @Override
    public int getTotalNumberOfShares() {
        return numberOfShares + 1;
    }

    @Override
    public void run(Match match, UseConsultantRequest useConsultantRequests) {
        CompanyTile tile = this.tile.fromMatch(match);

        Preconditions.checkState(useConsultantRequests.getConsultant() == ConsultantType.MEDIA_ADVISOR,
            "the infiltrate must be the same type as the consultant used");
        Preconditions.checkArgument(conglomerateType != extraConglomerate,
            "your extra conglomerate share cannot be the same type as your main conglomerate shares");
        Preconditions.checkArgument(tile.getCurrentConglomerate() != conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, numberOfShares);
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(extraConglomerate, 1);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, numberOfShares + 1);
        tile.addAgents(numberOfShares + 1);
    }
}
