package us.lsi.dp1.newcorporder.match.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.MatchState;
import us.lsi.dp1.newcorporder.match.company.CompanyTile;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.List;

@Getter
@Builder
public class MatchView {

    public static MatchView of(Match match) {
        return MatchView.of(match, null);
    }

    public static MatchView of(Match match, MatchPlayer player) {
        return MatchView.builder()
            .mode(match.getMode())
            .maxPlayers(match.getMaxPlayers())
            .host(getHost(match))
            .state(match.getState())
            .player(player != null ? OwnPlayerView.of(player) : null)
            .opponents(buildOpponents(match, player))
            .companyMatrix(match.getCompanyMatrix().getTiles())
            .generalSupply(GeneralSupplyView.of(match.getGeneralSupply()))
            .turn(TurnView.of(match))
            .build();
    }

    private static Integer getHost(Match match) {
        return match.getHost() != null ? match.getHost().getPlayerId() : null;
    }

    private static List<OpponentView> buildOpponents(Match match, MatchPlayer player) {
        return match.getTurnSystem().getPlayers().stream()
            .filter(opponent -> !opponent.equals(player))
            .map(OpponentView::of)
            .toList();
    }

    private final MatchMode mode;
    private final int maxPlayers;
    private final Integer host;
    private final MatchState state;

    private final OwnPlayerView player;
    private final List<OpponentView> opponents;
    private final CompanyTile[] companyMatrix;
    private final GeneralSupplyView generalSupply;
    private final TurnView turn;

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public Integer getHost() {
        return host;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public OwnPlayerView getPlayer() {
        return player;
    }
}
