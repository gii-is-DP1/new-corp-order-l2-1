package us.lsi.dp1.newcorporder.match.turn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.*;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.BasicInfiltrate;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;
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
    @Mock InfiltrateRequest infiltrateRequest;
    Infiltrate infiltrate;
    @Mock TakeConsultantRequest takeConsultantRequest;

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
        currentPlayer.getHeadquarter().addConsultant(ConsultantType.MEDIA_ADVISOR);
        companyTile = CompanyTile.builder()
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
    }

    //
    // Basic infiltrate
    //

    @Test
    void whenInfiltrateRequest_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile_responseIsSendCorrectly() {
        infiltrate = basicInfiltrateBuilder();
        Mockito.when(tileReference.fromMatch(match)).thenReturn(companyTile);
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        Mockito.when(infiltrateRequest.getInfiltrate()).thenReturn(infiltrate);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.INFILTRATE)
            .useConsultantRequest(useConsultantRequest)
            .build();

        InfiltrateResponse response = infiltrateTurn.onInfiltrateRequest(infiltrateRequest);

        Assertions.assertEquals(new InfiltrateResponse(InfiltrateTurn.State.TAKING_CONSULTANT), response);
        Assertions.assertEquals(1, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(3, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(4, companyTile.getAgents());
    }

    //
    // Use Consultant Request
    //
    @Test
    void whenUseConsultantRequest_responseIsSendCorrectly() {
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.SELECTING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        UseConsultantResponse response = infiltrateTurn.onUseConsultantRequest(useConsultantRequest);

        Assertions.assertEquals(new UseConsultantResponse(InfiltrateTurn.State.INFILTRATE), response);
        Assertions.assertEquals(0, currentPlayer.getHeadquarter().getConsultants().count(ConsultantType.MEDIA_ADVISOR));
    }

    @Test
    void givenUseConsultantRequest_whenMediaAdvisorIsUsedAndOnlyOneConglomerateTypeInHand_throwsException() {
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        currentPlayer.discardSharesFromHand(Conglomerate.OMNICORP, 2);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.SELECTING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> infiltrateTurn.onUseConsultantRequest(useConsultantRequest));
    }

    @Test
    void givenUseConsultantRequest_whenInvalidConsultantIsUsed_throwsException() {
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MILITARY_CONTRACTOR);
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.SELECTING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> infiltrateTurn.onUseConsultantRequest(useConsultantRequest));
    }

    //
    // Take Consultant Request
    //

    @Test
    void givenTakeConsultantRequest_ConsultantIsTaken(){
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        Mockito.when(takeConsultantRequest.getConsultant()).thenReturn(ConsultantType.MILITARY_CONTRACTOR);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.TAKING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        infiltrateTurn.onTakeConsultantRequest(takeConsultantRequest);

        Assertions.assertEquals(1, currentPlayer.getHeadquarter().getConsultants().count(ConsultantType.MILITARY_CONTRACTOR));
    }

    @Test
    void givenTakeConsultantRequest_whenUsedConsultantIsTaken_throwsException(){
        Mockito.when(takeConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        Mockito.when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.TAKING_CONSULTANT)
            .useConsultantRequest(useConsultantRequest)
            .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> infiltrateTurn.onTakeConsultantRequest(takeConsultantRequest));
    }


    private BasicInfiltrate basicInfiltrateBuilder() {
        return BasicInfiltrate.builder()
            .tile(tileReference)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(3)
            .build();
    }


}
