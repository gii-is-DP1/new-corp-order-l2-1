package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
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

    @Getter
    private final Integer playerId;
    @Getter
    private final Headquarter headquarter;

    private final Multiset<Conglomerate> hand = HashMultiset.create();

    private final List<CompanyType> secretObjectives = new ArrayList<>(2);

    public MatchPlayer(Integer playerId, Headquarter headquarter) {
        this.playerId = playerId;
        this.headquarter = headquarter;
    }

    public void init(ConsultantType initialConsultant, List<Conglomerate> initialHand) {
        this.hand.addAll(initialHand);
        this.headquarter.addConsultant(initialConsultant);
        this.selectSecretObjectives();
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

    /**
     * Use the conglomerate shares of the requested type
     *
     * @param conglomerateType       conglomerate type to use
     * @param conglomerateSharesUsed number of conglomerate shares to use
     * @throws IllegalArgumentException if there are not enought conglomerate shares of a type you cannot use it
     */
    public void useConglomerateShares(Conglomerate conglomerateType, Integer conglomerateSharesUsed) {
        Preconditions.checkArgument(this.hand.count(conglomerateType) <= conglomerateSharesUsed,
            "there are not enough conglomerate shares of that type");
        this.hand.remove(conglomerateType, conglomerateSharesUsed);
    }

    public List<Conglomerate> getHand() {
        return ImmutableList.copyOf(hand);
    }

    public List<CompanyType> getSecretObjectives() {
        return ImmutableList.copyOf(secretObjectives);
    }
}
