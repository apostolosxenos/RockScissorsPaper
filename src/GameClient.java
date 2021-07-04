import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private GameClient() {
    }

    public void start() {

        try {

            socket = new Socket("127.0.0.1", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Prints Server's greeting at start
            String serverGreeting = in.readLine();
            System.out.println(serverGreeting);

            do {

                // Chooses randomly from available options
                GameOptions clientChoice = randomChoice();
                System.out.println("You chose " + clientChoice);

                // Sends client's choice to server
                out.println(clientChoice.toString());

                // Reads and prints result
                String result = in.readLine();
                System.out.println(result);

                if (!playAgain()) {
                    out.println("exit");
                    break;
                }
                out.println("continue");

            } while (true);

        } catch (Exception e) {
            System.err.println("Server is down. " + e.getMessage());
            System.exit(1);
        } finally {
            System.exit(0);
        }
    }

    private void startClient() {
        this.start();
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient();
        gameClient.startClient();
    }

    private static boolean playAgain() {

        Scanner scanner = new Scanner(System.in);
        int number = 0;

        do {

            System.out.println();
            System.out.println("Press 1 to play again or 2 to exit.");
            number = scanner.nextInt();

        } while (number != 1 && number != 2);

        if (number == 2) {
            return false;
        }

        return true;
    }

    private GameOptions randomChoice() {
        return new GameChoice().randomChoice();
    }
}