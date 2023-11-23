package us.lsi.dp1.newcorporder.match.turn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.BasicInfiltrate;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

@MockitoSettings
class InfiltrateTurnTest {

    @Mock TurnSystem turnSystem;

    @Mock UseConsultantRequest useConsultantRequest;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference;
    @Mock CompanyTile companyTile;
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
        Mockito.when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        Mockito.when(tileReference.fromMatch(match)).thenReturn(companyTile);
    }

    //
    // Basic infiltrate
    //

    @Test
    void whenBasicInfiltrate_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquarters() {
        // given
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
    }




}
