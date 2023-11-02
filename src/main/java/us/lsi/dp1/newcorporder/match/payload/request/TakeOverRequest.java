package us.lsi.dp1.newcorporder.match.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
public class TakeOverRequest {
    @NotNull private int agents;
    @NotNull private CompanyTile sourceCompany;
    @NotNull private CompanyTile targetCompany;
}
