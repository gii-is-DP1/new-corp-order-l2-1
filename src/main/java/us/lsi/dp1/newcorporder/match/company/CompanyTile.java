package us.lsi.dp1.newcorporder.match.company;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;

@Getter
@EqualsAndHashCode(of = "company")
public class CompanyTile {

    private final Company company;

    private Conglomerate currentConglomerate;
    private int agents = 1;

    public CompanyTile(Company company, Conglomerate currentConglomerate) {
        this.company = company;
        this.currentConglomerate = currentConglomerate;
    }

    public void addAgents(int num) {
        Preconditions.checkArgument(num > 0, "num cannot be zero");
        this.agents += num;
    }

    public void removeAgent(int num) {
        Preconditions.checkArgument(num > 0, "num cannot be zero");
        Preconditions.checkState(this.agents > 1, "cannot leave a tile empty");
        this.agents -= num;
    }

    public void attack(Conglomerate conglomerate, int agents) {
        Preconditions.checkArgument(conglomerate != this.currentConglomerate, "cannot attack with the current conglomerate");
        Preconditions.checkArgument(agents > this.agents, "unsuccessful attack");
        this.currentConglomerate = conglomerate;
        this.agents = agents;
    }
}
