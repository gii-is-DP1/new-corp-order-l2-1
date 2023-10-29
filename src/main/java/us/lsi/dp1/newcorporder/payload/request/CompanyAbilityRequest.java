package us.lsi.dp1.newcorporder.payload.request;

import lombok.Data;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.companyAbility.CompanyAbility;

import javax.annotation.Nullable;

@Data
@Getter
public class CompanyAbilityRequest {

    @Nullable
    private CompanyAbility companyAbility;
}
