package us.lsi.dp1.newcorporder.match;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.achievement.Achievement;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MatchService {

    List<Match> matchList = new ArrayList<>();

    @Transactional(readOnly = true)
    public Iterable<Match> findAll() {
        return matchList;
    }

    @Transactional(readOnly = true)
    public Match findByInviteCode(String inviteCode) {
        Optional<Match> match = matchList.stream().filter(m -> m.getInviteCode().equals(inviteCode)).findFirst();
        if(match.isEmpty()) throw new ResourceNotFoundException("Match", "invite code", inviteCode);
        return match.get();
    }

    @Transactional(readOnly = true)
    public Match findRandomMatch() {
        Optional<Match> match = matchList.stream()
            .filter(m -> m.getMatchState().equals(MatchState.WAITING))
            .filter(m -> m.getPlayers().size() < m.getMaxPlayers())
            .findFirst();
        if(match.isEmpty()) throw new NoSuchElementException("No matches left");
        return match.get();
    }

    @Transactional(readOnly = true)
    public Match findRandomMatch(MatchMode matchMode, int maxPlayers) {
        Optional<Match> match = matchList.stream().filter(m -> m.getMatchState().equals(MatchState.WAITING))
            .filter(m -> m.getPlayers().size() < m.getMaxPlayers())
            .filter(m -> m.getMaxPlayers() == maxPlayers)
            .filter(m -> m.getMatchMode().equals(matchMode))
            .findFirst();
        if(match.isEmpty()) throw new NoSuchElementException("No matches left");
        return match.get();
    }
}
