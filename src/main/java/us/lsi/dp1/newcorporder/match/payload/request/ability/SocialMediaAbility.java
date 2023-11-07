package us.lsi.dp1.newcorporder.match.payload.request.ability;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

@Data
public class SocialMediaAbility implements CompanyAbility {

    private Headquarter hq;
    private Conglomerate conglomerateToRemove;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.SOCIAL_MEDIA;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        hq.removeConglomerates(conglomerateToRemove, 1);
    }
}
