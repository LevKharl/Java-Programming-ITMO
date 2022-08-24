package Main;

import ObjectToSend.ObjectToServer;
import ObjectToSend.CommandListener;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static Main.Modules.*;

public class Main implements Serializable {
    static int port = 6789;
    static SocketAddress address;
    static SocketChannel sock;
    public static String USERNAME;
    public static String PASSWORD;
    static CommandListener listener = new CommandListener();
    static ObjectToServer objectToServer;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            Modules.connectToServer();
            Modules.checkUserAut();
            while (true) {
                objectToServer = listener.receiveMessage();
                objectToServer.setUsername(USERNAME);
                objectToServer.setPassword(PASSWORD);
                Modules.sendObject(objectToServer);
                System.out.println("Ответ с сервера получен.\n" + readMessage().message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
