package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

import java.util.*;

public class GeneralSupply {

    /**
     * Builds a new general supply.
     *
     * @return the new general supply
     */
    public static GeneralSupply create() {
        return new GeneralSupply();
    }

    private final Queue<Conglomerate> deck = new LinkedList<>();
    private final Multiset<ConsultantType> consultants = HashMultiset.create();
    private final List<Conglomerate> openDisplay = new ArrayList<>(5);

    private GeneralSupply() {
    }

    public void init(Queue<Conglomerate> deck, int numberOfPlayers) {
        this.deck.addAll(deck);

        // there must be (numberOfPlayers - 1) consultants of each type and, if there are less than 3 players,
        // the CORPORATE_LAWYER is not used
        for (ConsultantType consultantType : ConsultantType.values()) {
            if (numberOfPlayers > 2 || consultantType != ConsultantType.CORPORATE_LAWYER) {
                consultants.add(consultantType, numberOfPlayers - 1);
            }
        }

        // the first 5 cards of the deck are revealed and placed into the open display
        for (int i = 0; i < 5; i++) {
            openDisplay.add(this.deck.remove());
        }
    }

    public Multiset<ConsultantType> getConsultantsLeft() {
        return ImmutableMultiset.copyOf(consultants);
    }

    /**
     * Gets the number of consultants left for the given type.
     *
     * @param consultantType the consultant type
     */
    public int getConsultantsLeft(ConsultantType consultantType) {
        return this.consultants.count(consultantType);
    }

    /**
     * Removes a consultant of the given type.
     *
     * @param consultantType the type of the consultant to remove
     * @throws java.util.NoSuchElementException if there are no consultants left for the given type
     */
    public void takeConsultant(ConsultantType consultantType) {
        if (!this.consultants.contains(consultantType)) {
            throw new NoSuchElementException("there are no " + consultantType + " consultants left in the general supply");
        }
        this.consultants.remove(consultantType);
    }

    /**
     * Gets the number of conglomerate shares remaining in the deck.
     */
    public int getConglomerateSharesLeftInDeck() {
        return this.deck.size();
    }

    /**
     * Polls a conglomerate share from the deck.
     *
     * @return the conglomerate share taken from the deck
     * @throws java.util.NoSuchElementException if the deck is empty
     */
    public Conglomerate takeConglomerateShareFromDeck() {
        if (this.deck.isEmpty()) {
            throw new NoSuchElementException("there are no shares left in the general supply");
        }
        return this.deck.remove();
    }

    public List<Conglomerate> getOpenDisplay() {
        return ImmutableList.copyOf(openDisplay);
    }
}
