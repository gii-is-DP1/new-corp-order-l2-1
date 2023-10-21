package us.lsi.dp1.newcorporder.achievement;

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
@RequestMapping("/api/v1/achievements")
@SecurityRequirement(name="bearerAuth")
public class AchievementController {
    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public ResponseEntity<List<Achievement>> findAll() {
        List<Achievement> res = (List<Achievement>) achievementService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Achievement> findById(@PathVariable("id") Integer id) {
        Achievement res = achievementService.findById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Achievement> create(@RequestBody @Valid Achievement achievement) {
        Achievement savedAchievement = achievementService.save(achievement);
        return new ResponseEntity<>(savedAchievement, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Achievement> update(@PathVariable("id") Integer id, @RequestBody @Valid Achievement achievement) {
        RestPreconditions.checkNotNull(achievementService.findById(id), "Achievement", "ID", id);
        return new ResponseEntity<>(this.achievementService.updateAchievement(achievement, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") int id) {
        RestPreconditions.checkNotNull(achievementService.findById(id), "Achievement", "ID", id);
        achievementService.deleteAchievement(id);
        return new ResponseEntity<>(new MessageResponse("Achievement deleted"), HttpStatus.OK);
    }
}
