package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

@MockitoSettings
public class MediaAdvisorInfiltrateTest {

    @Mock TurnSystem turnSystem;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference;

    MatchPlayer currentPlayer = playerWithId(1);
    Match match;

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void givenInfiltrate_whenGettingTotalNumberOfShares_returnsSameAsGivenPlusOne(int numberOfShares) {
        MediaAdvisorInfiltrate action = MediaAdvisorInfiltrate.builder()
            .numberOfShares(numberOfShares)
            .build();

        assertThat(action.getTotalNumberOfShares()).isEqualTo(numberOfShares + 1);
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = "MEDIA_ADVISOR", mode = EnumSource.Mode.EXCLUDE)
    void givenConsultantDifferentThanMediaAdvisor_whenInfiltrating_exceptionIsThrown(ConsultantType consultantType) {
        CompanyTile tile = CompanyTile.builder()
            .agents(1)
            .build();

        MediaAdvisorInfiltrate action = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(consultantType)))
            .hasMessageContaining("invalid request for the selected consultant: %s", consultantType);
        assertThat(tile.getAgents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(Conglomerate.class)
    void givenTileWithDifferentConglomerateThanWantedToInfiltrate_whenInfiltrating_exceptionIsThrown(Conglomerate conglomerate) {
        CompanyTile tile = CompanyTile.builder()
            .currentConglomerate(conglomerate)
            .agents(1)
            .build();
        when(tileReference.fromMatch(match)).thenReturn(tile);

        MediaAdvisorInfiltrate action = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(conglomerate.nextValue())
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(ConsultantType.MEDIA_ADVISOR)))
            .hasMessageContaining("cannot add agents to a tile that has agents from a different conglomerate");
        assertThat(tile.getAgents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(Conglomerate.class)
    void givenSameConglomerateAsExtraConglomerate_whenInfiltrating_exceptionIsThrown(Conglomerate conglomerate) {
        CompanyTile tile = CompanyTile.builder()
            .currentConglomerate(conglomerate)
            .agents(1)
            .build();
        when(tileReference.fromMatch(match)).thenReturn(tile);

        MediaAdvisorInfiltrate action = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(conglomerate)
            .numberOfShares(1)
            .extraConglomerate(conglomerate)
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(ConsultantType.MEDIA_ADVISOR)))
            .hasMessageContaining("your extra conglomerate share cannot be the same type as your main conglomerate shares");
        assertThat(tile.getAgents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(Conglomerate.class)
    void givenValidArguments_whenInfiltrating_sharesAreMovedToHeadquarterAndAgentsAreAddedToTile(Conglomerate conglomerate) {
        CompanyTile tile = CompanyTile.builder()
            .currentConglomerate(conglomerate)
            .agents(1)
            .build();
        when(tileReference.fromMatch(match)).thenReturn(tile);

        MediaAdvisorInfiltrate action = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(conglomerate)
            .numberOfShares(2)
            .extraConglomerate(conglomerate.nextValue())
            .build();

        currentPlayer.addSharesToHand(conglomerate, 2);
        currentPlayer.addSharesToHand(conglomerate.nextValue(), 1);
        action.apply(match, new UseConsultantRequest(ConsultantType.MEDIA_ADVISOR));

        assertThat(currentPlayer.getHand()).isEmpty();
        assertThat(currentPlayer.getHeadquarter().getConglomerateShares(conglomerate)).isEqualTo(3);
        assertThat(tile.getAgents()).isEqualTo(4);
        assertThat(currentPlayer.getShareUses()).hasSize(3);
    }
}
