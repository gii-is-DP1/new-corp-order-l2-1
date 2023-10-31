package us.lsi.dp1.newcorporder.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
public class TakeOverRequest {
    private int agents;
    private CompanyTile sourceCompany;
    private CompanyTile targetCompany;
}
