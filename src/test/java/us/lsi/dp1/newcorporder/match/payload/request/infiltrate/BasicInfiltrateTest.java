package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

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
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

@MockitoSettings
class BasicInfiltrateTest {

    @Mock TurnSystem turnSystem;

    @Mock UseConsultantRequest useConsultantRequest;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference;
    CompanyTile companyTile;
    Match match;
    MatchPlayer currentPlayer;
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
        companyTile = CompanyTile.builder()
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        Mockito.when(tileReference.fromMatch(match)).thenReturn(companyTile);
        infiltrate = BasicInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(2)
            .build();
    }

    @Test
    void whenBasicInfiltrate_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile() {
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);

        infiltrate.run(match, useConsultantRequest);

        Assertions.assertEquals(2, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(2, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(3, companyTile.getAgents());
    }

    @Test
    void givenBasicInfiltrate_whenTileIsFromDifferentConglomerate_ExceptionIsTrown() {
        Mockito.when(tileReference.fromMatch(match)).thenReturn(CompanyTile.builder()
            .currentConglomerate(Conglomerate.OMNICORP)
            .agents(1)
            .build());
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
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(consultantType);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "the infiltrate must be the same type as the consultant used");
    }

}
