package achievements.services;

import achievements.enteties.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import achievements.repos.AchievementRepository;
import achievements.enteties.Achievement;
import achievements.repos.GameRepository;

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
        if (!(this.alreadyExists(achievement))) {
            achievement = achievementRepository.save(achievement);
            return achievement.getId();
        }
        else throw new AchievementAlreadyExistsException(achievement);
    }

    public Achievement update (Achievement updatedAchievement, String updId)
                                            throws AchievementAlreadyExistsException, AchievementNotFoundException {

        Achievement existing = achievementRepository.findById(updId)
                .orElseThrow(()->new AchievementNotFoundException());
        //Ovo prebaciti u DTO:
        updatedAchievement.setGame(existing.getGame());
        updatedAchievement.setId(updId);
        updatedAchievement.setCreated(existing.getCreated());
        //
        if (!(this.alreadyExists(updatedAchievement))) {
            return achievementRepository.save(updatedAchievement);
        }
        else throw new AchievementAlreadyExistsException(updatedAchievement);
    }

    public void delete (String achievementId){
        achievementRepository.delete( achievementId);
    }

    public List<Achievement> getAllByGameSorted(String gameId){
        if(this.gameRepository.exists(gameId)) {
            return this.achievementRepository.findByGame_IdOrderByDisplayOrderAscCreatedAsc(gameId);
        }
        else throw new GameNotFoundException();
    }

    public Achievement getOne (String achievmentId){
        return achievementRepository.findById(achievmentId)
                .orElseThrow(()->new AchievementNotFoundException());

    }

//checks if achievement with same name for the same game already exists
    private boolean alreadyExists(Achievement check) {
        return this.achievementRepository
                    .achievementForGameAlreadyExists(check.getId(), check.getGame().getId(), check.getDisplayName())>0;

    }

}
