package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EqualsAndHashCode(of = "playerId")
public class MatchPlayer {

    @Getter private final Integer playerId;
    @Getter private final Headquarter headquarter;

    private final Multiset<Conglomerate> hand = HashMultiset.create();
    private final List<CompanyType> secretObjectives;

    public MatchPlayer(Integer playerId, Headquarter headquarter) {
        this(playerId, headquarter, new ArrayList<>(2));
    }

    @Builder
    private MatchPlayer(Integer playerId, Headquarter headquarter, List<CompanyType> secretObjectives) {
        this.playerId = playerId;
        this.headquarter = headquarter;
        this.secretObjectives = secretObjectives;
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

    public void addShareToHand(Conglomerate conglomerate) {
        this.addSharesToHand(conglomerate, 1);
    }

    public void addSharesToHand(Conglomerate conglomerate, int amount) {
        this.hand.add(conglomerate, amount);
    }

    public void discardSharesFromHand(Conglomerate conglomerate, int sharesToDiscard) {
        Preconditions.checkState(this.hand.count(conglomerate) >= sharesToDiscard,
            "you don't have enough shares of the given conglomerate to discard");

        this.hand.remove(conglomerate, sharesToDiscard);
    }

    public Multiset<Conglomerate> getHand() {
        return ImmutableMultiset.copyOf(hand);
    }

    public List<CompanyType> getSecretObjectives() {
        return ImmutableList.copyOf(secretObjectives);
    }

    public int getParticipationPoints(Conglomerate conglomerateType) {
        return headquarter.getTotalConglomeratesShares(conglomerateType) +
            (headquarter.getAgentsCaptured(conglomerateType) * 2);
    }

}
