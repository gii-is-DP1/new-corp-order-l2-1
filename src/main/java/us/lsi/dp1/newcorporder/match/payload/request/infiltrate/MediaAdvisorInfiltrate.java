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
public class MediaAdvisorInfiltrate implements Infiltrate {

    @NotNull private CompanyTileReference tile;
    @NotNull private Conglomerate conglomerateType;
    @NotNull @Positive private Integer numberOfShares;

    @NotNull private Conglomerate extraConglomerate;

    @Override
    public int getTotalNumberOfShares() {
        return numberOfShares + 1;
    }

    @Override
    public void apply(Match match, UseConsultantRequest useConsultantRequests) {
        CompanyTile tile = this.tile.fromMatch(match);
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println(useConsultantRequests.getConsultant());
        System.out.println(useConsultantRequests.getConsultant() == ConsultantType.MEDIA_ADVISOR);
        Preconditions.checkArgument(useConsultantRequests.getConsultant() == ConsultantType.MEDIA_ADVISOR,
            "invalid request for the selected consultant: %s", useConsultantRequests.getConsultant());
        Preconditions.checkArgument(conglomerateType != extraConglomerate,
            "your extra conglomerate share cannot be the same type as your main conglomerate shares");
        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, numberOfShares);
        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(extraConglomerate, 1);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, numberOfShares + 1);
        tile.addAgents(numberOfShares + 1);

        match.getTurnSystem().getCurrentPlayer().addShareUses(conglomerateType, numberOfShares);
        match.getTurnSystem().getCurrentPlayer().addShareUses(extraConglomerate, 1);
    }
}
