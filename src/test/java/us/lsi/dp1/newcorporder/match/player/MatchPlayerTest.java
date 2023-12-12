package us.lsi.dp1.newcorporder.match.player;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static us.lsi.dp1.newcorporder.match.player.MatchPlayerTestUtils.playerWithId;

public class MatchPlayerTest {

    @Test
    void whenInitializingMatchPlayer_twoRandomDistinctSecretObjectivesAreGiven() {
        MatchPlayer player = playerWithId(1);
        player.init(null, List.of());

        assertThat(player.getSecretObjectives())
            .doesNotHaveDuplicates()
            .hasSize(2);
    }
}
