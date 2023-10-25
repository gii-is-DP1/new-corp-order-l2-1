package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;

public class Headquarter {

    public static Headquarter create() {
        return new Headquarter();
    }

    private final Multiset<Conglomerate> agents = HashMultiset.create();
    private final Multiset<ConsultantType> consultants = HashMultiset.create();
    private final Multiset<Conglomerate> conglomerateShares = HashMultiset.create();
    private final Multiset<Conglomerate> usedConglomerateShares = HashMultiset.create();

    private Headquarter() {
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
    public Multiset<ConsultantType> getConsultants()
    {
        return consultants;
    }

    public void removeConsultant(ConsultantType consultant) {
        this.consultants.remove(consultant);
    }

    public void addConglomerateShare(Conglomerate conglomerate, int num) {
        this.conglomerateShares.add(conglomerate, num);
    }

    public void rotateConglomerates(Conglomerate type, int quantity) {
        Preconditions.checkArgument(
            this.conglomerateShares.count(type) >= quantity,
            "not enough conglomerate shares to use");
        this.usedConglomerateShares.add(type, quantity);
        this.conglomerateShares.remove(type, quantity);
    }

    public void unrotateConglomerateShare(Conglomerate type, int quantity) {
        Preconditions.checkArgument(
            this.usedConglomerateShares.count(type) >= quantity,
            "not enough used conglomerate shares");
        this.conglomerateShares.add(type, quantity);
        this.usedConglomerateShares.remove(type, quantity);
    }
}
