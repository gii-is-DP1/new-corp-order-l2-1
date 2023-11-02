package us.lsi.dp1.newcorporder.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
public class TakeOverRequest {
    @NotNull private int agents;
    @NotNull private CompanyTile sourceCompany;
    @NotNull private CompanyTile targetCompany;
}
