package achievements.services;

import achievements.enteties.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import achievements.repos.AchievementRepository;
import achievements.enteties.Achievement;
import achievements.repos.GameRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AchievementServices{
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private GameRepository gameRepository;
//returns Id of created achievement
    public String create(Achievement achievement, String gameId) throws AchievementAlreadyExistsException {
        Game achievementGame = gameRepository.findOne(gameId);
        achievement.setGame(achievementGame);
        achievement.setCreated(LocalDateTime.now());
        achievement.setUpdated(LocalDateTime.now());
        if (!(this.alreadyExists(achievement))) {
            achievement = achievementRepository.save(achievement);
            return achievement.getId();
        }
        else throw new AchievementAlreadyExistsException(achievement);
    }

    public Achievement update (Achievement updated, String updatedId) throws AchievementAlreadyExistsException {
        updated.setUpdated(LocalDateTime.now());
        if (!(this.alreadyExists(updated))) {
            Achievement existsing = achievementRepository.findOne(updatedId);
            updated.setId(updatedId);
            updated.setCreated(existsing.getCreated());
            return achievementRepository.save(updated);
        }
        else throw new AchievementAlreadyExistsException(updated);
    }

    public void delete (String achievementId){
        achievementRepository.delete( achievementId);
    }
//list all achivements for given game ID
    public List<Achievement> getAll (String gameId){
        if(this.gameRepository.exists(gameId)) {
            return this.achievementRepository.findByGame_IdOrderByDisplayOrderAscCreatedAsc(gameId);
        }
        else throw new IllegalGameExeption();
    }

    public Achievement getOne (String achievmentId){
       try{
            return this.achievementRepository.findOne(achievmentId);
        }
        catch (Exception e){
           throw new IllegalAchievemenExeption();
        }
    }
//checks if achievement with same name for the same game already exists
    private boolean alreadyExists(Achievement check) {
        return this.achievementRepository
                .existsByGame_idAndDisplayName(check.getGame().getId(), check.getDisplayName());
    }

}
