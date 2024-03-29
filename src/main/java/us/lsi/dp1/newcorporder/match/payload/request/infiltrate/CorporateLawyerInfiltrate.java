package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

import java.util.List;

@Data
@Builder
public class CorporateLawyerInfiltrate implements Infiltrate {

    @Size(min = 2, max = 2, message = "there must be 2 infiltrate actions when using the corporate lawyer consultant")
    private List<DefaultInfiltrate> actions;

    @Override
    public int getTotalNumberOfShares() {
        return actions.stream().mapToInt(DefaultInfiltrate::getNumberOfShares).sum();
    }

    @Override
    public void apply(Match match, UseConsultantRequest useConsultantRequests) {
        System.out.println("CCCCCCCCCCCC");
        System.out.println(useConsultantRequests.getConsultant());
        System.out.println(useConsultantRequests.getConsultant() == ConsultantType.MEDIA_ADVISOR);
        Preconditions.checkArgument(useConsultantRequests.getConsultant() == ConsultantType.CORPORATE_LAWYER,
            "invalid request for the selected consultant: %s", useConsultantRequests.getConsultant());
        Preconditions.checkArgument(actions.stream().map(DefaultInfiltrate::getConglomerateType).distinct().count() == 2,
            "the conglomerate shares selected have to be of different types");

        actions.forEach(action -> check(match, action.getConglomerateType(), action.getNumberOfShares(), action.getTile()));
        actions.forEach(action -> infiltrate(match, action.getConglomerateType(), action.getNumberOfShares(), action.getTile()));
    }

    private void check(Match match, Conglomerate conglomerateType, int conglomerateShares, CompanyTileReference tileReference) {
        CompanyTile tile = tileReference.fromMatch(match);

        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");
        Preconditions.checkState(match.getTurnSystem().getCurrentPlayer().getHand().count(conglomerateType) >= conglomerateShares,
            "you don't have enough shares of the %s conglomerate to discard", conglomerateType);
    }

    private void infiltrate(Match match, Conglomerate conglomerateType, int conglomerateShares, CompanyTileReference tileReference) {
        CompanyTile tile = tileReference.fromMatch(match);

        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares);
        tile.addAgents(conglomerateShares);

        match.getTurnSystem().getCurrentPlayer().addShareUses(conglomerateType, conglomerateShares);
    }
}
