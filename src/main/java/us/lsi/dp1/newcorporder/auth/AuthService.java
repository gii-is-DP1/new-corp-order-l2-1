package us.lsi.dp1.newcorporder.auth;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void registerUser(@Valid SignupRequest request) {
        User user = new User();

        user.setFirstSeen(LocalDate.now());
        userService.changeUsername(user, request.getUsername());
        userService.changePassword(user, request.getPassword());
        userService.changeEmail(user, request.getEmail());
        userService.changeAuthority(user, request.getAuthority());

        userService.saveUser(user);
    }
}
