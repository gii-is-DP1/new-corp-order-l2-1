package us.lsi.dp1.newcorporder.friendship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

    List<Friendship> findByUser(User user);

    boolean existsByUserAndFriend(User user, User friend);

    Friendship findByUserAndFriend(User user, User friend);

    void removeByUserAndFriend(User user, User friend);

}
