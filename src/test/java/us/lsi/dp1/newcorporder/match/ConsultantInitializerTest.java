package us.lsi.dp1.newcorporder.match;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ConsultantInitializerTest {

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void givenMoreThan2Players_availableConsultantsContainsAllConsultants(int players) {
        ConsultantInitializer consultantInitializer = new ConsultantInitializer(players);

        assertThat(consultantInitializer.getAvailableConsultants()).containsExactlyInAnyOrder(ConsultantType.values());
    }

    @Test
    void given2Players_availableConsultantsDoesNotContainCorporateLawyer() {
        ConsultantInitializer consultantInitializer = new ConsultantInitializer(2);

        assertThat(consultantInitializer.getAvailableConsultants())
            .hasSize(ConsultantType.values().length - 1)
            .doesNotContain(ConsultantType.CORPORATE_LAWYER);
    }
}
