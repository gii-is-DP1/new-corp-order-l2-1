package us.lsi.dp1.newcorporder.match.payload.request.ability;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@Data
public class SocialMediaAbility implements CompanyAbility {

    @NotNull private Integer playerId;
    @NotNull private Conglomerate conglomerateToRemove;

    @Override
    public CompanyType getCompanyType() {
        return CompanyType.SOCIAL_MEDIA;
    }

    @Override
    public void activate(Match match, TakeOverRequest takeOverRequest) {
        match.getPlayer(this.playerId).getHeadquarter().removeConglomerates(conglomerateToRemove, 1);
    }
}
