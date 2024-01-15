package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

@Data
@Builder
public class DefaultInfiltrate implements Infiltrate {

    @NotNull private CompanyTileReference tile;
    @NotNull private Conglomerate conglomerateType;
    @NotNull @Positive private Integer numberOfShares;

    @Override
    public int getTotalNumberOfShares() {
        return numberOfShares;
    }

    @Override
    public void apply(Match match, UseConsultantRequest useConsultantRequests) {
        System.out.println("BBBBBBBBBBBBBBB");
        System.out.println(useConsultantRequests.getConsultant());
        System.out.println(useConsultantRequests.getConsultant() == ConsultantType.MEDIA_ADVISOR);
        Preconditions.checkArgument(useConsultantRequests.getConsultant() == null,
            "invalid request for the selected consultant: %s", useConsultantRequests.getConsultant());

        CompanyTile tile = this.tile.fromMatch(match);
        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, numberOfShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, numberOfShares);
        tile.addAgents(numberOfShares);

        match.getTurnSystem().getCurrentPlayer().addShareUses(conglomerateType, numberOfShares);
    }
}
