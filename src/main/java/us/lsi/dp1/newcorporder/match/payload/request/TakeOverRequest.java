package us.lsi.dp1.newcorporder.match.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;

@Data
public class TakeOverRequest {
    @NotNull private int agents;
    @NotNull private CompanyTileReference sourceCompany;
    @NotNull private CompanyTileReference targetCompany;
}
