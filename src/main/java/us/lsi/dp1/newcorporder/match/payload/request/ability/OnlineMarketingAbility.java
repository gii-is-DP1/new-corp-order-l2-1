package us.lsi.dp1.newcorporder.match.payload.request.ability;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@Data
public class OnlineMarketingAbility implements CompanyAbility {

    @NotNull private CompanyTileReference firstCompany;
    @NotNull private CompanyTileReference secondCompany;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.ONLINE_MARKETING;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        CompanyTile firstCompany = this.firstCompany.fromMatch(match);
        CompanyTile secondCompany = this.secondCompany.fromMatch(match);

        int firstCompanyAgents = firstCompany.getAgents();
        Conglomerate firstCompanyCurrentConglomerate = firstCompany.getCurrentConglomerate();

        firstCompany.setAgents(secondCompany.getAgents());
        firstCompany.setCurrentConglomerate(secondCompany.getCurrentConglomerate());

        secondCompany.setAgents(firstCompanyAgents);
        secondCompany.setCurrentConglomerate(firstCompanyCurrentConglomerate);
    }
}
