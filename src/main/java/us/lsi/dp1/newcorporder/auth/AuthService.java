package us.lsi.dp1.newcorporder.auth;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.player.PlayerService;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.time.Instant;
import java.util.Random;

@Service
public class AuthService {

    private final UserService userService;
    private final PlayerService playerService;
    private final AuthorityService authorityService;

    public AuthService(UserService userService, PlayerService playerService, AuthorityService authorityService) {
        this.userService = userService;
        this.playerService = playerService;
        this.authorityService = authorityService;
    }

    @Transactional
    public void registerUser(@Valid SignupRequest request) {
        User user = User.builder()
            .firstSeen(Instant.now())
            .lastSeen(Instant.now())
            .authority(authorityService.findByName("USER"))
            .build();

        userService.changeUsername(user, request.getUsername());
        userService.changePassword(user, request.getPassword());
        userService.changeEmail(user, request.getEmail());
        Random random = new Random();
        userService.changePropic(user, random.nextInt(22)+1);
        userService.saveUser(user);
        playerService.save(Player.builder().user(user).build());
    }
}
