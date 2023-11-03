package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

import javax.annotation.Nullable;

@Data
public class InfiltrateRequest {
    @Nullable private Conglomerate conglomerateType;
    @Nullable private int conglomerateShares;
    @Nullable private CompanyTile tile;
}
