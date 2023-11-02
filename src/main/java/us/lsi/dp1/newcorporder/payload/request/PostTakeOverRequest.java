package us.lsi.dp1.newcorporder.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.companyAbility.CompanyAbility;

import javax.annotation.Nullable;

@Data
public class PostTakeOverRequest {
    @Nullable private CompanyAbility companyAbility;
    @Nullable private ConsultantType chosenConsultant;
}
