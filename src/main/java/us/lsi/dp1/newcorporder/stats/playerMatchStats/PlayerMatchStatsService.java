package us.lsi.dp1.newcorporder.stats.playerMatchStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.stats.MatchResult;
import us.lsi.dp1.newcorporder.stats.companyPlayerMatchStats.CompanyPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.conglomeratePlayerMatchStats.ConglomeratePlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.consultantPlayerMatchStats.ConsultantPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStats;
import us.lsi.dp1.newcorporder.stats.matchStats.MatchStatsRepository;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.playerMatchStats.PlayerMatchStatsRepository;

import java.util.*;

@Service
public class PlayerMatchStatsService {


    private static PlayerMatchStatsRepository playerMatchStatsRepository;
    private final MatchStatsRepository matchStatsRepository;


    public PlayerMatchStatsService(MatchStatsRepository matchStatsRepository, PlayerMatchStatsRepository playerMatchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
        this.playerMatchStatsRepository=playerMatchStatsRepository;
    }

    public Set<PlayerMatchStats> getPlayerMatchStats(Integer matchId) {
        return playerMatchStatsRepository.findByMatchId(matchId);}

    public Optional<MatchStats> getMatchStatsById(Integer id) {
        return matchStatsRepository.findById(id);}








}
