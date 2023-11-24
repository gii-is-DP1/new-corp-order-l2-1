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
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.CorporateLawyerInfiltrate;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

@MockitoSettings
class InfiltrateTurnTest {

    @Mock TurnSystem turnSystem;

    @Mock UseConsultantRequest useConsultantRequest;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference1;
    @Mock CompanyTileReference tileReference2;
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
        Mockito.when(tileReference1.fromMatch(match)).thenReturn(companyTile);
    }

    //
    // Basic infiltrate
    //

    @Test
    void whenInfiltrateRequest_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile() {
        // given
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        infiltrate = basicInfiltrateBuilder();
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
        Assertions.assertEquals(1, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(3, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        Assertions.assertEquals(4, companyTile.getAgents());
    }

    @Test
    void whenInfiltrateRequest_responseIsSentCorrectly(){
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        infiltrate = basicInfiltrateBuilder();
        infiltrateRequest = InfiltrateRequest.builder()
            .infiltrate(infiltrate)
            .build();
        InfiltrateTurn infiltrateTurn = InfiltrateTurn.builder()
            .match(match)
            .currentState(InfiltrateTurn.State.INFILTRATE)
            .useConsultantRequest(useConsultantRequest)
            .build();
        // when
        InfiltrateResponse response = infiltrateTurn.onInfiltrateRequest(infiltrateRequest);
        // then
        Assertions.assertEquals(new InfiltrateResponse(InfiltrateTurn.State.TAKING_CONSULTANT), response);
    }

    private BasicInfiltrate basicInfiltrateBuilder(){
        return BasicInfiltrate.builder()
            .tile(tileReference1)
            .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
            .numberOfShares(3)
            .build();
    }




}
