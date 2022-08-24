package Main;

import ObjectToSend.ObjectToClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Sendler extends Thread implements Serializable {
    private final ObjectToClient objectToClient;
    private final OutputStream os;

    public Sendler(ObjectToClient objectToClient, OutputStream os) {
        this.objectToClient = objectToClient;
        this.os = os;
    }

    @Override
    public void run() {
        try {
            sendMessage(objectToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(ObjectToClient message) throws IOException {
        try {
            byte[] serializableMessage = BytesHandler.serialize(message);
            this.sendLenOfMessage(serializableMessage.length);
            os.write(serializableMessage);
        } catch (IOException e) {
            System.out.println("Проблема в методе Server.sendMessage()");
        }
    }

    private void sendLenOfMessage(int lenOfMessage) throws IOException {
        try {
            String lenOfMessageToString = "" + lenOfMessage;
            byte[] lenlen = {(byte) lenOfMessageToString.length()};
            os.write(lenlen);
            byte[] lenOfMessageByteArray = lenOfMessageToString.getBytes(StandardCharsets.UTF_8);
            os.write(lenOfMessageByteArray);
        } catch (IOException e) {
            System.out.println("Client disconnects.");
        }
    }
}
