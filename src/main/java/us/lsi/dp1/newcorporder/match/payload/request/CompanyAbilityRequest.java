package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.payload.request.ability.CompanyAbility;

import javax.annotation.Nullable;

@Data
public class CompanyAbilityRequest {
    @Nullable private CompanyAbility companyAbility;
}
