package us.lsi.dp1.newcorporder.notification;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification getNotification(Integer id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
    }

    public List<Notification> getNotifications(User user, NotificacionState state) {
        return notificationRepository.findByUserAndState(user, state);
    }

    public List<Notification> getNonDismissedNotifications(User user) {
        return notificationRepository.findNonDismissedByUser(user);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void dismiss(Notification notification) {
        Preconditions.checkState(notification.getState() != NotificacionState.DISMISSED,
            "The notification is already dismissed.");

        notification.setState(NotificacionState.DISMISSED);
        notificationRepository.save(notification);
    }
}
