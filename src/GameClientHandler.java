import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClientHandler implements Runnable {

    private final Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private int clientId = 0;
    private static int numConnections;

    public GameClientHandler(Socket client) {
        clientId = ++numConnections;
        this.client = client;
    }


    @Override
    public void run() {

        System.out.println(String.format("Client #%d is connected.", clientId));

        try {

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Greeting
            out.println("Welcome to Rock-Paper-Scissors game! You play first.");

            do {

                // Reads client's choice
                String clientInput = in.readLine();
                System.out.println(String.format("Client #%d choice: %s", clientId, clientInput));

                // Convert client's choice (String) to an available option (GameOptions)
                GameOptions clientChoice = GameOptions.valueOf(clientInput);
                // Server chooses randomly from available options
                GameOptions serverChoice = randomChoice();

                // Retrieves result by comparing both choices
                GameResult result = GameReferee.getResult(serverChoice, clientChoice);
                // Announces result to client
                announceResult(out, serverChoice, result);

                // Read client's input to play again or exit
                clientInput = in.readLine();

                if (clientInput.equalsIgnoreCase("exit")) {
                    System.out.println(String.format("Client exited."));
                    break;
                }
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSocket();
        }
    }

    private void announceResult(PrintWriter out, GameOptions serverChoice, GameResult result) {

        String message = result.equals(GameResult.DRAW) ?
                String.format("Server chose %s. *** Result is DRAW", serverChoice) :
                String.format("Server chose %s. *** Winner is %s", serverChoice, result);

        out.println(message);
    }

    private GameOptions randomChoice() {
        return new GameChoice().randomChoice();
    }

    private void closeSocket() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (client != null) client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
