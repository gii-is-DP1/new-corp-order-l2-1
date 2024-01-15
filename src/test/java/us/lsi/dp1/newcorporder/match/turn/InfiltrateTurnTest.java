package us.lsi.dp1.newcorporder.match.turn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.InfiltrateTurn.State;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

@MockitoSettings
class InfiltrateTurnTest {

    @Mock TurnSystem turnSystem;
    @Mock GeneralSupply generalSupply;

    Match match;
    MatchPlayer currentPlayer = playerWithId(1);

    @BeforeEach
    void setUp() {
        match = Match.builder()
            .maxPlayers(4)
            .mode(MatchMode.NORMAL)
            .generalSupply(generalSupply)
            .turnSystem(turnSystem)
            .build();

        lenient().when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
    }

    //
    // InfiltrateTurn#onUseConsultantRequest
    //

    @ParameterizedTest
    @EnumSource(value = State.class, names = "SELECTING_CONSULTANT", mode = EnumSource.Mode.EXCLUDE)
    void givenStateOtherThanSelectingConsultant_whenUsingConsultant_exceptionIsThrown(State state) {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(state)
            .build();

        assertThatThrownBy(() -> turn.onUseConsultantRequest(new UseConsultantRequest(null)))
            .hasMessageContaining("invalid action for the current state (%s)", State.SELECTING_CONSULTANT);
    }

    @Test
    void givenCorporateLawyerConsultantAndPlayingWith2Players_whenUsingConsultant_exceptionIsThrown() {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.SELECTING_CONSULTANT)
            .build();

        when(turnSystem.getPlayers()).thenReturn(List.of(playerWithId(1), playerWithId(2)));
        UseConsultantRequest request = new UseConsultantRequest(ConsultantType.CORPORATE_LAWYER);

