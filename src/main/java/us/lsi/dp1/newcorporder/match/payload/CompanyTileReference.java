package us.lsi.dp1.newcorporder.match.payload;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;

@Data
public class CompanyTileReference {
    private int x, y;

    public CompanyTile fromMatch(Match match) {
        return match.getCompanyMatrix().getTile(this.x, this.y);
    }
}
