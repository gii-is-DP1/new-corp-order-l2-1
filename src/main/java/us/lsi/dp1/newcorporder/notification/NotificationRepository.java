package us.lsi.dp1.newcorporder.notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    List<Notification> findByUserOrderBySentAtAsc(User user);

}
