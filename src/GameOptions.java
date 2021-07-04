public enum GameOptions {

    ROCK(0),
    PAPER(1),
    SCISSORS(2);

    private final int value;

    GameOptions(int value){
        this.value = value;
    }

    public boolean isRock() {
        return this.value == ROCK.value;
    }

    public boolean isPaper() {
        return this.value == PAPER.value;
    }

    public boolean isScissors() {
        return this.value == SCISSORS.value;
    }
}