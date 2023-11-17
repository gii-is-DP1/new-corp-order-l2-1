package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.company.Company;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
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

@MockitoSettings
class MatchTest {

    @Mock GeneralSupply generalSupply;
    @Mock CompanyMatrix companyMatrix;
    @Mock TurnSystem turnSystem;

    @Test
    void givenMatchWith2Players_whenInitializingMatch_generalSupplyIsInitialized() {
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        match.addPlayer(new MatchPlayer(1, Headquarter.create()));
        match.addPlayer(new MatchPlayer(2, Headquarter.create()));
        match.init();

        verify(generalSupply, times(1)).init(MatchMode.NORMAL, 2);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_companyMatrixIsInitializedWithCoupleMatchSize() {
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        match.addPlayer(new MatchPlayer(1, Headquarter.create()));
        match.addPlayer(new MatchPlayer(2, Headquarter.create()));
        match.init();

        verify(companyMatrix, times(1)).init(MatchSize.COUPLE);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void givenMatchWithMoreThan2Players_whenInitializingMatch_companyMatrixIsInitializedWithGroupMatchSize(int players) {
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        for (int i = 0; i < players; i++) {
            match.addPlayer(new MatchPlayer(i, Headquarter.create()));
        }
        match.init();

        verify(companyMatrix, times(1)).init(MatchSize.GROUP);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_playersAreInitialized() {
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        List<Conglomerate> conglomerates = getRandomConglomerates(Match.INITIAL_CONGLOMERATE_SHARES_PER_PLAYER);
        when(generalSupply.takeConglomerateSharesFromDeck(Match.INITIAL_CONGLOMERATE_SHARES_PER_PLAYER))
            .thenReturn(conglomerates);

        match.addPlayer(new MatchPlayer(1, Headquarter.create()));
        match.addPlayer(new MatchPlayer(2, Headquarter.create()));
        match.init();

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
    void whenMatchIsOver_calculatePV() {
        CompanyTile ct1 = CompanyTile.builder().company(Company.READALOT).currentConglomerate(Conglomerate.MEGA_MEDIA).agents(1).build();
        CompanyTile ct2 = CompanyTile.builder().company(Company.WALLPAPER).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(2).build();
        CompanyTile ct3 = CompanyTile.builder().company(Company.HOLOGRAFX).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(3).build();
        CompanyTile ct4 = CompanyTile.builder().company(Company.XCURBR).currentConglomerate(Conglomerate.MEGA_MEDIA).agents(3).build();
        CompanyTile ct5 = CompanyTile.builder().company(Company.GENERTIC_SUB_INC).currentConglomerate(Conglomerate.MEGA_MEDIA).agents(2).build();
        CompanyTile ct6 = CompanyTile.builder().company(Company.CLICKBAITER).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(1).build();
        CompanyTile ct7 = CompanyTile.builder().company(Company.VISUAL_TERROR_INC).currentConglomerate(Conglomerate.TOTAL_ENTERTAINMENT).agents(1).build();
        CompanyTile ct8 = CompanyTile.builder().company(Company.SLIMGROTZ_INC).currentConglomerate(Conglomerate.MEGA_MEDIA).agents(1).build();
        CompanyMatrix companyMatrix = CompanyMatrix.builder()
            .matchSize(MatchSize.GROUP)
            .tiles(Arrays.asList(ct1,ct2,ct3,ct4, ct5, ct6, ct7, ct8))
            .build();
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        MatchPlayer player1 = MatchPlayer.builder()
            .playerId(1)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 6),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 2)))
                .usedConglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 3),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 0)))
                .capturedAgents(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 0),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 0)))
                .build())
            .secretObjectives(Arrays.asList(CompanyType.AMBIENT_ADVERTISING, CompanyType.PRINT_MEDIA))
            .build();
        MatchPlayer player2 = MatchPlayer.builder()
            .playerId(2)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 1),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 6)))
                .usedConglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 3),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 6)))
                .capturedAgents(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 1),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 2)))
                .build())
            .secretObjectives(Arrays.asList(CompanyType.BROADCAST_NETWORK, CompanyType.PRINT_MEDIA))
            .build();

        MatchPlayer player3 = MatchPlayer.builder()
            .playerId(3)
            .headquarter(Headquarter.builder()
                .consultants(HashMultiset.create())
                .conglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 6),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 0)))
                .usedConglomerateShares(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 0),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 8)))
                .capturedAgents(multiset(
                    Multisets.immutableEntry(Conglomerate.MEGA_MEDIA, 0),
                    Multisets.immutableEntry(Conglomerate.TOTAL_ENTERTAINMENT, 3)))
                .build())
            .secretObjectives(Arrays.asList(CompanyType.BROADCAST_NETWORK, CompanyType.PRINT_MEDIA))
            .build();

        match.addPlayer(player1);
        match.addPlayer(player2);
        match.addPlayer(player3);

        Multiset<MatchPlayer> pv = match.calculateVictoryPoints();
        assertThat(pv.count(player1)).isEqualTo(12);
        assertThat(pv.count(player2)).isEqualTo(18);
        assertThat(pv.count(player3)).isEqualTo(4);
    }

    @Test
    void whenMatchIsOver_rankParticipation() {
        Match match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
        Multiset<Conglomerate> conglomerates = HashMultiset.create();
        conglomerates.addAll(Arrays.asList(Conglomerate.MEGA_MEDIA, Conglomerate.MEGA_MEDIA, Conglomerate.MEGA_MEDIA));
        Multiset<ConsultantType> consultantsEmpty = HashMultiset.create();
        Headquarter.HeadquarterBuilder builder = Headquarter.builder();
        builder.consultants(consultantsEmpty);
        builder.conglomerateShares(createMegaMedia(6));
        builder.usedConglomerateShares(createMegaMedia(3));
        builder.capturedAgents(createMegaMedia(0));
        Headquarter headquarter1 = builder
            .build();
        Headquarter headquarter2 = Headquarter.builder()
            .consultants(consultantsEmpty)
            .conglomerateShares(createMegaMedia(1))
            .usedConglomerateShares(createMegaMedia(3))
            .capturedAgents(createMegaMedia(1))
            .build();
        Headquarter headquarter3 = Headquarter.builder()
            .consultants(consultantsEmpty)
            .conglomerateShares(createMegaMedia(6))
            .usedConglomerateShares(createMegaMedia(0))
            .capturedAgents(createMegaMedia(0))
            .build();
        MatchPlayer matchPlayer1 = MatchPlayer.builder()
            .playerId(1)
            .headquarter(headquarter1)
            .secretObjectives(null)
            .build();
        MatchPlayer matchPlayer2 = MatchPlayer.builder()
            .playerId(2)
            .headquarter(headquarter2)
            .secretObjectives(null)
            .build();
        MatchPlayer matchPlayer3 = MatchPlayer.builder()
            .playerId(3)
            .headquarter(headquarter3)
            .secretObjectives(null)
            .build();

        match.addPlayer(matchPlayer1);
        match.addPlayer(matchPlayer2);
        match.addPlayer(matchPlayer3);

        List<MatchPlayer> ranking = match.rankPlayerParticipation(Conglomerate.MEGA_MEDIA);
        assertThat(ranking.get(0)).isEqualTo(matchPlayer1);
        assertThat(ranking.get(1)).isEqualTo(matchPlayer2);
        assertThat(ranking.get(2)).isEqualTo(matchPlayer3);
    }

    Multiset<Conglomerate> createMegaMedia(int i) {
        Multiset<Conglomerate> conglomerates = HashMultiset.create();
        for (int j = 0; j < i; j++) {
            conglomerates.add(Conglomerate.MEGA_MEDIA);
        }
        return conglomerates;
    }

    @SafeVarargs
    final <E> Multiset<E> multiset(Multiset.Entry<E>... entries) {
        Multiset<E> multiset = HashMultiset.create();
        for (Multiset.Entry<E> entry : entries) {
            multiset.add(entry.getElement(), entry.getCount());
        }
        return multiset;
    }
}
