package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyType;

import java.util.List;

public class Headquarter {

    private final Multiset<Conglomerate> agents = HashMultiset.create();
    private final Multiset<ConsultantType> consultants = HashMultiset.create();
    private final Multiset<Conglomerate> conglomerateShares = HashMultiset.create();
    private final Multiset<Conglomerate> usedConglomerateShares = HashMultiset.create();
    @Getter private final List<CompanyType> secretObjectives;

    public Headquarter(List<CompanyType> secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    public void addAgents(Conglomerate conglomerate, int num) {
        this.agents.add(conglomerate, num);
    }

    public void removeAgents(Conglomerate conglomerate, int num) {
        this.agents.remove(conglomerate, num);
    }

    public void addConsultant(ConsultantType consultant) {
        this.consultants.add(consultant);
    }

    public void removeConsultant(ConsultantType consultant) {
        this.consultants.remove(consultant);
    }

    public void addConglomerateShare(Conglomerate conglomerate, int num) {
        this.conglomerateShares.add(conglomerate, num);
    }

    public void useConglomerateShare(Conglomerate conglomerate, int num) {
        Preconditions.checkArgument(
            this.conglomerateShares.count(conglomerate) >= num,
            "not enough conglomerate shares to use");
        this.usedConglomerateShares.add(conglomerate, num);
        this.conglomerateShares.remove(conglomerate, num);
    }

    public void unlockConglomerateShare(Conglomerate conglomerate, int num) {
        Preconditions.checkArgument(
            this.usedConglomerateShares.count(conglomerate) >= num,
            "not enough used conglomerate shares");
        this.conglomerateShares.add(conglomerate, num);
        this.usedConglomerateShares.remove(conglomerate, num);
    }
}
