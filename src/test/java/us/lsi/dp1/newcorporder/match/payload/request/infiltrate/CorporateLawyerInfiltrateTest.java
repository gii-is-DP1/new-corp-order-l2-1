package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.company.Company;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

@MockitoSettings
class CorporateLawyerInfiltrateTest {

    @Mock TurnSystem turnSystem;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference1;
    @Mock CompanyTileReference tileReference2;

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
    void givenInfiltrate_whenGettingTotalNumberOfShares_returnsSumOfTotalSharesOfAllActions(int numberOfShares) {
        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder().numberOfShares(numberOfShares).build(),
                DefaultInfiltrate.builder().numberOfShares(numberOfShares + 1).build()
            ))
            .build();

        assertThat(action.getTotalNumberOfShares()).isEqualTo(numberOfShares * 2 + 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void givenNumberDifferentThan2InfiltrateActions_whenValidating_exceptionIsThrown(int actions) {
        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(IntStream.range(0, actions).mapToObj(i -> DefaultInfiltrate.builder().build()).toList())
            .build();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertThat(validator.validate(action))
            .hasSize(1)
            .first().extracting(ConstraintViolation::getMessage, as(STRING))
            .contains("there must be 2 infiltrate actions when using the corporate lawyer consultant");
    }

    @ParameterizedTest
    @EnumSource(value = ConsultantType.class, names = "CORPORATE_LAWYER", mode = EnumSource.Mode.EXCLUDE)
    void givenConsultantDifferentThanCorporateLawyer_whenInfiltrating_exceptionIsThrown(ConsultantType consultantType) {
        CompanyTile tile = CompanyTile.builder()
            .agents(1)
            .build();
        CompanyTile tile2 = CompanyTile.builder()
            .agents(1)
            .build();

        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder().tile(tileReference1).build(),
                DefaultInfiltrate.builder().tile(tileReference2).build()
            ))
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(consultantType)))
            .hasMessageContaining("invalid request for the selected consultant: %s", consultantType);
        assertThat(tile.getAgents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(Conglomerate.class)
    void givenTheSameConglomerateTwice_whenInfiltrating_exceptionIsThrown(Conglomerate conglomerate) {
        CompanyTile tile = CompanyTile.builder()
            .company(Company.CLICKBAITER)
            .currentConglomerate(conglomerate)
            .agents(1)
            .build();
        CompanyTile tile2 = CompanyTile.builder()
            .company(Company.GENERTIC_SUB_INC)
            .currentConglomerate(conglomerate)
            .agents(1)
            .build();

        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder().tile(tileReference1).conglomerateType(conglomerate).build(),
                DefaultInfiltrate.builder().tile(tileReference2).conglomerateType(conglomerate).build()
            ))
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(ConsultantType.CORPORATE_LAWYER)))
            .hasMessageContaining("the conglomerate shares selected have to be of different types");
        assertThat(tile.getAgents()).isEqualTo(1);
        assertThat(tile2.getAgents()).isEqualTo(1);
    }

    @Test
    void givenInvalidSecondInfiltrate_whenInfiltrating_firstDidNotApply() {
        CompanyTile tile1 = CompanyTile.builder()
            .company(Company.CLICKBAITER)
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        CompanyTile tile2 = CompanyTile.builder()
            .company(Company.GENERTIC_SUB_INC)
            .currentConglomerate(Conglomerate.OMNICORP)
            .agents(2)
            .build();
        when(tileReference1.fromMatch(match)).thenReturn(tile1);
        when(tileReference2.fromMatch(match)).thenReturn(tile2);

        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 2);
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 1);

        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder().tile(tileReference1).conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT).numberOfShares(2).build(),
                DefaultInfiltrate.builder().tile(tileReference2).conglomerateType(Conglomerate.OMNICORP).numberOfShares(2).build()
            ))
            .build();

        assertThatThrownBy(() -> action.apply(match, new UseConsultantRequest(ConsultantType.CORPORATE_LAWYER)))
            .hasMessageContaining("you don't have enough shares of the OMNICORP conglomerate to discard");

        assertThat(currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT)).isEqualTo(2);
        assertThat(currentPlayer.getHand().count(Conglomerate.OMNICORP)).isEqualTo(1);
        assertThat(currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT)).isEqualTo(0);
        assertThat(currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.OMNICORP)).isEqualTo(0);
        assertThat(tile1.getAgents()).isEqualTo(1);
        assertThat(tile2.getAgents()).isEqualTo(2);
        assertThat(currentPlayer.getShareUses()).hasSize(0);
    }

    @Test
    void givenValidArguments_whenInfiltrating_sharesAreMovedToHeadquarterAndAgentsAreAddedToTile() {
        CompanyTile tile1 = CompanyTile.builder()
            .company(Company.CLICKBAITER)
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        CompanyTile tile2 = CompanyTile.builder()
            .company(Company.GENERTIC_SUB_INC)
            .currentConglomerate(Conglomerate.OMNICORP)
            .agents(2)
            .build();
        when(tileReference1.fromMatch(match)).thenReturn(tile1);
        when(tileReference2.fromMatch(match)).thenReturn(tile2);

        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 2);
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 1);

        CorporateLawyerInfiltrate action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder().tile(tileReference1).conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT).numberOfShares(2).build(),
                DefaultInfiltrate.builder().tile(tileReference2).conglomerateType(Conglomerate.OMNICORP).numberOfShares(1).build()
            ))
            .build();

        action.apply(match, new UseConsultantRequest(ConsultantType.CORPORATE_LAWYER));

        assertThat(currentPlayer.getHand()).isEmpty();
        assertThat(currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT)).isEqualTo(2);
        assertThat(currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.OMNICORP)).isEqualTo(1);
        assertThat(tile1.getAgents()).isEqualTo(3);
        assertThat(tile2.getAgents()).isEqualTo(3);
        assertThat(currentPlayer.getShareUses()).hasSize(3);
    }
}
