package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchMode;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchStatsService {

    private MatchStats matchStatsList;

    public MatchStatsService() {this.matchStatsList = new MatchStats();}

    public MatchStatsService withMode(MatchMode mode) {matchStatsList.setMode(mode);return this;}

    public MatchStatsService withStartedAt(Instant startedAt) {matchStatsList.setStartedAt(startedAt);return this;}

    public MatchStatsService withEndedAt(Instant endedAt) {matchStatsList.setEndedAt(endedAt);return this;}

    public MatchStatsService withPlayerMatchStats(Set<PlayerMatchStats> playerMatchStats) {matchStatsList.setPlayerMatchStats(playerMatchStats);return this;}

    public MatchStats build() {return matchStatsList;}

    public MatchStats createMatchStats(MatchMode mode, Instant startedAt, Instant endedAt) {
        return new MatchStatsService()
            .withMode(mode)
            .withStartedAt(startedAt)
            .withEndedAt(endedAt)
            .build();
    }

    @Transactional
    public Optional<MatchStats> getMatchStatsById(Integer id) {
        return matchStatsList.stream().filter(matchStats -> matchStats.getId().equals(id)).findFirst();
    }

    public List<MatchStats> getAllMatchStats() {return new ArrayList<>(matchStatsList);}

    public List<MatchStats> getMatchStatsByMode(MatchMode mode) {
        return matchStatsList.stream().filter(stats -> stats.getMode() == mode).collect(Collectors.toList());}

    @Transactional
    public MatchStats updateMatchStats(MatchStats updatedMatchStats) {
        matchStatsList.removeIf(stats -> stats.getId().equals(updatedMatchStats.getId()));
        matchStatsList.add(updatedMatchStats);
        return updatedMatchStats;
    }

    @Transactional
    public void deleteMatchStatsById(Integer id) {
        matchStatsList.removeIf(stats -> stats.getId().equals(updatedMatchStats.getId()));
    }

    public MatchStats createMatchStats(Match match ) {
        MatchMode mode = match.getMatchMode();
        Instant startedAt = null;   // TODO
        Instant endedAt = Instant.now();
        MatchStats res =  new MatchStats(mode, startedAt, endedAt);

        for(MatchPlayer player : match.getPlayers()) {
            res.addPlayerMatchStats(PlayerMatchStatsService.createPlayerMatchStats(match, player));
        }
        return res;
    }
}
