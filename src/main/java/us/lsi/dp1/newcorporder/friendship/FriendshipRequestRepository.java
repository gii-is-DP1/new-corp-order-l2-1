package us.lsi.dp1.newcorporder.friendship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.user.User;

@Repository
public interface FriendshipRequestRepository extends CrudRepository<FriendshipRequest, Integer> {

    FriendshipRequest findBySenderAndReceiver(User sender, User receiver);

    boolean existsBySenderAndReceiver(User sender, User receiver);

    int countByReceiver(User receiver);

}
