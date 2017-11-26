package achievements.services;

import achievements.enteties.Achievement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AchievementAlreadyExistsException extends RuntimeException {
    public AchievementAlreadyExistsException(Achievement o) {
        super ("Achivement "+o.getDisplayName()+" for "+o.getGame().getDisplayName()+" already exists");
    }
}
