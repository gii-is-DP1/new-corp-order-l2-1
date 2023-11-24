package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class MediaAdvisorInfiltrateTest {

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
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 2);
        companyTile = CompanyTile.builder()
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        Mockito.when(tileReference.fromMatch(match)).thenReturn(companyTile);
        infiltrate = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(2)
            .extraConglomerate(Conglomerate.OMNICORP)
            .build();
    }

    @Test
    void whenMediaAdvisorInfiltrate_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile() {
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        infiltrate.run(match, useConsultantRequest);

        Assertions.assertEquals(2, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(1, currentPlayer.getHand().count(Conglomerate.OMNICORP));
        Assertions.assertEquals(3, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(4, companyTile.getAgents());
    }

    @Test
    void givenMediaAdvisorInfiltrate_whenTileIsFromDifferentConglomerate_ExceptionIsTrown() {
        Mockito.when(tileReference.fromMatch(match)).thenReturn(CompanyTile.builder()
            .currentConglomerate(Conglomerate.OMNICORP)
            .agents(1)
            .build());
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "you cannot add agents to a tile that has agents from a different conglomerate");
    }

    @Test
    void givenMediaAdvisorInfiltrate_whenExtraConglomerateIsSameAsMain_ExceptionIsTrown() {
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        infiltrate = MediaAdvisorInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(2)
            .extraConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "your extra conglomerate share cannot be the same type as your main conglomerate shares");
    }

    @Test
    void givenMediaAdvisorInfiltrate_whenMediaAdvisorIsNotUsed_ExceptionIsTrown() {
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.CORPORATE_LAWYER);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            infiltrate.run(match, useConsultantRequest);
        }, "the infiltrate must be the same type as the consultant used");
    }
}