package achievements.controllers;

import achievements.Application;
import achievements.enteties.Achievement;
import achievements.services.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import achievements.services.AchievementAlreadyExistsException;
import achievements.services.AchievementServices;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/game/{gameId}")
@EntityScan(basePackageClasses = {Application.class, Jsr310JpaConverters.class})
public class AchievementController {

    @Autowired
   private  AchievementServices achievementServices;

@GetMapping("/achievements")
    public @ResponseBody List<Achievement> listAllAchievements (@PathVariable String gameId){
          return achievementServices.getAllByGameSorted(gameId);
    }

    @PostMapping("/achievements")
    public ResponseEntity<?> add (@RequestBody Achievement achievement, @PathVariable String gameId) {
        String createdId;
        createdId = achievementServices.create(achievement, gameId);
            if (createdId != null) {
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{createdId}")
                        .buildAndExpand(createdId).toUri();
                return ResponseEntity.created(location).build();
            }
            return ResponseEntity.noContent().build();
        }

    @GetMapping("/achievements/{achievementId}")
    public @ResponseBody Achievement getAchievement (@PathVariable String achievementId) {
            return achievementServices.getOne(achievementId);
    }

    @PutMapping("/achievements/{achievementId}")
    public ResponseEntity<?> updateAchievement (@RequestBody Achievement achievement,
                                                @PathVariable String achievementId) {

            achievement = achievementServices.update(achievement,achievementId);
            if (achievement.getId() != null) {
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{createdId}")
                        .buildAndExpand(achievement.getId() ).toUri();
                return ResponseEntity.created(location).build();
            }
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/achievements/{achievementId}")
        public ResponseEntity deleteAchievement (@PathVariable String achievementId) {
        achievementServices.delete(achievementId);
            return ResponseEntity.ok("Deleted");
    }






}
