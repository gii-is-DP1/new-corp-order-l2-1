package us.lsi.dp1.newcorporder.friendship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.user.User;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

    boolean existsByUserAndFriend(User user, User friend);

    Friendship findByUserAndFriend(User user, User friend);

    void removeByUserAndFriend(User user, User friend);

}
