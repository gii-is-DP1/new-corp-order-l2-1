package us.lsi.dp1.newcorporder.player;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.payload.response.MessageResponse;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@SecurityRequirement(name = "bearerAuth")
class PlayerRestController {

    private final PlayerService playerService;


    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> findAll() {
        List<Player> res = (List<Player>) playerService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Player> findById(@PathVariable("id") Integer id) {
        Player res = playerService.findById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> create(@RequestBody @Valid Player player) {
        Player savedPlayer = playerService.save(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @PutMapping(value = "{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> update(@PathVariable("playerId") Integer id, @RequestBody @Valid Player player) {
        RestPreconditions.checkNotNull(playerService.findById(id), "Player", "ID", id);
        return new ResponseEntity<>(this.playerService.updatePlayer(player, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") int id) {
        RestPreconditions.checkNotNull(playerService.findById(id), "Player", "ID", id);
        playerService.deletePlayer(id);
        return new ResponseEntity<>(new MessageResponse("Player deleted"), HttpStatus.OK);
    }
}
