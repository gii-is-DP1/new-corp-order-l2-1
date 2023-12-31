package us.lsi.dp1.newcorporder.auth;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.time.Instant;

@Service
public class AuthService {

    private final PasswordEncoder encoder;
    private final AuthorityService authorityService;
    private final UserService userService;

    public AuthService(PasswordEncoder encoder, AuthorityService authorityService, UserService userService) {
        this.encoder = encoder;
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @Transactional
    public void createUser(@Valid SignupRequest request) {
        Authority authority = authorityService.findByName("USER");

        User user = User.builder()
            .username(request.getUsername())
            .password(encoder.encode(request.getPassword()))
            .email(request.getEmail())
            .firstSeen(Instant.now())
            .lastSeen(Instant.now())
            .authority(authority)
            .build();

        userService.saveUser(user);
    }
}
