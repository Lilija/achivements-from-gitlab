package achievements.services;

public class IllegalGameExeption extends RuntimeException {
    public IllegalGameExeption() {
        super("Game doesn't exist!");
    }
}