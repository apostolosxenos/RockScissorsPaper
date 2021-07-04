public class GameReferee {

    public static GameResult getResult(GameOptions serverChoice, GameOptions clientChoice) {
        if (serverChoice == clientChoice)
            return GameResult.DRAW;
        return isClientWinner(serverChoice, clientChoice) ? GameResult.CLIENT : GameResult.SERVER;
    }

    private static boolean isClientWinner(GameOptions serverChoice, GameOptions clientChoice) {
        return (clientChoice.isRock() && serverChoice.isScissors() ||
                clientChoice.isPaper() && serverChoice.isRock() ||
                clientChoice.isScissors() && serverChoice.isPaper());
    }
}
