package achievements.controllers;

import achievements.Application;
import achievements.enteties.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import achievements.services.AchievementServices;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/game/{gameId}")
@EntityScan(basePackageClasses = {Application.class, Jsr310JpaConverters.class})
public class AchievementController {

    @Autowired
   private  AchievementServices achievementServices;

    //Gets all achivements for given Game Id
    @GetMapping("/achievements" )
    public  List<Achievement> listAllAchievements (@PathVariable String gameId){
        return  achievementServices.getAllByGameSorted(gameId);
    }
//Creates given achievement for given Game Id and returns URL in responce header
    @PostMapping("/achievements")
    public ResponseEntity<?> addAchievement (@RequestBody @Validated Achievement achievement,
                                             @PathVariable String gameId) {
        String createdId;
        createdId = achievementServices.create(achievement, gameId).getId();
            if (createdId != null) {
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{createdId}")
                        .buildAndExpand(createdId).toUri();
                return ResponseEntity.created(location).build();
            }
            return ResponseEntity.noContent().build();
        }

    //Deletes all achivements for given Game Id
    @DeleteMapping("/achievements")
    public ResponseEntity<?> deleteAchievementsByGame (@PathVariable String gameId) {
            achievementServices.deleteAllByGame(gameId);
            return ResponseEntity.ok().build();
    }
    //Gets achivement for given Id
    @GetMapping("/achievements/{achievementId}")
    public  Achievement getAchievement (@PathVariable String achievementId,
                                        @PathVariable String gameId) {
            return achievementServices.getOne(achievementId, gameId);
    }
//Updates achievement with given data returns URL
    @PutMapping("/achievements/{achievementId}")
    public ResponseEntity<?> updateAchievement (@RequestBody @Validated Achievement achievement,
                                                @PathVariable String achievementId,
                                                @PathVariable String gameId) {
            achievement = achievementServices.update(achievement,achievementId, gameId);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{achievementId}")
                        .buildAndExpand(achievement.getId() ).toUri();
                return ResponseEntity.ok().location(location).build();
        }
//Deletes one achievements for given achievement Id
        @DeleteMapping("/achievements/{achievementId}")
        public ResponseEntity<?> deleteAchievement (@PathVariable String achievementId,
                                                    @PathVariable String gameId ) {
        achievementServices.deleteOne(achievementId, gameId);
        return ResponseEntity.ok().build();
    }






}
