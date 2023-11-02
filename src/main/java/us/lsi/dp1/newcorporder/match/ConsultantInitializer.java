package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class ConsultantInitializer {
    private static final Random random = new Random();
    private final List<ConsultantType> consultants;
    public ConsultantInitializer(int players)
    {
        consultants = Lists.newArrayList(ConsultantType.values());
        if (players < 3)
            consultants.remove(ConsultantType.CORPORATE_LAWYER);
    }

    public ConsultantType getRandomUniqueConsultant()
    {
        if(consultants.size() == 0) throw new IllegalStateException("There are no more consultants!");
        int randomIndex = random.nextInt(consultants.size());
        ConsultantType consultant = consultants.get(randomIndex);
        consultants.remove(randomIndex);
        return consultant;
    }
}
