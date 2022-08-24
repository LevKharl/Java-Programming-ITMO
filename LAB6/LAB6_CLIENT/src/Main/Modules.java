package Main;

import ObjectToSend.ObjectToClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import static Main.Main.*;

public class Modules implements Serializable {
    static void connectToServer() throws IOException {
        try {
            address = new InetSocketAddress("localhost", port);
            Modules.waitForConnection();
            sock.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Проблема в методе Client.connectToServer()");
        }
    }

    private static void waitForConnection() {
        try {
            sock = SocketChannel.open();
            sock.connect(address);
            System.out.println("Сервер успешно подключен.");
        } catch (IOException e) {
            try {
                Thread.sleep(3000);
                System.out.println("Ожидается подключение сервера.");
                Modules.waitForConnection();
            } catch (InterruptedException a) {
                System.out.println("Проблема в методе Client.waitForConnection()");
            }
        }

    }

    static ObjectToClient readMessage() throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Modules.readLengthOfMessage());
        while (byteBuffer.hasRemaining()) {
            sock.read(byteBuffer);
        }
        byte[] b = byteBuffer.array();
        return (ObjectToClient) BytesHandler.deserialize(b);
    }

    private static int readLengthOfMessage() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[1]);
        while (byteBuffer.hasRemaining()) {
            sock.read(byteBuffer);
        }
        int len = byteBuffer.array()[0];
        ByteBuffer lenMessage = ByteBuffer.allocate(len);
        while (lenMessage.hasRemaining()) {
            sock.read(lenMessage);
        }
        return Integer.parseInt(new String(lenMessage.array()));

    }

    public static void sendObject(Object command) throws IOException {
        try {
            byte[] byteArrayObject = BytesHandler.serialize(command);
            Modules.sendLengthOfObject(byteArrayObject.length);
            ByteBuffer bb = ByteBuffer.wrap(byteArrayObject);
            sock.write(bb);
            bb.clear();
        } catch (IOException e) {
            System.out.println("Проблема в методе Client.sendObject()");
        }
    }

    private static void sendLengthOfObject(int lenArr) {
        try {
            String lenArrStr = "" + lenArr;
            byte[] arr = lenArrStr.getBytes(StandardCharsets.UTF_8);
            byte[] cnt = {(byte) arr.length};
            sock.write(ByteBuffer.wrap(cnt));
            sock.write(ByteBuffer.wrap(arr));
        } catch (IOException e) {
            System.out.println("Проблема в методе Client.sendLenOfObject()");
        }
    }
}
