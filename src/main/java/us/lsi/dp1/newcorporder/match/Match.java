package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeShareRequest;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import java.util.*;

public class Match {

    public static final int INITIAL_CONGLOMERATE_SHARES_PER_PLAYER = 4;
    public static final int SHARES_IN_OPEN_DISPLAY = 4;
    public static final int MAX_SHARES_IN_HAND = 6;

    public static Match create(int maxPlayers, MatchMode matchMode) {
        GeneralSupply generalSupply = GeneralSupply.create();
        CompanyMatrix companyMatrix = CompanyMatrix.create();
        String inviteCode = "foo"; // TODO make code random

        return new Match(maxPlayers, matchMode, inviteCode, generalSupply, companyMatrix);
    }

    @Getter private final int maxPlayers;
    @Getter private final MatchMode matchMode;
    @Getter private final String inviteCode;

    @Getter private final GeneralSupply generalSupply;
    @Getter private final CompanyMatrix companyMatrix;
    @Getter private final TurnSystem turnSystem;

    @Getter private MatchState matchState = MatchState.WAITING;
    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    private Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
        this.turnSystem = new TurnSystem(this);
    }

    public void init() {
        generalSupply.init(matchMode, players.size());
        companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);

        initPlayers();

        turnSystem.init(new ArrayList<>(players.values()));
        matchState = MatchState.PLAYING;
    }

    private void initPlayers() {
        ConsultantInitializer consultantInitializer = new ConsultantInitializer(players.size());

        for (MatchPlayer player : getPlayers())
            initPlayer(player, consultantInitializer);
    }

    private void initPlayer(MatchPlayer player, ConsultantInitializer consultantInitializer) {
        List<Conglomerate> initialHand = drawInitialHand();
        ConsultantType initialConsultant = consultantInitializer.getRandomUniqueConsultant();
        player.init(initialConsultant, initialHand);
    }

    //
    // Plot
    //

    private void takeShare(TakeShareRequest takeShareRequest) {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.SELECTING_FIRST_SHARE
                                 || turnSystem.getCurrentState() == MatchTurnState.SELECTING_SECOND_SHARE,
            "illegal turn state");

        Conglomerate share = switch (takeShareRequest.getSource()) {
            case DECK -> this.generalSupply.takeConglomerateShareFromDeck();
            case OPEN_DISPLAY -> {
                Preconditions.checkNotNull(takeShareRequest.getConglomerate(),
                    "conglomerate must be specified to take a share from the open display");

                this.generalSupply.takeConglomerateShareFromOpenDisplay(takeShareRequest.getConglomerate());
                yield takeShareRequest.getConglomerate();
            }
        };

        turnSystem.getCurrentPlayer().addShareToHand(share);
        this.nextPlotTurnState();
    }

    private void discardShares(DiscardShareRequest discardShareRequest) {
        Preconditions.checkState(turnSystem.getCurrentState() == MatchTurnState.DISCARDING_SHARES_FROM_HAND,
            "cannot discard a share on your turn state");
        Preconditions.checkState(turnSystem.getCurrentPlayer().getHand().size() - discardShareRequest.getSharesToDiscard().size() == MAX_SHARES_IN_HAND,
            "you have to discard the necessary number of shares to have exactly %d left on your hand",
            MAX_SHARES_IN_HAND);

        // for each given conglomerate and number of shares, discard them from the player's hand
        discardShareRequest.getSharesToDiscard().forEachEntry(turnSystem.getCurrentPlayer()::discardSharesFromHand);

        this.nextPlotTurnState();
    }

    private void nextPlotTurnState() {
        // if just took the first share, the next turn state is SELECTING_SECOND_SHARE
        if (turnSystem.getCurrentState() == MatchTurnState.SELECTING_FIRST_SHARE) {
            turnSystem.setState(MatchTurnState.SELECTING_SECOND_SHARE);
            return;
        }

        // if the player has more than MAX_SHARES_IN_HAND shares in his hand, the next turn state is DISCARD_SHARES_FROM_HAND
        if (turnSystem.getCurrentPlayer().getHand().size() > MAX_SHARES_IN_HAND) {
            turnSystem.setState(MatchTurnState.DISCARDING_SHARES_FROM_HAND);
            return;
        }

        // now, refill the missing shares in the open display
        int currentSharesInOpenDisplay = this.generalSupply.getOpenDisplay().size();
        if (currentSharesInOpenDisplay < SHARES_IN_OPEN_DISPLAY) {
            try {
                this.generalSupply.revealConglomerateSharesToOpenDisplay(SHARES_IN_OPEN_DISPLAY - currentSharesInOpenDisplay);
            } catch (IllegalStateException e) {
                // TODO call final round
            }
        }

        turnSystem.passTurn();
    }

    private List<Conglomerate> drawInitialHand() {
        return generalSupply.takeConglomerateSharesFromDeck(INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
    }

    private Collection<MatchPlayer> getPlayers() {
        return players.values();
    }
}
