package achievements.controllers;

import achievements.Application;
import achievements.enteties.Achievement;
import achievements.enteties.Game;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import achievements.services.AchievementAlreadyExistsException;
import achievements.services.AchievementServices;

import java.util.List;

@Controller
@RequestMapping("/game/{gameId}")
@EntityScan(basePackageClasses = {Application.class, Jsr310JpaConverters.class})
public class AchievementController {

    @Autowired
    AchievementServices achievementServices;

@GetMapping("/achievements")
    public @ResponseBody List<Achievement> listAllAchievements (@PathVariable String gameId){
          return achievementServices.getAll(gameId);
    }

    @PostMapping("/achievements")
    public void add (@RequestBody Achievement achievement, @PathVariable String gameId) {
        try {

            achievementServices.create(achievement, gameId);
        }
        catch ( AchievementAlreadyExistsException e){

        }
        }

    @GetMapping("/achievements/{achievementId}")
    public @ResponseBody Achievement getAchievement (@PathVariable String achievementId) {
            return achievementServices.getOne(achievementId);
    }

    @PutMapping("/achievements/{achievementId}")
    public void  updateAchievement (@RequestBody Achievement achievement, @PathVariable String achievementId) {
        try {
             achievementServices.update(achievement,achievementId);
        } catch (AchievementAlreadyExistsException e) {
            e.getMessage();

        }
    }
        @DeleteMapping("/achievements/{achievementId}")
        public void deleteAchievement (@PathVariable String achievementId) {
        achievementServices.delete(achievementId);
    }



}
