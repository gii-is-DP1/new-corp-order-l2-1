package us.lsi.dp1.newcorporder.player;

import org.junit.Assert;
import org.junit.Test;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.player.Headquarter;

public class HeadquarterTest {

    @Test
    public void getCapturedAgentsCountTest()
    {
        Headquarter hq = Headquarter.create();
        hq.addAgents(Conglomerate.GENERIC_INC,5);
        hq.addAgents(Conglomerate.OMNICORP,3);
        hq.addAgents(Conglomerate.OMNIMEDIA,2);
        Assert.assertEquals(10, hq.getCapturedAgentsCount());
    }

    @Test
    public void addCapturedAgentTest()
    {
        Headquarter hq = Headquarter.create();
        hq.addAgents(Conglomerate.GENERIC_INC,5);
        hq.addAgents(Conglomerate.GENERIC_INC,1);
        Assert.assertEquals(6,hq.getCapturedAgentsCount());
    }
}
