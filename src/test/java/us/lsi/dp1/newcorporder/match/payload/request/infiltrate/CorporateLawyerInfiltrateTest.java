package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

@MockitoSettings
class CorporateLawyerInfiltrateTest {

    @Mock TurnSystem turnSystem;

    @Mock UseConsultantRequest useConsultantRequest;
    @Mock GeneralSupply generalSupply;
    @Mock CompanyTileReference tileReference1;
    @Mock CompanyTileReference tileReference2;
    CompanyTile companyTile1;
    CompanyTile companyTile2;
    Match match;
    MatchPlayer currentPlayer;
    InfiltrateRequest infiltrateRequest;
    Infiltrate action;

    @Mock DefaultInfiltrate defaultInfiltrateMock;

    @BeforeEach
    void setUp() {
        match = Match.builder()
            .maxPlayers(4)
            .mode(MatchMode.NORMAL)
            .generalSupply(generalSupply)
            .turnSystem(turnSystem)
            .build();
        currentPlayer = playerWithId(1);
        currentPlayer.addSharesToHand(Conglomerate.TOTAL_ENTERTAINMENT, 4);
        currentPlayer.addSharesToHand(Conglomerate.OMNICORP, 2);
        companyTile1 = CompanyTile.builder()
            .currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT)
            .agents(1)
            .build();
        companyTile2 = CompanyTile.builder().
            currentConglomerate(Conglomerate.OMNICORP)
            .agents(1)
            .build();
    }

    @Test
    void givenCorporateLawyerInfiltrate_usedConglomeratesSharesAreRemovedFromHandAndAddedToHeadquartersAndTile(){
        // given
        action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder()
                    .tile(tileReference1)
                    .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
                    .numberOfShares(2)
                    .build(),
                DefaultInfiltrate.builder()
                    .tile(tileReference2)
                    .conglomerateType(Conglomerate.OMNICORP)
                    .numberOfShares(1)
                    .build()
            ))
            .build();

        when(tileReference1.fromMatch(match)).thenReturn(companyTile1);
        when(tileReference2.fromMatch(match)).thenReturn(companyTile2);
        when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
        // when
        when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.CORPORATE_LAWYER);
        action.apply(match, useConsultantRequest);
        // then
        assertEquals(2, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        assertEquals(2, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        assertEquals(3, companyTile1.getAgents());

        assertEquals(1, currentPlayer.getHand().count(Conglomerate.OMNICORP));
        assertEquals(1, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.OMNICORP));
        assertEquals(2, companyTile2.getAgents());
    }

    @Test
    void givenCorporateLawyerInfiltrate_whenTileIsFromSameConglomerate_ExceptionIsTrown() {
        // given
        action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(
                DefaultInfiltrate.builder()
                    .tile(tileReference1)
                    .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
                    .numberOfShares(2)
                    .build(),
                DefaultInfiltrate.builder()
                    .tile(tileReference2)
                    .conglomerateType(Conglomerate.TOTAL_ENTERTAINMENT)
                    .numberOfShares(1)
                    .build()
            ))
            .build();;
        when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.CORPORATE_LAWYER);
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            action.apply(match, useConsultantRequest);
        }, "the conglomerate shares selected have to be of different type");
        assertIfNothingChange();
    }

    @Test
    void givenCorporateLawyerInfiltrate_whenThereAreNot2DifferentBasicInfiltrate_trowsException() {
        // given
        CorporateLawyerInfiltrate infiltrateWith1 = CorporateLawyerInfiltrate.builder()
            .actions(List.of(defaultInfiltrateMock))
            .build();
        CorporateLawyerInfiltrate infiltrateWith3 = CorporateLawyerInfiltrate.builder()
            .actions(List.of(defaultInfiltrateMock, defaultInfiltrateMock, defaultInfiltrateMock))
            .build();
        when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.CORPORATE_LAWYER);
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            infiltrateWith1.apply(match, useConsultantRequest);
        }, "there must be 2 infiltrate actions when using the corporate lawyer consultant");
        assertThrows(IllegalArgumentException.class, () -> {
            infiltrateWith3.apply(match, useConsultantRequest);
        }, "there must be 2 infiltrate actions when using the corporate lawyer consultant");
        assertIfNothingChange();
    }

    @Test
    void givenCorporateLawyerInfiltrate_whenTheConsultantIsntCorporateLawyer_trowsException() {
    	// given
        action = CorporateLawyerInfiltrate.builder()
            .actions(List.of(defaultInfiltrateMock, defaultInfiltrateMock))
                .build();;
        when(useConsultantRequest.getConsultant()).thenReturn(ConsultantType.MEDIA_ADVISOR);
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            action.apply(match, useConsultantRequest);
        }, "the infiltrate must be the same type as the consultant used");
        assertIfNothingChange();
    }

    private void assertIfNothingChange(){
        assertEquals(4, currentPlayer.getHand().count(Conglomerate.TOTAL_ENTERTAINMENT));
        assertEquals(0, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.TOTAL_ENTERTAINMENT));
        assertEquals(1, companyTile1.getAgents());

        assertEquals(2, currentPlayer.getHand().count(Conglomerate.OMNICORP));
        assertEquals(0, currentPlayer.getHeadquarter().getConglomerateShares(Conglomerate.OMNICORP));
        assertEquals(1, companyTile2.getAgents());
    }

}
