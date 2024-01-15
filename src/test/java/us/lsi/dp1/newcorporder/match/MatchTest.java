package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.company.Company;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;
import us.lsi.dp1.newcorporder.util.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

@MockitoSettings
class MatchTest {

    @Mock GeneralSupply generalSupply;
    @Mock CompanyMatrix companyMatrix;
    @Mock TurnSystem turnSystem;

    @Test
    void givenMatchWith2Players_whenInitializingMatch_generalSupplyIsInitialized() {
        Match match = new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null);
        match.addPlayer(playerWithId(1));
        match.addPlayer(playerWithId(2));
        match.start();

        verify(generalSupply, times(1)).init(MatchMode.NORMAL, 2);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_companyMatrixIsInitializedWithCoupleMatchSize() {
        Match match = new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null);
        match.addPlayer(playerWithId(1));
        match.addPlayer(playerWithId(2));
        match.start();

        verify(companyMatrix, times(1)).init(MatchSize.COUPLE);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void givenMatchWithMoreThan2Players_whenInitializingMatch_companyMatrixIsInitializedWithGroupMatchSize(int players) {
        Match match = new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null);
        for (int i = 0; i < players; i++) {
            match.addPlayer(playerWithId(i));
        }
        match.start();

        verify(companyMatrix, times(1)).init(MatchSize.GROUP);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_playersAreInitialized() {
        Match match = new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null);
        when(turnSystem.getPlayers()).thenCallRealMethod();

        List<Conglomerate> conglomerates = getRandomConglomerates(Match.INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
        when(generalSupply.takeConglomerateSharesFromDeck(Match.INITIAL_CONGLOMERATE_SHARES_PER_PLAYER))
            .thenReturn(conglomerates);

        match.addPlayer(playerWithId(1));
        match.addPlayer(playerWithId(2));
        match.start();

        assertThat(match.getPlayers()).map(player -> player.getHeadquarter().getConsultants())
            .doesNotHaveDuplicates()
            .hasSize(2);

        assertThat(match.getPlayers()).map(MatchPlayer::getHand)
            .allSatisfy(shares -> assertThat(shares).isEqualTo(HashMultiset.create(conglomerates)));
    }

    List<Conglomerate> getRandomConglomerates(int amount) {
        List<Conglomerate> conglomerates = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            conglomerates.add(RandomUtils.getRandomElement(Conglomerate.values(), random));
        }
        return conglomerates;
    }

    @Test
    void whenRankingParticipation_calculationsAreDoneCorrectly() {
        Match match = new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null);

        MatchPlayer matchPlayer1 = MatchPlayer.builder()
            .playerId(1)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(createMegaMedia(6))
                .usedConglomerateShares(createMegaMedia(3))
                .capturedAgents(createMegaMedia(0))
                .build())
            .secretObjectives(null)
            .build();
        MatchPlayer matchPlayer2 = MatchPlayer.builder()
            .playerId(2)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(createMegaMedia(1))
                .usedConglomerateShares(createMegaMedia(3))
                .capturedAgents(createMegaMedia(1))
                .build())
            .secretObjectives(null)
            .build();
        MatchPlayer matchPlayer3 = MatchPlayer.builder()
            .playerId(3)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(createMegaMedia(6))
                .usedConglomerateShares(createMegaMedia(0))
                .capturedAgents(createMegaMedia(0))
                .build())
            .secretObjectives(null)
            .build();

        match.addPlayer(matchPlayer1);
        match.addPlayer(matchPlayer2);
        match.addPlayer(matchPlayer3);

        List<MatchPlayer> ranking = match.rankPlayerParticipation(Conglomerate.MEGAMEDIA);
        assertThat(ranking.get(0)).isEqualTo(matchPlayer1);
        assertThat(ranking.get(1)).isEqualTo(matchPlayer2);
        assertThat(ranking.get(2)).isEqualTo(matchPlayer3);
    }

    @Test
    void whenCalculatingVictoryPoints_calculationIsDoneCorrectly() {
        CompanyMatrix companyMatrix = CompanyMatrix.builder()
            .matchSize(MatchSize.GROUP)
            .tiles(CompanyTile.builder().company(Company.READALOT).currentConglomerate(Conglomerate.MEGAMEDIA).agents(1).build(),
                CompanyTile.builder().company(Company.WALLPAPER).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(2).build(),
                CompanyTile.builder().company(Company.HOLOGRAFX).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(3).build(),
                CompanyTile.builder().company(Company.XCURBR).currentConglomerate(Conglomerate.MEGAMEDIA).agents(3).build(),
                CompanyTile.builder().company(Company.GENERIC_SUB_INC).currentConglomerate(Conglomerate.MEGAMEDIA).agents(2).build(),
                CompanyTile.builder().company(Company.CLICKBAITER).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(1).build(),
                CompanyTile.builder().company(Company.VISUAL_TERROR_INC).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(1).build(),
                CompanyTile.builder().company(Company.SLIMGROTZ_INC).currentConglomerate(Conglomerate.MEGAMEDIA).agents(1).build())
            .build();

        Match match = spy(new Match(4, MatchMode.NORMAL, MatchVisibility.PRIVATE, null, generalSupply, companyMatrix, turnSystem, null));

        MatchPlayer player1 = MatchPlayer.builder()
            .playerId(1)
            .headquarter(Headquarter.create())
            .secretObjectives(Arrays.asList(CompanyType.AMBIENT_ADVERTISING, CompanyType.PRINT_MEDIA))
            .build();
        MatchPlayer player2 = MatchPlayer.builder()
            .playerId(2)
            .headquarter(Headquarter.create())
            .secretObjectives(Arrays.asList(CompanyType.BROADCAST_NETWORK, CompanyType.PRINT_MEDIA))
            .build();
        MatchPlayer player3 = MatchPlayer.builder()
            .playerId(3)
            .headquarter(Headquarter.create())
            .secretObjectives(Arrays.asList(CompanyType.BROADCAST_NETWORK, CompanyType.PRINT_MEDIA))
            .build();

        when(match.rankPlayerParticipation(Conglomerate.MEGAMEDIA)).thenReturn(List.of(player1, player2, player3));
        when(match.rankPlayerParticipation(Conglomerate.TOTAL_ENTERTAINMENT)).thenReturn(List.of(player2, player3, player1));
        when(match.rankPlayerParticipation(Conglomerate.OMNICORP)).thenReturn(List.of(player1, player2, player3));
        when(match.rankPlayerParticipation(Conglomerate.GENERIC_INC)).thenReturn(List.of(player1, player2, player3));

        match.addPlayer(player1);
        match.addPlayer(player2);
        match.addPlayer(player3);

        Multiset<MatchPlayer> pv = match.calculateVictoryPoints();
        assertThat(pv.count(player1)).isEqualTo(12);
        assertThat(pv.count(player2)).isEqualTo(18);
        assertThat(pv.count(player3)).isEqualTo(4);
    }

    Multiset<Conglomerate> createMegaMedia(int i) {
        Multiset<Conglomerate> conglomerates = HashMultiset.create();
        conglomerates.add(Conglomerate.MEGAMEDIA, i);
        return conglomerates;
    }
}
