package us.lsi.dp1.newcorporder.payload.request;

import lombok.Data;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
@Getter
public class TakeOverRequest {

    private Conglomerate typeToRotate;
    private int quantityToRotate;
    private CompanyTile sourceCompany;
    private CompanyTile targetCompany;
}
