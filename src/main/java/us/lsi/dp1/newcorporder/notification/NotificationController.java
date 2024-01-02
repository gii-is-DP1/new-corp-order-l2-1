package us.lsi.dp1.newcorporder.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.user.UserService;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Operation(
        summary = "Get your notifications",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Your notifications"
    )
    @GetMapping
    public List<Notification> getNotifications() {
        return notificationService.getNonDismissedNotifications(userService.findCurrentUser());
    }

    @Operation(
        summary = "Dismiss the given notification",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The notification was dismissed successfully"
    )
    @DeleteMapping("/{id}")
    public void getNotifications(@PathVariable Integer id) {
        Notification notification = notificationService.getNotification(id);
        RestPreconditions.checkAccess(notification.getUser().equals(userService.findCurrentUser()));

        notificationService.dismiss(notification);
    }
}
