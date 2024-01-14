package us.lsi.dp1.newcorporder.match.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
public class CompanyTileReference {

    @NotNull private Integer x;
    @NotNull private Integer y;

    public CompanyTile fromMatch(Match match) {
        return match.getCompanyMatrix().getTile(this.x, this.y);
    }
}
