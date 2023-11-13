package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
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
     * @param quantity the quantity of shares to remove
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
}
