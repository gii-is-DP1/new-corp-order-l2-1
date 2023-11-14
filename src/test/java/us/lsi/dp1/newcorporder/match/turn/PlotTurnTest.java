package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.collect.HashMultiset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeShareRequest;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.TakeShareResponse;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.PlotTurn.State;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@MockitoSettings
class PlotTurnTest {

    @Mock TurnSystem turnSystem;
    @Mock GeneralSupply generalSupply;

    MatchPlayer currentPlayer = new MatchPlayer(1, Headquarter.create());
    Match match;
    PlotTurn turn;

    @BeforeEach
    void setUp() {
        match = Match.builder()
            .maxPlayers(4)
            .matchMode(MatchMode.NORMAL)
            .generalSupply(generalSupply)
            .turnSystem(turnSystem)
            .build();
        turn = spy(new PlotTurn(match));

        lenient().when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
    }

    //
    // Take share request
    //

    @ParameterizedTest
    @EnumSource(
        value = State.class,
        names = {"SELECTING_FIRST_SHARE", "SELECTING_SECOND_SHARE"},
        mode = EnumSource.Mode.EXCLUDE
    )
    void givenStateOtherThanAnySelectingShare_whenRequestingToTakeShare_throwsException(State state) {
        turn.setCurrentState(state);

        TakeShareRequest request = TakeShareRequest.builder()
            .source(TakeShareRequest.Source.DECK)
            .build();

        assertThatThrownBy(() -> turn.onTakeShareRequest(request))
            .hasMessageContaining("illegal turn state");
    }

    @Test
    void givenDeckSourceAndNoConglomerate_whenRequestingToTakeShare_shareIsTakenFromDeckAndGivenToCurrentPlayer() {
        turn.setCurrentState(State.SELECTING_FIRST_SHARE);
        when(generalSupply.takeConglomerateShareFromDeck()).thenReturn(Conglomerate.GENERIC_INC);

        TakeShareResponse response = turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.DECK)
            .build());

        verify(generalSupply).takeConglomerateShareFromDeck();
        assertThat(currentPlayer.getHand()).containsExactly(Conglomerate.GENERIC_INC);

        assertThat(response.getShareTaken()).isEqualTo(Conglomerate.GENERIC_INC);
    }

    @Test
    void givenOpenDisplaySourceAndNoConglomerate_whenRequestingToTakeShare_throwsException() {
        turn.setCurrentState(State.SELECTING_FIRST_SHARE);

        assertThatThrownBy(() -> turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.OPEN_DISPLAY)
            .build())
        ).hasMessageContaining("conglomerate must be specified");
    }

    @Test
    void givenOpenDisplaySourceAndNoConglomerate_whenRequestingToTakeShare_shareIsTakenFromDeckAndGivenToCurrentPlayer() {
        turn.setCurrentState(State.SELECTING_FIRST_SHARE);

        TakeShareResponse response = turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.OPEN_DISPLAY)
            .conglomerate(Conglomerate.GENERIC_INC)
            .build());

        verify(generalSupply).takeConglomerateShareFromOpenDisplay(Conglomerate.GENERIC_INC);
        assertThat(currentPlayer.getHand()).containsExactly(Conglomerate.GENERIC_INC);

        assertThat(response.getShareTaken()).isEqualTo(Conglomerate.GENERIC_INC);
    }

    @Test
    void givenHandWithExactlyTheAllowedNumberOfShares_whenRequestingToTakeFirstShare_nextStateIsSelectingSecondShare() {
        turn.setCurrentState(State.SELECTING_FIRST_SHARE);
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND);

        TakeShareResponse response = turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.OPEN_DISPLAY)
            .conglomerate(Conglomerate.GENERIC_INC)
            .build());

        assertThat(currentPlayer.getHand()).hasSize(Match.MAX_SHARES_IN_HAND + 1);
        assertThat(response.getNextState()).isEqualTo(State.SELECTING_SECOND_SHARE);
    }

    @Test
    void givenHandWithExactlyTheAllowedNumberOfShares_whenRequestingToTakeSecondShare_nextStateIsDiscardingShares() {
        turn.setCurrentState(State.SELECTING_SECOND_SHARE);
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND);

        TakeShareResponse response = turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.OPEN_DISPLAY)
            .conglomerate(Conglomerate.GENERIC_INC)
            .build());

        assertThat(currentPlayer.getHand()).hasSize(Match.MAX_SHARES_IN_HAND + 1);
        assertThat(response.getNextState()).isEqualTo(State.DISCARDING_SHARES_FROM_HAND);
    }

    @Test
    void given_whenRequestingToTakeSecondShare_endTurnIsCalled() {
        turn.setCurrentState(State.SELECTING_SECOND_SHARE);
        when(generalSupply.takeConglomerateShareFromDeck()).thenReturn(Conglomerate.GENERIC_INC);
        doNothing().when(turn).endTurn();

        TakeShareResponse response = turn.onTakeShareRequest(TakeShareRequest.builder()
            .source(TakeShareRequest.Source.DECK)
            .build());

        verify(turn).endTurn();
    }

    //
    // Discard shares request
    //

    @ParameterizedTest
    @EnumSource(
        value = State.class,
        names = {"DISCARDING_SHARES_FROM_HAND"},
        mode = EnumSource.Mode.EXCLUDE
    )
    void givenStateOtherThanDiscardingShares_whenRequestingToDiscardShares_throwsException(State state) {
        turn.setCurrentState(state);

        assertThatThrownBy(() -> turn.onDiscardShareRequest(new DiscardShareRequest()))
            .hasMessageContaining("cannot discard a share on your turn state");
    }

    @Test
    void givenSharesToDiscard_whenRequestingToDiscardShares_endTurnIsCalled() {
        turn.setCurrentState(State.DISCARDING_SHARES_FROM_HAND);
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND + 1);
        doNothing().when(turn).endTurn();

        HashMultiset<Conglomerate> shares = HashMultiset.create(List.of(Conglomerate.GENERIC_INC));
        DiscardShareResponse response = turn.onDiscardShareRequest(new DiscardShareRequest(shares));

        verify(turn).endTurn();
    }

    //
    // Turn ending
    //

    @Test
    void givenLessSharesInOpenDisplayThanNeeded_whenTurnEnds_neededSharesAreRevealedAndTurnIsPassed() {
        when(generalSupply.getOpenDisplay()).thenReturn(HashMultiset.create(List.of(Conglomerate.GENERIC_INC)));

        turn.endTurn();

        verify(generalSupply).revealConglomerateSharesToOpenDisplay(Match.SHARES_IN_OPEN_DISPLAY - 1);
        verify(turnSystem).passTurn();
    }

    @Test
    void givenLessSharesInOpenDisplayThanNeededAndDeckWithNotEnoughShares_whenTurnEnds_activatesFinalRound() {
        when(generalSupply.getOpenDisplay()).thenReturn(HashMultiset.create(List.of(Conglomerate.GENERIC_INC)));
        when(generalSupply.revealConglomerateSharesToOpenDisplay(anyInt())).thenThrow(new IllegalStateException()); // not enough shares in the deck

        turn.endTurn();

        verify(generalSupply).revealConglomerateSharesToOpenDisplay(Match.SHARES_IN_OPEN_DISPLAY - 1);
        verify(turnSystem).activateFinalRound();
        verify(turnSystem).passTurn();
    }
}
