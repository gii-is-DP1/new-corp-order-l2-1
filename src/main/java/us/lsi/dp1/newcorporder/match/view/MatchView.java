package us.lsi.dp1.newcorporder.match.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.match.chat.Message;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchView {

    public static MatchView of(Match match) {
        return MatchView.of(match, null);
    }

    public static MatchView of(Match match, MatchPlayer player) {
        MatchView.MatchViewBuilder builder = MatchView.builder()
            .mode(match.getMode())
            .maxPlayers(match.getMaxPlayers())
            .host(match.getHost() != null ? match.getHost().getPlayerId() : null)
            .state(match.getState())
            .player(player != null ? OwnPlayerView.of(player) : null)
            .opponents(buildOpponents(match, player))
            .isSpectating(!match.getPlayers().contains(player))
            .messages(match.getChat().getMessages());
        if(match.getState() == MatchState.PLAYING)
            builder
            .companyMatrix(match.getCompanyMatrix().getTiles())
            .generalSupply(GeneralSupplyView.of(match.getGeneralSupply()))
            .turn(TurnView.of(match.getTurnSystem()))
            .isPlaying(match.getTurnSystem().getCurrentPlayer() == player);
        if(match.getState() == MatchState.FINISHED)
            builder
                .isWinner(match.getWinners().contains(player));
        return builder.build();
    }

    private static List<OpponentView> buildOpponents(Match match, MatchPlayer player) {
        return match.getPlayers().stream()
            .filter(opponent -> !opponent.equals(player))
            .map(opponent -> OpponentView.of(opponent, match.getState()))
            .toList();
    }

    private final boolean isSpectating;
    private final boolean isPlaying;
    private final MatchMode mode;
    private final int maxPlayers;
    private final Integer host;
    private final MatchState state;

    private final OwnPlayerView player;
    private final List<OpponentView> opponents;
    private final CompanyTile[] companyMatrix;
    private final GeneralSupplyView generalSupply;
    private final TurnView turn;

    private final Boolean isWinner;

    private final List<Message> messages;

    @JsonInclude
    public Integer getHost() {
        return host;
    }
}
