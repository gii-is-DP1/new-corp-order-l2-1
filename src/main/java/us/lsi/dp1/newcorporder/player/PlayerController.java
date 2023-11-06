package us.lsi.dp1.newcorporder.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/players/")
public class PlayerController {

    private final PlayerService playerService;
    private final UserService userService;

    private final String PLAYERS_LISTING = "/players/PlayersListing";


    public PlayerController(PlayerService playerService, UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }


    @GetMapping("/{page}")
    public String getAllPlayers(@PathVariable("page") Integer page, Map<String, Object> model, Principal principal) {
        User user = this.userService.findUser(principal.getName()).get();

        Pageable paging = PageRequest.of(page, 5);

        Page<Player> playerPage = playerService.getAllPlayers(paging);
        List<Player> players = playerPage.getContent();
        model.put("hasPrevious", playerPage.hasPrevious());
        model.put("hasNext", playerPage.hasNext());
        model.put("page", page);
        model.put("principalName", principal.getName());

        model.put("players", players);
        return PLAYERS_LISTING;
    }
}
