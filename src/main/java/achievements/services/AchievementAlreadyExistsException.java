package achievements.services;

import achievements.enteties.Achievement;

public class AchievementAlreadyExistsException extends Throwable {
    public AchievementAlreadyExistsException(Achievement o) {
        super ("Achivement with this name for this game already exists");
    }
}
