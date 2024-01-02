package us.lsi.dp1.newcorporder.notification;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Query("SELECT notification FROM Notification notification " +
           "WHERE notification.user = :user AND notification.state = :state " +
           "ORDER BY notification.sentAt DESC")
    List<Notification> findByUserAndState(User user, NotificacionState state);

    @Query("SELECT notification FROM Notification notification " +
           "WHERE notification.user = :user AND notification.state != 2 " +
           "ORDER BY notification.sentAt DESC")
    List<Notification> findNonDismissedByUser(User user);

}
