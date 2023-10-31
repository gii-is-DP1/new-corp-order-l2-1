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
    private final Multiset<Conglomerate> capturedAgents = HashMultiset.create();
    private final Multiset<ConsultantType> consultants = HashMultiset.create();
    private final Multiset<Conglomerate> conglomerateShares = HashMultiset.create();
    private final Multiset<Conglomerate> usedConglomerateShares = HashMultiset.create();

    private Headquarter() {
    }

    public void addAgents(Conglomerate conglomerate, int num) {
        this.capturedAgents.add(conglomerate, num);
    }

    public void removeAgents(Conglomerate conglomerate, int num) {
        this.capturedAgents.remove(conglomerate, num);
    }

    public int getCapturedAgentsCount() {return capturedAgents.size();}

    public void addConsultant(ConsultantType consultant) {
        this.consultants.add(consultant);
    }

    public void removeConsultant(ConsultantType consultant) {
        this.consultants.remove(consultant);
    }

    public void addConglomerates(Conglomerate conglomerate, int quantity) {
        this.conglomerateShares.add(conglomerate, quantity);
    }

    public void removeConglomerates(Conglomerate conglomerate, int quantity) {
        this.conglomerateShares.remove(conglomerate, quantity);
    }

    public void addConglomerate(Conglomerate conglomerate, Boolean isRotated) {
        addConglomerates(conglomerate, 1);
        if(isRotated) rotateConglomerates(conglomerate, 1);
    }


    public void rotateConglomerates(Conglomerate type, int quantity) {
        Preconditions.checkArgument(
            this.conglomerateShares.count(type) >= quantity,
            "not enough conglomerate shares to use");
        this.usedConglomerateShares.add(type, quantity);
        this.conglomerateShares.remove(type, quantity);
    }

    public void unrotateConglomerates(Conglomerate type, int quantity) {
        Preconditions.checkArgument(
            this.usedConglomerateShares.count(type) >= quantity,
            "not enough used conglomerate shares");
        this.conglomerateShares.add(type, quantity);
        this.usedConglomerateShares.remove(type, quantity);
    }
}