        assertThatThrownBy(() -> turn.onUseConsultantRequest(request))
            .hasMessageContaining("you cannot use the consultant 'Corporate lawyer'");
    }

    @Test
    void givenMediaAdvisorConsultantAndOnlyOneTypeOfConglomerateInHand_whenUsingConsultant_exceptionIsThrown() {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.SELECTING_CONSULTANT)
            .build();

        UseConsultantRequest request = new UseConsultantRequest(ConsultantType.MEDIA_ADVISOR);
        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 4);
        currentPlayer.getHeadquarter().addConsultant(ConsultantType.MEDIA_ADVISOR, 2);

        assertThatThrownBy(() -> turn.onUseConsultantRequest(request))
            .hasMessageContaining("you cannot use the consultant 'Media Advisor' if you only have one type of conglomerate share in hand");
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = {"MEDIA_ADVISOR", "CORPORATE_LAWYER"}, mode = EnumSource.Mode.EXCLUDE)
    void givenSelectingConsultantStateAndInvalidConsultant_whenUsingConsultant_exceptionIsThrown(ConsultantType consultantType) {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.SELECTING_CONSULTANT)
            .build();

        assertThatThrownBy(() -> turn.onUseConsultantRequest(new UseConsultantRequest(consultantType)))
            .hasMessageContaining("invalid consultant for an infiltrate turn");
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = {"MEDIA_ADVISOR", "CORPORATE_LAWYER"})
    void givenSelectingConsultantStateAndValidConsultant_whenUsingConsultant_consultantIsUsedAndRemovedFromHand(ConsultantType consultantType) {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.SELECTING_CONSULTANT)
            .build();

        UseConsultantRequest request = new UseConsultantRequest(consultantType);
        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 4);
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 2);
        currentPlayer.getHeadquarter().addConsultant(consultantType, 2);

        UseConsultantResponse response = turn.onUseConsultantRequest(request);

        assertThat(response.getNextState()).isEqualTo(State.INFILTRATE);
        assertThat(currentPlayer.getHeadquarter().getConsultants().count(consultantType)).isEqualTo(1);
        assertThat(turn.getUseConsultantRequest()).isEqualTo(request);
    }

    //
    // InfiltrateTurn#onInfiltrateRequest
    //

    @ParameterizedTest
    @EnumSource(value = State.class, names = "INFILTRATE", mode = EnumSource.Mode.EXCLUDE)
    void givenStateOtherThanInfiltrate_whenInfiltrating_exceptionIsThrown(State state) {
        Infiltrate action = mock(Infiltrate.class);

        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(state)
            .build();

        assertThatThrownBy(() -> turn.onInfiltrateRequest(new InfiltrateRequest(action)))
            .hasMessageContaining("invalid action for the current state (%s)", State.INFILTRATE);
    }

    @Test
    void givenInfiltrateStateWithLessThan3Shares_whenInfiltrating_actionIsExecutedAndTurnIsPassed() {
        Infiltrate action = mock(Infiltrate.class);
        when(action.getTotalNumberOfShares()).thenReturn(2);

        UseConsultantRequest useConsultantRequest = new UseConsultantRequest();
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.INFILTRATE)
            .useConsultantRequest(useConsultantRequest)
            .build();

        InfiltrateResponse response = turn.onInfiltrateRequest(new InfiltrateRequest(action));

        verify(action).apply(match, useConsultantRequest);
        assertThat(response.getNextState()).isEqualTo(State.NONE);
        verify(turnSystem).passTurn();
    }

    @Test
    void givenInfiltrateStateWith3Shares_whenInfiltrating_actionIsExecutedAndNextStateIsTakingConsultant() {
        Infiltrate action = mock(Infiltrate.class);
        when(action.getTotalNumberOfShares()).thenReturn(3);

        UseConsultantRequest useConsultantRequest = new UseConsultantRequest();
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.INFILTRATE)
            .useConsultantRequest(useConsultantRequest)
            .build();

        InfiltrateResponse response = turn.onInfiltrateRequest(new InfiltrateRequest(action));

        verify(action).apply(match, useConsultantRequest);
        assertThat(response.getNextState()).isEqualTo(State.TAKING_CONSULTANT);
        verify(turnSystem, never()).passTurn();
    }

    //
    // InfiltrateTurn#onTakeConsultantRequest
    //

    @ParameterizedTest
    @EnumSource(value = State.class, names = "TAKING_CONSULTANT", mode = EnumSource.Mode.EXCLUDE)
    void givenStateOtherThanTakingConsultant_whenTakingConsultant_exceptionIsThrown(State state) {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(state)
            .build();

        assertThatThrownBy(() -> turn.onTakeConsultantRequest(new TakeConsultantRequest()))
            .hasMessageContaining("invalid action for the current state (%s)", State.TAKING_CONSULTANT);
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = {"MEDIA_ADVISOR", "CORPORATE_LAWYER"})
    void givenTakingConsultantStateAndSameConsultantAsUsed_whenTakingConsultant_exceptionIsThrown(ConsultantType consultantType) {
        UseConsultantRequest useConsultantRequest = new UseConsultantRequest(consultantType);

        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.TAKING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        assertThatThrownBy(() -> turn.onTakeConsultantRequest(new TakeConsultantRequest(consultantType)))
            .hasMessageContaining("you cannot take the same consultant you used to infiltrate the company");
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = {"MEDIA_ADVISOR", "CORPORATE_LAWYER"})
    void givenTakingConsultantStateAndDifferentConsultantThanUsed_whenTakingConsultant_consultantIsTaken(ConsultantType consultantType) {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .currentState(State.TAKING_CONSULTANT)
            .useConsultantRequest(new UseConsultantRequest())
            .build();

        turn.onTakeConsultantRequest(new TakeConsultantRequest(consultantType));

        verify(generalSupply).takeConsultant(consultantType);
        assertThat(currentPlayer.getHeadquarter().getConsultants()).containsExactly(consultantType);
    }

    //
    // InfiltrateTurn#onDiscardShareRequest
    //

    @Test
    void whenDiscardingShare_exceptionIsThrown() {
        InfiltrateTurn turn = InfiltrateTurn.builder()
            .match(match)
            .build();

        assertThatCode(() -> turn.onDiscardShareRequest(new DiscardShareRequest()))
            .hasMessageContaining("invalid move for the current action");
    }
}
