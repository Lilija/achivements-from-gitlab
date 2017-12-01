package achievements.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AchievementNotFoundException extends RuntimeException {
    public AchievementNotFoundException(String achievementId, String gameId) {
        super("Achievement Id " +achievementId + " for game Id "+gameId+" doesn't exist!");
    }
}
