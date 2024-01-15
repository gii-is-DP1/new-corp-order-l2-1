package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.friendship.FriendshipService;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.match.payload.response.MatchResponse;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.view.MatchView;
import us.lsi.dp1.newcorporder.notification.InviteNotification;
import us.lsi.dp1.newcorporder.notification.Notification;
import us.lsi.dp1.newcorporder.notification.NotificationService;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.player.PlayerService;
import us.lsi.dp1.newcorporder.stats.MatchStatsService;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.util.OffsetPageable;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static us.lsi.dp1.newcorporder.configuration.SecurityConfiguration.ADMIN;

@Service
public class MatchService {

    // TODO: synchronize
    private final PlayerService playerService;
    private final FriendshipService friendshipService;
    private final NotificationService notificationService;
    private final MatchStatsService matchStatsService;
    private final MatchRepository matchRepository;

    public MatchService(PlayerService playerService, FriendshipService friendshipService, NotificationService notificationService,
                        MatchStatsService matchStatsService, MatchRepository matchRepository) {
        this.playerService = playerService;
        this.friendshipService = friendshipService;
        this.notificationService = notificationService;
        this.matchStatsService = matchStatsService;
        this.matchRepository = matchRepository;
    }

    public Optional<Match> getMatch(String code) {
        return matchRepository.getByMatchCode(code);
    }
    public List<MatchResponse> getMatches(Pageable pageable) {
        List<MatchResponse> playing = this.getPlayingMatches(pageable);
        int statsPageSize = pageable.getPageSize() - playing.size();

        // add finished matches from db after the matches in play
        if (statsPageSize > 0) {
            long statsOffset = pageable.getOffset() + playing.size() - matchRepository.getMatches().size();
            Pageable statsPageable = OffsetPageable.of(statsOffset, statsPageSize);
            matchStatsService.findLast(statsPageable).stream()
                .map(MatchResponse::of)
                .forEach(playing::add);
        }

        return playing;
    }

    private List<MatchResponse> getPlayingMatches(Pageable pageable) {
        if (pageable.getOffset() >= matchRepository.getMatches().size()) {
            return new ArrayList<>();
        }

        return matchRepository.getMatches().stream()
            .sorted(Comparator.comparing(Match::getCreationTime))
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .map(MatchResponse::of)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public MatchAssignmentResponse quickPlay(Player player, MatchMode mode, int maxPlayers) {
        Optional<Match> match = matchRepository.findRandomPublicMatch(player, mode, maxPlayers);
        System.out.println(match.isPresent() ? match.get().getCode() : "");
        if (match.isEmpty()) return createNewMatch(player, mode, maxPlayers, MatchVisibility.PUBLIC);
        else
            return this.join(player, match.get());
    }

    public MatchAssignmentResponse createPrivateMatch(Player player, MatchMode mode, int maxPlayers) {
        return createNewMatch(player, mode, maxPlayers, MatchVisibility.PRIVATE);
    }

    public MatchAssignmentResponse createNewMatch(Player host, MatchMode mode, int maxPlayers, MatchVisibility matchVisibility) {
        Match match = matchRepository.createNewMatch(mode, matchVisibility, maxPlayers);
        MatchAssignmentResponse mr = join(host, match);
        match.setHost(match.getPlayer(host.getId()));
        return mr;

    }

    public void inviteFriend(User user, User friend, Match match) {
        Preconditions.checkState(friendshipService.areFriends(user, friend),
            "%s is not your friend.", friend.getUsername());

        Notification notification = InviteNotification.builder()
            .user(friend)
            .sentAt(Instant.now())
            .sender(user)
            .matchCode(match.getCode())
            .build();
        notificationService.save(notification);
    }

    public MatchView getMatchView(Player player, Match match) {
        MatchPlayer matchPlayer = match.getPlayer(player.getId());
        RestPreconditions.checkAccess(matchPlayer != null || canViewMatch(player, match),
            "Cannot spectate match: you are not friends with every player");

        return matchPlayer != null ? MatchView.of(match, matchPlayer) : MatchView.of(match);
    }

    private boolean canViewMatch(Player viewer, Match match) {
        if (viewer.getUser().hasAuthority(ADMIN)) {
            return true;
        }

        return match.getPlayers().stream()
            .map(matchPlayer -> playerService.findById(matchPlayer.getPlayerId()).getUser())
            .allMatch(user -> friendshipService.areFriends(viewer.getUser(), user));
    }

    public MatchAssignmentResponse join(Player player, Match match) {
        if (match.getPlayer(player.getId()) == null) {
            match.addPlayer(MatchPlayer.create(player));
        }

        return new MatchAssignmentResponse(match.getCode());
    }

    public void leave(Player player, Match match) {
        match.removePlayer(match.getPlayer(player.getId()));
    }

    public void forceStart(Player player, Match match) {
        Preconditions.checkState(match.isHost(player), "You are not the match host");
        match.start();
    }

    public Optional<Match> findByCode(String code) {
        return matchRepository.getByMatchCode(code);
    }
}
