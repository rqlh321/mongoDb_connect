import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public static ArrayList<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException {
        MongoDb.getInstance();
        Server server = new Server();
        server.runServer();
    }

    private void runServer() throws IOException, SQLException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server Started...");
        boolean run = true;

        while (run) {
            System.out.println("Waiting connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Server get connection...");
            ClientThread client = new ClientThread(socket);
            clients.add(client);
            client.start();
        }
        serverSocket.close();
    }
}
