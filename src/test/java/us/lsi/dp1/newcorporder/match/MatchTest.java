package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.HashMultiset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.turn.TurnSystem;
import us.lsi.dp1.newcorporder.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
class MatchTest {

    @Mock GeneralSupply generalSupply;
    @Mock CompanyMatrix companyMatrix;
    @Mock TurnSystem turnSystem;

    Match match;

    @BeforeEach
    void setUp() {
        match = new Match(4, MatchMode.NORMAL, null, generalSupply, companyMatrix, turnSystem);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_generalSupplyIsInitialized() {
        match.addPlayer(new MatchPlayer(1, Headquarter.create()));
        match.addPlayer(new MatchPlayer(2, Headquarter.create()));
        match.init();

        verify(generalSupply, times(1)).init(MatchMode.NORMAL, 2);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_companyMatrixIsInitializedWithCoupleMatchSize() {
        match.addPlayer(new MatchPlayer(1, Headquarter.create()));
        match.addPlayer(new MatchPlayer(2, Headquarter.create()));
        match.init();

        verify(companyMatrix, times(1)).init(MatchSize.COUPLE);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void givenMatchWithMoreThan2Players_whenInitializingMatch_companyMatrixIsInitializedWithGroupMatchSize(int players) {
        for (int i = 0; i < players; i++) {
            match.addPlayer(new MatchPlayer(i, Headquarter.create()));
        }
        match.init();

        verify(companyMatrix, times(1)).init(MatchSize.GROUP);
    }

    @Test
    void givenMatchWith2Players_whenInitializingMatch_playersAreInitialized() {
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
}
