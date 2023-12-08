package us.lsi.dp1.newcorporder.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
class PlayerController {

    private final PlayerService playerService;


    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(
        summary = "List all players",
        description = "List all players",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The players list"
    )
    @GetMapping
    public ResponseEntity<List<Player>> findAll() {
        List<Player> res = (List<Player>) playerService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(
        summary = "Find a player by its id",
        description = "Find a player by its id",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The found player"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Player not found"
    )
    @GetMapping(value = "{id}")
    public ResponseEntity<Player> findById(@PathVariable("id") Integer id) {
        Player res = playerService.findById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(
        summary = "Create a player",
        description = "Create a player",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The created player"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid player"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Authorization information is missing or invalid"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> create(@RequestBody @Valid Player player) {
        Player savedPlayer = playerService.save(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update a player by its id",
        description = "Update a player by its id",
        tags = "put"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The updated player"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid player"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Player to update not found"
    )
    @PutMapping(value = "{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> update(@PathVariable("playerId") Integer id, @RequestBody @Valid Player player) {
        RestPreconditions.checkNotNull(playerService.findById(id), "Player", "ID", id);
        return new ResponseEntity<>(this.playerService.updatePlayer(player, id), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete a player by its id",
        description = "Delete a player by its id",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Player deleted"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Player to delete not found"
    )
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") int id) {
        RestPreconditions.checkNotNull(playerService.findById(id), "Player", "ID", id);
        playerService.deletePlayer(id);
        return new ResponseEntity<>(new MessageResponse("Player deleted"), HttpStatus.OK);
    }
}
