package us.lsi.dp1.newcorporder.match;

import lombok.Getter;
import us.lsi.dp1.newcorporder.match.company.CompanyMatrix;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Match {

    /**
     * Creates a new match for the given configuration
     *
     * @param maxPlayers the max number of players that the match would hold
     * @param matchMode  the mode of the match
     * @return the new match
     */
    public static Match create(int maxPlayers, MatchMode matchMode) {
        GeneralSupply generalSupply = GeneralSupply.create();
        CompanyMatrix companyMatrix = CompanyMatrix.create();
        String inviteCode = "foo"; // TODO make code random

        return new Match(maxPlayers, matchMode, inviteCode, generalSupply, companyMatrix);
    }

    private final int maxPlayers;
    private final MatchMode matchMode;
    private final String inviteCode;

    private final Map<Integer, MatchPlayer> players = new HashMap<>();

    private final GeneralSupply generalSupply;
    private final CompanyMatrix companyMatrix;

    private Match(int maxPlayers, MatchMode matchMode, String inviteCode, GeneralSupply generalSupply, CompanyMatrix companyMatrix) {
        this.maxPlayers = maxPlayers;
        this.matchMode = matchMode;
        this.inviteCode = inviteCode;
        this.generalSupply = generalSupply;
        this.companyMatrix = companyMatrix;
    }

    public void init() {
        this.generalSupply.init(null, players.size());
        this.companyMatrix.init(players.size() > 2 ? MatchSize.GROUP : MatchSize.COUPLE);
        this.getMatchPlayers().forEach(MatchPlayer::init);
    }

    public MatchPlayer getMatchPlayer(int playerId) {
        return this.players.get(playerId);
    }

    public Collection<MatchPlayer> getMatchPlayers() {
        return this.players.values();
    }
}
