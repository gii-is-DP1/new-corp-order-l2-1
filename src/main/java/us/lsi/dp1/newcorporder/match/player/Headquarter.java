package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import lombok.Builder;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;

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

    @Builder
    private Headquarter(Multiset<Conglomerate> capturedAgents, Multiset<ConsultantType> consultants, Multiset<Conglomerate> conglomerateShares, Multiset<Conglomerate> usedConglomerateShares) {
        this.capturedAgents.addAll(capturedAgents);
        this.consultants.addAll(consultants);
        this.conglomerateShares.addAll(conglomerateShares);
        this.usedConglomerateShares.addAll(usedConglomerateShares);
    }




    /**
     * Add an agent of the given conglomerate to captured agents
     *
     * @param conglomerate the given conglomerate to capture an agent
     */
    public void captureAgent(Conglomerate conglomerate) {
        this.capturedAgents.add(conglomerate, 1);
    }

    /**
     * Remove the given quantity of agents
     *
     * @param conglomerate the given conglomerate to remove an agent
     * @param quantity     the quantity of agents to remove
     */
    public void removeAgents(Conglomerate conglomerate, int quantity) {
        this.capturedAgents.remove(conglomerate, quantity);
    }

    /**
     * Get how many agents are captured
     *
     * @return the quantity of captured agents
     */
    public int getCapturedAgentsCount() {
        return capturedAgents.size();
    }

    /**
     * Add a consultant of the given type
     *
     * @param consultant the given consultant to add
     */
    public void addConsultant(ConsultantType consultant) {
        this.consultants.add(consultant);
    }

    /**
     * Remove a consultant of the given type
     *
     * @param consultant the given consultant to remove
     */
    public void removeConsultant(ConsultantType consultant) {
        this.consultants.remove(consultant);
    }

    /**
     * Returns the consultants in this headquarter
     */
    public Multiset<ConsultantType> getConsultants() {
        return ImmutableMultiset.copyOf(consultants);
    }

    /**
     * Add the given quantity of shares to the given conglomerate
     *
     * @param conglomerate the given conglomerate to add the given quantity of shares
     * @param quantity     the quantity of shares to add
     */
    public void addConglomerates(Conglomerate conglomerate, int quantity) {
        this.conglomerateShares.add(conglomerate, quantity);
    }

    /**
     * Add a share of the given conglomerate depending on whether it is rotated or not
     *
     * @param conglomerate the given conglomerate to add a share
     * @param isRotated    if the given conglomerate is used
     */
    public void addConglomerate(Conglomerate conglomerate, Boolean isRotated) {
        if (!isRotated) {
            this.addConglomerates(conglomerate, 1);
        } else {
            this.usedConglomerateShares.add(conglomerate);
        }
    }

    /**
     * Remove the given quantity of shares to the given conglomerate
     *
     * @param conglomerate the given conglomerate to remove the given quantity of shares
     * @param quantity     the quantity of shares to remove
     */
    public void removeConglomerates(Conglomerate conglomerate, int quantity) {
        this.conglomerateShares.remove(conglomerate, quantity);
    }

    /**
     * Remove a share of the given conglomerate depending on whether it is rotated or not
     *
     * @param conglomerate the given conglomerate to remove a share
     * @param isRotated    if the given conglomerate is used
     */
    public void removeConglomerate(Conglomerate conglomerate, Boolean isRotated) {
        if (!isRotated) {
            this.removeConglomerates(conglomerate, 1);
        } else {
            this.usedConglomerateShares.remove(conglomerate);
        }
    }

    /**
     * Rotate the given quantity of shares to the given conglomerate
     *
     * @param conglomerate the given conglomerate to rotate the given quantity of shares
     * @param quantity     the quantity of shares to rotate
     */
    public void rotateConglomerates(Conglomerate conglomerate, int quantity) {
        Preconditions.checkArgument(
            this.conglomerateShares.count(conglomerate) >= quantity,
            "not enough conglomerate shares to use");
        this.usedConglomerateShares.add(conglomerate, quantity);
        this.conglomerateShares.remove(conglomerate, quantity);
    }

    /**
     * Unrotate the given quantity of shares to the given conglomerate
     *
     * @param conglomerate the given conglomerate to unrotate the given quantity of shares
     * @param quantity     the quantity of shares to unrotate
     */
    public void unrotateConglomerates(Conglomerate conglomerate, int quantity) {
        Preconditions.checkArgument(
            this.usedConglomerateShares.count(conglomerate) >= quantity,
            "not enough used conglomerate shares");
        this.conglomerateShares.add(conglomerate, quantity);
        this.usedConglomerateShares.remove(conglomerate, quantity);
    }

    /**
     * Get how many shares are unused of the given conglomerate
     *
     * @param conglomerate the given conglomerate to get the quantity of shares unused
     * @return how many shares are unused of the given conglomerate
     */
    public int getConglomerateShares(Conglomerate conglomerate) {
        return this.conglomerateShares.count(conglomerate);
    }

    /**
     * Get how many shares are used of the given conglomerate
     *
     * @param conglomerate the given conglomerate to get the quantity of shares used
     * @return how many shares are used of the given conglomerate
     */
    public int getUsedConglomerateShares(Conglomerate conglomerate) {
        return this.usedConglomerateShares.count(conglomerate);
    }

    public int getTotalConglomeratesShares(Conglomerate conglomerateType) {
        return this.conglomerateShares.count(conglomerateType) + this.usedConglomerateShares.count(conglomerateType);
    }

    public int getAgentsCaptured(Conglomerate conglomerateType) {
        return this.capturedAgents.count(conglomerateType);
    }

    public int getConsultantsVP() {
        Multiset<ConsultantType> consultants = HashMultiset.create(this.consultants);
        List<ConsultantType> bestMatchConsultants = rankBestConsultantToMatch(consultants);
        int vp = 0;
        // punto extra por tener 4 consultores diferentes
        if (consultants.elementSet().size() >= 4) {
            vp++;
        }
        while (consultants.size() > 1 && bestMatchConsultants.size() > 1) {
            vp++;
            consultants.remove(bestMatchConsultants.get(0), 1);
            consultants.remove(bestMatchConsultants.get(1), 1);
            bestMatchConsultants = rankBestConsultantToMatch(consultants);
        }
        return vp;
    }

    private List<ConsultantType> rankBestConsultantToMatch(Multiset<ConsultantType> consultants) {
        return consultants.entrySet().stream()
            .sorted(Comparator.<Multiset.Entry<ConsultantType>>comparingInt(Multiset.Entry::getCount).reversed())
            .map(Multiset.Entry::getElement)
            .toList();
    }
}
