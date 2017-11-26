package achievements.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AchievementNotFoundException extends RuntimeException {
    public AchievementNotFoundException() {
        super("Achievement doesn't exist!");
    }
}
