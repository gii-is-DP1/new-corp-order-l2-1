package us.lsi.dp1.newcorporder.match;

import com.google.common.collect.Multiset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneralSupplyTest {

    GeneralSupply generalSupply;

    @BeforeEach
    void setUp() {
        generalSupply = GeneralSupply.create();
    }

    @Test
    void given2Players_whenInitializingGeneralSupply_thereOneConsultantOfEachTypeWithoutCorporateLawyer() {
        generalSupply.init(MatchMode.NORMAL, 2);

        assertThat(generalSupply.getConsultantsLeft().elementSet())
            .hasSize(ConsultantType.values().length - 1)
            .doesNotContain(ConsultantType.CORPORATE_LAWYER);

        assertThat(generalSupply.getConsultantsLeft()).hasSize(ConsultantType.values().length - 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void givenMoreThan2Players_whenInitializingGeneralSupply_thereAreOneLessConsultantOfEachTypeThanNumberOfPlayers(int players) {
        generalSupply.init(MatchMode.NORMAL, players);

        assertThat(generalSupply.getConsultantsLeft().elementSet()).containsExactlyInAnyOrder(ConsultantType.values());
        assertThat(generalSupply.getConsultantsLeft().entrySet()).map(Multiset.Entry::getCount)
            .allSatisfy(count -> assertThat(count).isEqualTo(players - 1));
    }
}
