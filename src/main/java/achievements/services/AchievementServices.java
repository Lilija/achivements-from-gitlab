package achievements.services;

import achievements.enteties.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
//takes achievement from HTTP body and gameId from path and returns Id of created achievement
    public String create(Achievement achievement, String gameId) throws AchievementAlreadyExistsException, GameNotFoundException {
        Game achievementGame = gameRepository.findById(gameId)
                            .orElseThrow(()->new GameNotFoundException());
        achievement.setGame(achievementGame);
        //This part should go to EventListener?
        achievement.setCreated(LocalDateTime.now());
        achievement.setUpdated(LocalDateTime.now());

        if (!(this.alreadyExists(achievement))) {
            achievement = achievementRepository.save(achievement);
            return achievement.getId();
        }
        else throw new AchievementAlreadyExistsException(achievement);
    }

    public Achievement update (Achievement updAchievement, String updId)
                                            throws AchievementAlreadyExistsException, AchievementNotFoundException {
        //this should go to EventListener?
        updAchievement.setUpdated(LocalDateTime.now());
        //--------------------------------------------
        if (!(this.alreadyExists(updAchievement))) {
            Achievement existsing = achievementRepository.findById(updId)
                    .orElseThrow(()->new AchievementNotFoundException());
            //Because it will probably be changed to use DTOs:
            updAchievement.setId(updId);
            updAchievement.setCreated(existsing.getCreated());
            return achievementRepository.save(updAchievement);
        }
        else throw new AchievementAlreadyExistsException(updAchievement);
    }

    public void delete (String achievementId){
        achievementRepository.delete( achievementId);
    }
//list all achivements for given game ID
    public List<Achievement> getAllByGameSorted(String gameId){
        if(this.gameRepository.exists(gameId)) {
            return this.achievementRepository.findByGame_IdOrderByDisplayOrderAscCreatedAsc(gameId);
        }
        else throw new GameNotFoundException();
    }

    public Achievement getOne (String achievmentId){
        if(this.achievementRepository.exists(achievmentId)) {
            return this.achievementRepository.findOne(achievmentId);
        }
        else throw new AchievementNotFoundException();
    }
//checks if achievement with same name for the same game already exists
    private boolean alreadyExists(Achievement check) {
        return this.achievementRepository
                    .achievementForGameAlreadyExists(check.getId(), check.getGame().getId(), check.getDisplayName())>0;

    }

}
