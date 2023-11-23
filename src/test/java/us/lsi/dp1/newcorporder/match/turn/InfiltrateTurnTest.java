package us.lsi.dp1.newcorporder.match.turn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.*;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.BasicInfiltrate;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@MockitoSettings
class InfiltrateTurnTest {

    @Mock TurnSystem turnSystem;

    @Mock UseConsultantRequest useConsultantRequest;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference;
    CompanyTile companyTile;
    Match match;
    MatchPlayer currentPlayer;
    InfiltrateRequest infiltrateRequest;
    Infiltrate infiltrate;

    @BeforeEach
    void setUp() {
        match = Match.builder()
            .maxPlayers(4)
            .matchMode(MatchMode.NORMAL)
            .generalSupply(generalSupply)
            .turnSystem(turnSystem)
            .build();
        currentPlayer = new MatchPlayer(1, Headquarter.create());
        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 4);
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 2);
        companyTile = CompanyTile.builder()
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        Mockito.when(tileReference.fromMatch(match)).thenReturn(companyTile);
    }

    //
    // Basic infiltrate
    //

    @Test
    void whenBasicInfiltrate_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile() {
        // given
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        infiltrate = BasicInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(2)
            .build();
        infiltrateRequest = InfiltrateRequest.builder()
            .infiltrate(infiltrate)
            .build();
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.INFILTRATE)
            .useConsultantRequest(useConsultantRequest)
            .build();
        // when
        infiltrateTurn.onInfiltrateRequest(infiltrateRequest);
        // then
        Assertions.assertEquals(2, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(2, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(3, companyTile.getAgents());
    }

    @Test
    void givenBasicInfiltrate_whenTileIsFromDifferentConglomerate_ExceptionIsTrown() {
        // given
        infiltrate = BasicInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.OMNICORP)
            .numberOfShares(2)
            .build();
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "you cannot add agents to a tile that has agents from a different conglomerate");
    }

    @ParameterizedTest
    @EnumSource(
        value = ConsultantType.class,
        names = {"MEDIA_ADVISOR", "CORPORATE_LAWYER"},
        mode = EnumSource.Mode.EXCLUDE
    )
    void givenBasicInfiltrate_whenConsultantIsUsed_ExceptionIsTrown(ConsultantType consultantType) {
        // given
        infiltrate = BasicInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(2)
            .build();
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(consultantType);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "the infiltrate must be the same type as the consultant used");
    }

}
