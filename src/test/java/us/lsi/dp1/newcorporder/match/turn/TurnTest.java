package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.collect.HashMultiset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.player.Headquarter;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.lenient;

@MockitoSettings
class TurnTest {

    @Mock TurnSystem turnSystem;
    @Mock GeneralSupply generalSupply;

    MatchPlayer currentPlayer = new MatchPlayer(1, Headquarter.create());
    Match match;
    Turn turn;

    @BeforeEach
    void setUp() {
        match = Match.builder()
            .maxPlayers(4)
            .matchMode(MatchMode.NORMAL)
            .generalSupply(generalSupply)
            .turnSystem(turnSystem)
            .build();
        turn = new Turn(match) {};

        lenient().when(turnSystem.getCurrentPlayer()).thenReturn(currentPlayer);
    }

    @Test
    void givenLessNumberOfSharesToDiscardThanNeeded_whenRequestingToDiscardShares_throwsException() {
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND + 1);

        assertThatThrownBy(() -> turn.onDiscardShareRequest(new DiscardShareRequest(HashMultiset.create())))
            .hasMessageContaining("you have to discard the necessary number of shares");
    }

    @Test
    void givenMoreNumberOfSharesToDiscardThanNeeded_whenRequestingToDiscardShares_throwsException() {
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND + 1);

        HashMultiset<Conglomerate> shares = HashMultiset.create(List.of(Conglomerate.GENERIC_INC, Conglomerate.GENERIC_INC));
        assertThatThrownBy(() -> turn.onDiscardShareRequest(new DiscardShareRequest(shares)))
            .hasMessageContaining("you have to discard the necessary number of shares");
    }

    @Test
    void givenInvalidSharesToDiscard_whenRequestingToDiscardShares_throwsException() {
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND + 1);

        HashMultiset<Conglomerate> shares = HashMultiset.create(List.of(Conglomerate.OMNICORP));
        assertThatThrownBy(() -> turn.onDiscardShareRequest(new DiscardShareRequest(shares)))
            .hasMessageContaining("you don't have enough shares of the given conglomerate to discard");
    }

    @Test
    void givenInvalidSharesToDiscard_whenRequestingToDiscardShares_discardsTheGivenSharesFromPlayersHand() {
        currentPlayer.addSharesToHand(Conglomerate.GENERIC_INC, Match.MAX_SHARES_IN_HAND + 1);

        HashMultiset<Conglomerate> shares = HashMultiset.create(List.of(Conglomerate.GENERIC_INC));
        DiscardShareResponse response = turn.onDiscardShareRequest(new DiscardShareRequest(shares));

        assertThat(currentPlayer.getHand())
            .hasSize(Match.MAX_SHARES_IN_HAND)
            .containsOnly(Conglomerate.GENERIC_INC);
    }
}
