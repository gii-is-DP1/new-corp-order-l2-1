package us.lsi.dp1.newcorporder.achievement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.payload.response.MessageResponse;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievements")
@SecurityRequirement(name="bearerAuth")
@Tag(name = "Achievement", description = "The Achievement API")
public class AchievementController {
    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @Operation(
        summary = "List all achievements",
        description = "List all achievements",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The achievements list"
    )
    @GetMapping
    public ResponseEntity<List<Achievement>> findAll(@RequestParam(required = false) String name) {
        List<Achievement> res = achievementService.getAllFilteredAchievements(name);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(
        summary = "Find an achievement by its id",
        description = "Find an achievement by its id",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The found achievement"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Achievement not found"
    )
    @GetMapping(value = "{id}")
    public ResponseEntity<Achievement> findById(@PathVariable("id") Integer id) {
        Achievement res = achievementService.findById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(
        summary = "Create an achievement",
        description = "Create an achievement",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "The created achievement"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid achievement"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Authorization information is missing or invalid"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Achievement> create(@RequestBody @Valid Achievement achievement) {
        Achievement savedAchievement = achievementService.save(achievement);
        return new ResponseEntity<>(savedAchievement, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an achievement by its id",
        description = "Update an achievement by its id",
        tags = "put"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The updated achievement"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid achievement"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Achievement to update not found"
    )
    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Achievement> update(@PathVariable("id") Integer id, @RequestBody @Valid Achievement achievement) {
        RestPreconditions.checkNotNull(achievementService.findById(id), "Achievement", "ID", id);
        return new ResponseEntity<>(this.achievementService.updateAchievement(achievement, id), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete an achievement by its id",
        description = "Delete an achievement by its id",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Achievement deleted"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Achievement to delete not found"
    )
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") int id) {
        RestPreconditions.checkNotNull(achievementService.findById(id), "Achievement", "ID", id);
        achievementService.deleteAchievement(id);
        return new ResponseEntity<>(new MessageResponse("Achievement deleted"), HttpStatus.OK);
    }
}
