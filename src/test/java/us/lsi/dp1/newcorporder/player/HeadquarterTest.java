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
        for(int i = 0; i < 5; i++)
            hq.captureAgent(Conglomerate.GENERIC_INC);
        for(int i = 0; i < 3; i++)
            hq.captureAgent(Conglomerate.OMNICORP);
        hq.captureAgent(Conglomerate.OMNIMEDIA);
        Assert.assertEquals(9, hq.getCapturedAgentsCount());
    }

    @Test
    public void addCapturedAgentTest()
    {
        Headquarter hq = Headquarter.create();
        hq.captureAgent(Conglomerate.GENERIC_INC);
        hq.captureAgent(Conglomerate.GENERIC_INC);
        Assert.assertEquals(2,hq.getCapturedAgentsCount());
    }
}
