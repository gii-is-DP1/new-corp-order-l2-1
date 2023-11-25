package us.lsi.dp1.newcorporder.match.player;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchPlayerTest {

    @Test
    void whenInitializingMatchPlayer_twoRandomDistinctSecretObjectivesAreGiven() {
        MatchPlayer player = new MatchPlayer(1, Headquarter.create());
        player.init(null, List.of());

        assertThat(player.getSecretObjectives())
            .doesNotHaveDuplicates()
            .hasSize(2);
    }
}
