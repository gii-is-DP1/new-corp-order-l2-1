package us.lsi.dp1.newcorporder.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.lsi.dp1.newcorporder.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final UserService userService;
    private final NotificationRepository notificationRepository;

    public NotificationController(UserService userService, NotificationRepository notificationRepository) {
        this.userService = userService;
        this.notificationRepository = notificationRepository;
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
        return notificationRepository.findByUserOrderBySentAtAsc(userService.findCurrentUser());
    }
}
