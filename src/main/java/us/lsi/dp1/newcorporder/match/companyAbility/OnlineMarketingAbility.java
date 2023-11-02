package us.lsi.dp1.newcorporder.match.companyAbility;

import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.payload.request.TakeOverRequest;

public class OnlineMarketingAbility implements CompanyAbility {

    private CompanyTile firstCompany;
    private CompanyTile secondCompany;

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        int aAgents = firstCompany.getAgents();
        Conglomerate aConglomerate = firstCompany.getCurrentConglomerate();

        firstCompany.setAgents(secondCompany.getAgents());
        firstCompany.setCurrentConglomerate(secondCompany.getCurrentConglomerate());

        secondCompany.setAgents(aAgents);
        secondCompany.setCurrentConglomerate(aConglomerate);
    }
}
