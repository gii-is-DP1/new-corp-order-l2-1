package us.lsi.dp1.newcorporder.match;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.achievement.Achievement;
import us.lsi.dp1.newcorporder.achievement.AchievementService;
import us.lsi.dp1.newcorporder.auth.payload.response.MessageResponse;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@SecurityRequirement(name="bearerAuth")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> findAll() {
        List<Match> res = (List<Match>) matchService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "{inviteCode}")
    public ResponseEntity<Match> findByInviteCode(@PathVariable("inviteCode") String inviteCode) {
        Match res = matchService.findByInviteCode(inviteCode);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/random")
    public ResponseEntity<Match> findRandomMatch() {
        Match res = matchService.findRandomMatch();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/random/{matchMode}/{maxPlayers}")
    public ResponseEntity<Match> findRandomMatch(@PathVariable @Valid MatchMode matchMode, @PathVariable Integer maxPlayers) {
        Match res = matchService.findRandomMatch(matchMode, maxPlayers);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
