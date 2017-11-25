package achievements.services;

import achievements.enteties.Achievement;

public class AchievementAlreadyExistsException extends Throwable {
    public AchievementAlreadyExistsException(Achievement o) {
        super ("Achivement "+o.getDisplayName()+" for this game already exists");
    }
}
