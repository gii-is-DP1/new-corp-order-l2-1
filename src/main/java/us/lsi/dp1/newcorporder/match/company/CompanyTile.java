package us.lsi.dp1.newcorporder.match.company;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;

@Getter
public class CompanyTile {

    private final Company company;
    private final Multiset<Conglomerate> agents = HashMultiset.create();

    public CompanyTile(Company company) {
        this.company = company;
    }
}
