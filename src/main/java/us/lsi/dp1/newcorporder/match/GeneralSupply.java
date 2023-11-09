package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
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
    private final Multiset<Conglomerate> openDisplay = HashMultiset.create();

    private GeneralSupply() {
    }

    public void init(MatchMode matchMode, int numberOfPlayers) {
        this.fillDeck(matchMode);
        this.fillConsultants(numberOfPlayers);

        // reveal the first 5 cards from the deck to the open display
        this.revealConglomerateSharesToOpenDisplay(5);
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
        Preconditions.checkState(this.deck.size() < sharesToTake,
            "there are no enough conglomerate shares left in the deck to take");

        List<Conglomerate> conglomerateShares = new ArrayList<>();
        for (int i = 0; i < sharesToTake; i++) {
            conglomerateShares.add(this.takeConglomerateShareFromDeck());
        }

        return conglomerateShares;
    }

    /**
     * Removes a share of the given conglomerate from the open display.
     *
     * @param conglomerate the conglomerate to take the share of
     * @throws IllegalStateException if there are no shares of the given conglomerate in the open display
     */
    public void takeConglomerateShareFromOpenDisplay(Conglomerate conglomerate) {
        Preconditions.checkState(this.openDisplay.contains(conglomerate),
            "there are no shares for the given conglomerate");

        this.openDisplay.remove(conglomerate);
    }

    /**
     * Polls the given number of conglomerate shares from the deck and places them into the open display.
     *
     * @param sharesToTake the number of conglomerate shares to take from the deck and place them into the open display
     * @return the shares taken from the deck
     * @throws IllegalArgumentException if the given number of shares to take is not greater than 0
     * @throws IllegalStateException    if there are no enough conglomerate shares left in the deck to take, or the current
     *                                  number of shares in the open display + the given number of shares to take is grater
     *                                  than 5
     */
    public List<Conglomerate> revealConglomerateSharesToOpenDisplay(int sharesToTake) {
        Preconditions.checkArgument(sharesToTake > 0, "cannot reveal less than 1 share");
        Preconditions.checkState(this.openDisplay.size() + sharesToTake > 5,
            "open display size would be greater than 5");

        List<Conglomerate> sharesTaken = this.takeConglomerateSharesFromDeck(sharesToTake);
        this.openDisplay.addAll(sharesTaken);
        return sharesTaken;
    }

    /**
     * Gets the conglomerate shares in the open display.
     *
     * @return an immutable view of the conglomerate shares in the open display
     */
    public Multiset<Conglomerate> getOpenDisplay() {
        return ImmutableMultiset.copyOf(openDisplay);
    }
}
