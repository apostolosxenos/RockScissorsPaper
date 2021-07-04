import java.util.Random;

public class GameChoice {

    public GameOptions randomChoice() {
        return GameOptions.values()[new Random().nextInt(GameOptions.values().length)];
    }
}
