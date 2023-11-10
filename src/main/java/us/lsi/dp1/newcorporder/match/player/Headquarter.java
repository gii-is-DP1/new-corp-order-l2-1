package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public void captureAgent(Conglomerate conglomerate) {
        this.capturedAgents.add(conglomerate, 1);
    }

    public void removeAgents(Conglomerate conglomerate, int num) {
        this.capturedAgents.remove(conglomerate, num);
    }

    public int getCapturedAgentsCount() {
        return capturedAgents.size();
    }

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
        if (isRotated) rotateConglomerates(conglomerate, 1);
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

    public int getTotalConglomeratesSharesOfAType(Conglomerate conglomerateType) {
        return this.conglomerateShares.count(conglomerateType) + this.usedConglomerateShares.count(conglomerateType);
    }

    public int getAgentsCapturedOfAType(Conglomerate conglomerateType) {
        return this.capturedAgents.count(conglomerateType);
    }

    public int getConsultantsVP() {
        int vp = 0;
        List<ConsultantType> bestMatchConsultants= rankBestConsultantToMatch();
        if (this.consultants.elementSet().size() >= 4) vp++; //Punto extra por tener 4 consultores diferentes
        while (this.consultants.size() > 1 && bestMatchConsultants != null && bestMatchConsultants.size() > 1) {
            vp++;
            this.consultants.remove(bestMatchConsultants.get(0), 1);
            this.consultants.remove(bestMatchConsultants.get(1), 1);
            bestMatchConsultants = rankBestConsultantToMatch();
        }
        return vp;
    }

    private List<ConsultantType> rankBestConsultantToMatch() {
        List<ConsultantType> consultantList = new ArrayList<>();
        for (ConsultantType c : ConsultantType.values()) {
            if (this.consultants.count(c) > 0)
                consultantList.add(c);
        }
        if (consultantList.isEmpty())
            return null;
        else
            return consultantList.stream()
                .sorted(Comparator.comparingInt(this.consultants::count).reversed())
                .toList();
    }


}
