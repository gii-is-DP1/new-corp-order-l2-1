package us.lsi.dp1.newcorporder.match;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.friendship.FriendshipService;
import us.lsi.dp1.newcorporder.match.payload.response.MatchAssignmentResponse;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.match.view.MatchView;
import us.lsi.dp1.newcorporder.notification.InviteNotification;
import us.lsi.dp1.newcorporder.notification.Notification;
import us.lsi.dp1.newcorporder.notification.NotificationService;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.time.Instant;
import java.util.Optional;

@Service
public class MatchService {

    // TODO: synchronize
    private final FriendshipService friendshipService;
    private final NotificationService notificationService;
    private final MatchRepository matchRepository;

    public MatchService(FriendshipService friendshipService, NotificationService notificationService, MatchRepository matchRepository) {
        this.friendshipService = friendshipService;
        this.notificationService = notificationService;
        this.matchRepository = matchRepository;
    }

    public MatchAssignmentResponse quickPlay(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.findRandomPublicMatch(player, mode, maxPlayers)
            .orElseGet(() -> matchRepository.createNewMatch(mode, MatchVisibility.PUBLIC, maxPlayers));

        return this.join(player, match);
    }

    public MatchAssignmentResponse createPrivateMatch(Player player, MatchMode mode, int maxPlayers) {
        Match match = matchRepository.createNewMatch(mode, MatchVisibility.PRIVATE, maxPlayers);

        return this.join(player, match);
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
        return matchPlayer != null ? MatchView.of(match, matchPlayer) : MatchView.of(match);
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
        RestPreconditions.checkAccess(match.isHost(player));
        match.start();
    }

    public Optional<Match> findByCode(String code) {
        return matchRepository.getByMatchCode(code);
    }
}
