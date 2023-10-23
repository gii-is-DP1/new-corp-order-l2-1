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

    public void init(MatchMode matchMode, int numberOfPlayers) {
        this.fillDeck(matchMode);
        this.fillConsultants(numberOfPlayers);
        this.fillOpenDisplay();
    }

    private void fillDeck(MatchMode matchMode) {
        LinkedList<Conglomerate> deck = new LinkedList<>();

        // add matchMode#getSharesPerConglomerateInDeck() shares of each Conglomerate to the deck
        for (int i = 0; i < matchMode.getSharesPerConglomerateInDeck(); i++) {
            deck.addAll(Arrays.asList(Conglomerate.values()));
        }

        Collections.shuffle(deck);
        this.deck.addAll(deck);
    }

    private void fillConsultants(int numberOfPlayers) {
        // there must be (numberOfPlayers - 1) consultants of each type and, if there are less than 3 players,
        // the CORPORATE_LAWYER is not used
        for (ConsultantType consultantType : ConsultantType.values()) {
            if (numberOfPlayers > 2 || consultantType != ConsultantType.CORPORATE_LAWYER) {
                consultants.add(consultantType, numberOfPlayers - 1);
            }
        }
    }

    private void fillOpenDisplay() {
        // the first 5 cards of the deck are revealed and placed into the open display
        for (int i = 0; i < 5; i++) {
            Conglomerate conglomerateShare = this.takeConglomerateShareFromDeck();
            openDisplay.add(conglomerateShare);
        }
    }

    /**
     * Gets the consultants left by type.
     *
     * @return the consultants left by type
     */
    public Multiset<ConsultantType> getConsultantsLeft() {
        return ImmutableMultiset.copyOf(consultants);
    }

    /**
     * Gets the number of consultants left for the given type.
     *
     * @param consultantType the consultant type
     * @return the number of consultant left for the given type
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
     *
     * @return the number of conglomerate shares remaining in the deck
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

    /**
     * Polls the given number of conglomerate shares from the deck.
     *
     * @param sharesToTake the number of conglomerate shares to take from the deck
     * @return the conglomerate shares taken from the deck
     * @throws IllegalStateException if there are no enough conglomerate shares left in the deck to take
     */
    public List<Conglomerate> takeConglomerateSharesFromDeck(int sharesToTake) {
        if (this.deck.size() < sharesToTake) {
            throw new IllegalStateException("there are no enough conglomerate shares left in the deck to take");
        }

        List<Conglomerate> conglomerateShares = new ArrayList<>();
        for (int i = 0; i < sharesToTake; i++) {
            conglomerateShares.add(this.takeConglomerateShareFromDeck());
        }

        return conglomerateShares;
    }

    /**
     * Gets the conglomerate shares in the open display.
     *
     * @return an immutable view of the conglomerate shares in the open display
     */
    public List<Conglomerate> getOpenDisplay() {
        return ImmutableList.copyOf(openDisplay);
    }
}
