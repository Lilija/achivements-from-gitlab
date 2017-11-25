package achievements.services;

public class IllegalAchievmentIdExeption extends RuntimeException {
    public IllegalAchievmentIdExeption() {
        super("Achievement doesn't exist!");
    }
}
