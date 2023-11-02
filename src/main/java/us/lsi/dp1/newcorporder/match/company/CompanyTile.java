package us.lsi.dp1.newcorporder.match.company;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.match.Conglomerate;

@Getter
@EqualsAndHashCode(of = "company")
public class CompanyTile {

    private final Company company;

    @Setter
    private Conglomerate currentConglomerate;
    @Setter
    private int agents = 1;

    public CompanyTile(Company company, Conglomerate currentConglomerate) {
        this.company = company;
        this.currentConglomerate = currentConglomerate;
    }

    public void addAgents(int quantity) {
        Preconditions.checkArgument(quantity > 0, "quantity cannot be zero");
        this.agents += quantity;
    }

    public void removeAgents(int num) {
        Preconditions.checkArgument(num > 0, "num cannot be zero");
        Preconditions.checkState(this.agents > 1, "cannot leave a tile empty");
        this.agents -= num;
    }

    public void takeOver(Conglomerate conglomerate, int agents) {
        Preconditions.checkArgument(conglomerate != this.currentConglomerate, "cannot attack with the current conglomerate");
        this.currentConglomerate = conglomerate;
        this.agents = agents;
    }
}
