import java.io.IOException;
import java.net.ServerSocket;

public class GameServer extends Thread {

    private ServerSocket serverSocket;
    private int port;

    public GameServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {

            // Infinite loop. Accepting client connections.
            while (true) {
                new Thread(new GameClientHandler(serverSocket.accept())).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    private void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        if (args.length > 0)
            Integer.parseInt(args[0]);
        GameServer server = new GameServer(port);
        server.startServer();
        System.out.println("Server started on port: " + port);
    }
}