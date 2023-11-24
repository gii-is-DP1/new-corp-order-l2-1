package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CorporateLawyerInfiltrate implements Infiltrate {

    private List<BasicInfiltrate> actions;

    @Override
    public int getTotalNumberOfShares() {
        return actions.stream().mapToInt(BasicInfiltrate::getNumberOfShares).sum();
    }

    @Override
    public void run(Match match, UseConsultantRequest useConsultantRequests) {
        Preconditions.checkState(useConsultantRequests.getConsultant() == ConsultantType.CORPORATE_LAWYER,
            "the infiltrate must be the same type as the consultant used");
        Preconditions.checkArgument(actions.size() == 2,
            "there must be 2 infiltrate actions when using the corporate lawyer consultant");
        Preconditions.checkArgument(actions.stream().map(BasicInfiltrate::getConglomerateType).distinct().count() == 2,
            "the conglomerate shares selected have to be of different type");

        actions.forEach(action -> infiltrate(match, action.getConglomerateType(), action.getNumberOfShares(), action.getTile()));
    }

    private int getDifferentConglomerates() {
        int res = 0;
        List<Conglomerate> ls = new ArrayList<>();
        for(BasicInfiltrate b: actions) {
        	if(!ls.contains(b.getConglomerateType())) {
        		ls.add(b.getConglomerateType());
        		res++;
        	}
        }
        return res;
            }

    private void infiltrate(Match match, Conglomerate conglomerateType, int conglomerateShares, CompanyTileReference tileReference) {
        CompanyTile tile = tileReference.fromMatch(match);

        Preconditions.checkArgument(tile.getCurrentConglomerate() == conglomerateType,
            "you cannot add agents to a tile that has agents from a different conglomerate");

        match.getTurnSystem().getCurrentPlayer().discardSharesFromHand(conglomerateType, conglomerateShares);
        match.getTurnSystem().getCurrentPlayer().getHeadquarter().addConglomerates(conglomerateType, conglomerateShares);
        tile.addAgents(conglomerateShares);
    }
}
