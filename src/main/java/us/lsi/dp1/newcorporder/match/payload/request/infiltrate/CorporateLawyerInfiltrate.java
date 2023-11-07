package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.request.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

import java.util.List;

@Data
public class CorporateLawyerInfiltrate implements Infiltrate {

    private List<BasicInfiltrate> actions;

    @Override
    public int getConglomerateSharesUsed() {
        return actions.stream().mapToInt(BasicInfiltrate::getConglomerateShares).sum();
    }

    @Override
    public void run(Match match, ConsultantRequest consultantRequests) {
        Preconditions.checkState(consultantRequests.getConsultant() == ConsultantType.CORPORATE_LAWYER,
            "the infiltrate must be the same type as the consultant used");
        Preconditions.checkArgument(actions.size() == 2,
            "there must be 2 infiltrate actions when using the corporate lawyer consultant");
        Preconditions.checkArgument(actions.stream().map(BasicInfiltrate::getConglomerateType).distinct().count() == 2,
            "the conglomerate shares selected have to be of different type");

        actions.forEach(action -> infiltrate(match, action.getConglomerateType(), action.getConglomerateShares(), action.getTile()));
    }

    private void infiltrate(Match match, Conglomerate conglomerateType, int conglomerateShares, CompanyTileReference tileReference) {
        CompanyTile tile = match.getCompanyMatrix().getTile(tileReference.getX(), tileReference.getY());

        Preconditions.checkArgument(tile.getCurrentConglomerate() != conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares);
        tile.addAgents(conglomerateShares);
    }
}
