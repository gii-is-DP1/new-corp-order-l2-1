package us.lsi.dp1.newcorporder.match.player;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.company.CompanyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchPlayer {

    @Getter private final Integer playerId;
    @Getter private final Headquarter headquarter;

    private final Multiset<Conglomerate> hand = HashMultiset.create();
    private final List<CompanyType> secretObjectives = new ArrayList<>(2);

    public MatchPlayer(Integer playerId, Headquarter headquarter) {
        this.playerId = playerId;
        this.headquarter = headquarter;
    }

    public void init(ConsultantType consultant) {
        this.selectSecretObjectives();
        this.headquarter.addConsultant(consultant);
    }

    private void selectSecretObjectives() {
        List<CompanyType> companyTypes = Lists.newArrayList(CompanyType.values());
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            CompanyType selectedType = companyTypes.get(random.nextInt(companyTypes.size()));
            this.secretObjectives.add(selectedType);
            companyTypes.remove(selectedType);
        }
    }

    public List<CompanyType> getSecretObjectives() {
        return ImmutableList.copyOf(secretObjectives);
    }
}
