package Main;

import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;
import TechStuff.CommandListener;

import java.io.IOException;
import java.io.Serializable;

import static Main.BytesHandler.deserialize;

public class Server implements Serializable {
    public static void Go (){
        ObjectToClient objectToClient;
        ObjectToServer objectToServer;
        CommandListener commandListener = new CommandListener();

        try {
            Modules.connectToClient();
            while (true) {
                System.out.println("Ожидается запрос от клиента. ");
                byte[] commandBytes = Modules.readObject();
                objectToServer = (ObjectToServer) deserialize(commandBytes);
                if (objectToServer.getName() != null) {
                    System.out.println("Запрос получен.Вызвана команда: " + objectToServer.getName());
                    objectToClient = commandListener.receiveMessage(objectToServer);
                    Modules.sendMessage(objectToClient);
                } else {
                    System.out.println("gblhf");
                }
            }
        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Проблема в методе Server.main()");
        }
    }
}
