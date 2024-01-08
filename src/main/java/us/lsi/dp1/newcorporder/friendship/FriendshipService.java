package us.lsi.dp1.newcorporder.friendship;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.friendship.payload.FriendshipView;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

import java.time.Instant;
import java.util.List;

@Service
public class FriendshipService {

    @Value("${newcorporder.app.maxFriendshipRequests}")
    private int maxFriendshipRequests;

    private final FriendshipRepository friendshipRepository;
    private final FriendshipRequestRepository friendshipRequestRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipRequestRepository friendshipRequestRepository) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipRequestRepository = friendshipRequestRepository;
    }

    public List<FriendshipView> getFriendships(User user) {
        return friendshipRepository.findByUser(user).stream()
            .map(FriendshipView::of)
            .toList();
    }

    public List<UserView> getFriends(User user) {
        return friendshipRepository.findByUser(user).stream()
            .map(friendship -> UserView.reduced(friendship.getFriend()))
            .toList();
    }

    public boolean areFriends(User user1, User user2) {
        return friendshipRepository.existsByUserAndFriend(user1, user2);
    }

    public Friendship getFriendship(User user1, User user2) {
        return friendshipRepository.findByUserAndFriend(user1, user2);
    }

    @Transactional
    public void requestFriendship(User sender, User receiver) {
        Preconditions.checkState(!sender.equals(receiver),
            "cannot send a friend request to yourself");
        Preconditions.checkState(!friendshipRepository.existsByUserAndFriend(sender, receiver),
            "%s is already your friend", receiver.getUsername());
        Preconditions.checkState(!friendshipRequestRepository.existsBySenderAndReceiver(sender, receiver),
            "you already sent a friend request to %s", receiver.getUsername());
        Preconditions.checkState(!friendshipRequestRepository.existsBySenderAndReceiver(receiver, sender),
            "%s already send you a friend request", receiver.getUsername());

        Preconditions.checkState(friendshipRequestRepository.countByReceiver(receiver) < 10,
            "%s has more than %s requests in queue. Try later.",
            receiver.getUsername(), maxFriendshipRequests);

        FriendshipRequest request = FriendshipRequest.builder()
            .sentAt(Instant.now())
            .sender(sender)
            .receiver(receiver)
            .build();

        friendshipRequestRepository.save(request);
    }

    @Transactional
    public void acceptFriendship(User user, User sender) {
        FriendshipRequest request = friendshipRequestRepository.findBySenderAndReceiver(sender, user);
        Preconditions.checkState(request != null, "%s did not send you any friend request", sender.getUsername());

        this.addFriend(user, sender);
        friendshipRequestRepository.delete(request);
    }

    @Transactional
    public void denyFriendship(User user, User sender) {
        FriendshipRequest request = friendshipRequestRepository.findBySenderAndReceiver(sender, user);
        Preconditions.checkState(request != null, "%s did not send you any friend request", sender.getUsername());

        friendshipRequestRepository.delete(request);
    }

    @Transactional
    public void removeFriend(User user1, User user2) {
        Preconditions.checkState(friendshipRepository.existsByUserAndFriend(user1, user2), "%s is not your friend", user2.getUsername());

        friendshipRepository.removeByUserAndFriend(user1, user2);
        friendshipRepository.removeByUserAndFriend(user2, user1);
    }

    @Transactional
    void addFriend(User user1, User user2) {
        Preconditions.checkState(!friendshipRepository.existsByUserAndFriend(user1, user2), "%s is already your friend", user2.getUsername());

        Instant now = Instant.now();
        friendshipRepository.save(Friendship.builder()
            .since(now)
            .user(user1)
            .friend(user2)
            .build());

        friendshipRepository.save(Friendship.builder()
            .since(now)
            .user(user2)
            .friend(user1)
            .build());
    }
}
