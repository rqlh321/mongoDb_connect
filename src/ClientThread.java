import com.google.gson.Gson;
import model.GeneralMessage;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private boolean holdAlive = true;
    private Gson gson = new Gson();

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        super.run();
        try {
            while (holdAlive) {
                String clientMassage = input.readLine();
                if (clientMassage != null) {
                    System.out.println("Client massage: " + clientMassage);
                    GeneralMessage generalMessage = gson.fromJson(clientMassage, GeneralMessage.class);
                    switch (generalMessage.getType()) {
                        case Constant.POST:
                            Message message = gson.fromJson(generalMessage.getData(), Message.class);
                            DaoChat.postMessage(message);
                            Server.clients.forEach(client -> {
                                System.out.println("Broadcasting: " + generalMessage.getData());
                                client.output.println(generalMessage.getData());
                            });
                            break;
                        case Constant.GET:
                            String answer = gson.toJson(DaoChat.getMessages());
                            System.out.println(answer);
                            output.println(answer);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                Server.clients.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
