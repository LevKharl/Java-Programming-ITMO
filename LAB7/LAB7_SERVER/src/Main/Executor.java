package Main;

import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;
import ObjectToSend.CommandListener;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Main.BytesHandler.deserialize;
import static Users.Users.checkuser;

public class Executor extends Thread implements Serializable {
    private final Socket client;
    public final byte[] commandBytes;
    private final ExecutorService executorFork = Executors.newCachedThreadPool();
    ObjectToClient objectToClient;
    ObjectToServer objectToServer;
    static CommandListener listener = new CommandListener();

    public Executor(byte[] commandBytes, Socket client) {
        this.commandBytes = commandBytes;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            objectToServer = (ObjectToServer) deserialize(this.commandBytes);
            if (objectToServer.getName() != null) {
                if ((!objectToServer.getName().equals("user"))||(!objectToServer.getName().equals("register"))){
                    System.out.print(objectToServer.getUsername()+" ");
                    System.out.println(objectToServer.getName());
                    objectToClient = listener.receiveMessage(objectToServer);
                } else if (checkuser(objectToServer)){
                    objectToClient = listener.receiveMessage(objectToServer);
                    System.out.println("Пользователь " + (objectToServer.getObject()) + " подключился");
                }
            }
            executorFork.execute(new Sendler(objectToClient, client.getOutputStream()));
            executorFork.shutdown();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
