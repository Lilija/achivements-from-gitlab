package achievements.services;

import achievements.enteties.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import achievements.repos.AchievementRepository;
import achievements.enteties.Achievement;
import achievements.repos.GameRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AchievementServices{
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private GameRepository gameRepository;

//takes achievement from HTTP body and gameId from path and returns Id of created achievement
    public Achievement create(Achievement achievement, String gameId) throws AchievementAlreadyExistsException, GameNotFoundException {
        Game achievementGame = gameRepository.findById(gameId)
                            .orElseThrow(()->new GameNotFoundException(gameId));
        achievement.setGame(achievementGame);
        if (!(this.alreadyExists(achievement))) {
            achievement = achievementRepository.saveAndFlush(achievement);
            return achievement;
        }
        else throw new AchievementAlreadyExistsException(achievement);
    }

    public Achievement update(Achievement updatedAchievement, String updId, String gameId)
                                            throws AchievementAlreadyExistsException,
                                                    AchievementNotFoundException {

        Achievement existing = achievementRepository.findByIdAndGame_Id(updId,gameId)
                .orElseThrow(()->new AchievementNotFoundException(updId, gameId));
        //Ovo prebaciti u DTO:
        //sve sto nije poslato za update takodje treba da ostane iz existing
            updatedAchievement.setGame(existing.getGame());
            updatedAchievement.setId(updId);
            updatedAchievement.setCreated(existing.getCreated());
        //
        if (!(this.alreadyExists(updatedAchievement))) {
            return achievementRepository.saveAndFlush(updatedAchievement);
        }
        else throw new AchievementAlreadyExistsException(updatedAchievement);
    }

    public void deleteOne(String achievementId, String gameId) throws AchievementNotFoundException {
        if (achievementRepository.existsByIdAndGame_id(achievementId, gameId)) {
            achievementRepository.delete(achievementId);
        }
        else throw new AchievementNotFoundException(achievementId,gameId );
    }
@Transactional
    public void deleteAllByGame (String gameId){
        if (gameRepository.exists(gameId)) {
            achievementRepository.deleteAllByGameId(gameId);
        }
        else throw new GameNotFoundException(gameId);
    }


    public List<Achievement> getAllByGameSorted(String gameId){
        if(this.gameRepository.exists(gameId)) {
            return this.achievementRepository.findByGame_IdOrderByDisplayOrderAscCreatedAsc(gameId);
        }
        else throw new GameNotFoundException(gameId);
    }

    public Achievement getOne (String achievementId, String gameId){
        return achievementRepository.findByIdAndGame_Id(achievementId, gameId)
                .orElseThrow(()->new AchievementNotFoundException(achievementId, gameId));

    }

//checks if achievement with same name for the same game already exists
    private boolean alreadyExists(Achievement check) {
        return this.achievementRepository
                    .achievementForGameAlreadyExists(check.getId(), check.getGame().getId(), check.getDisplayName())
                    .isPresent();
    }

}
