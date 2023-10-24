package us.lsi.dp1.newcorporder.match.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.company.CompanyType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class MatchPlayer {

    @Getter
    private final Integer playerId;
    @Getter
    private final Headquarter headquarter;

    private final List<Conglomerate> hand = new ArrayList<>();
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

    private Integer countConglomerateSharesInHand(Conglomerate conglomerateType) {
        Integer res = 0;
        for (Conglomerate elem : hand) {
            if (elem.equals(conglomerateType))
                res++;
        }
        return res;
    }

    /**
     * Use the conglomerate Shares of the request type
     * @param conglomerateSharesUsed number of conglomerate shares used
     * @param conglomerateType type of the conglomerate you want to use
     */
    public void useConglomerateShares(Integer conglomerateSharesUsed, Conglomerate conglomerateType) {
        Integer conglomerateSharesInHand = countConglomerateSharesInHand(conglomerateType);
        if (conglomerateSharesInHand <= conglomerateSharesUsed) {
            for (int i = conglomerateSharesUsed; i <= 0; i = i - 1) {   //FIXME: es mejor usar el primitivo o Integer?
                hand.remove(conglomerateType);
            }

        } else {
            throw new NoSuchElementException("there are not enough conglomerate shares of that type");
        }
    }

    public List<Conglomerate> getHand() {
        return ImmutableList.copyOf(hand);
    }

    public List<CompanyType> getSecretObjectives() {
        return ImmutableList.copyOf(secretObjectives);
    }
}
