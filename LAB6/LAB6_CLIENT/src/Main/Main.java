package Main;

import TechStuff.CommandListener;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static Main.Modules.*;

public class Main implements Serializable {
    static int port = 1337;
    static SocketAddress address;
    static SocketChannel sock;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CommandListener commandListener = new CommandListener();
        try {
            Modules.connectToServer();
            while (true) {
                commandListener.receiveMessage();
                System.out.println("Ответ с сервера получен.\n" + readMessage().message);
            }

        } catch (Exception e) {
            System.out.println("Сервер временно недоступен. Попробуйте позже");;
            Modules.connectToServer();
            while (true) {
                commandListener.receiveMessage();
                System.out.println("Ответ с сервера получен.\n" + readMessage().message);
            }
        }
    }
}
