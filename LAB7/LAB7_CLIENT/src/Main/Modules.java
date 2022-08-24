package Main;

import ObjectToSend.ObjectToClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import static Main.Main.*;
import static Users.Users.add_user;
import static Users.Users.check_user;

public class Modules implements Serializable {
    static void connectToServer() {
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

    public static void checkUserAut() throws IOException, ClassNotFoundException {
        Modules.sendObject(check_user());
        String out = readMessage().message;
        System.out.println(out);

        while (out.equals("Неверный пароль. ")) {
            Modules.sendObject(check_user());
            out = readMessage().message;
            System.out.println(out);
        }
        if (out.equals("Пользователь не зарегистрирован. ")) {
            Modules.sendObject(add_user());
            out = readMessage().message;
            System.out.println(out);
        }
        if (!out.equals("Пользователь инициализирован. ")) {
            Modules.sendObject(check_user());
            out = readMessage().message;
            System.out.println(out);
        }
    }

    public static void sendObject(Object command) {
        try {
            byte[] byteArrayObject = BytesHandler.serialize(command);
            Modules.sendLenOfObject(byteArrayObject.length);
            sock.write(ByteBuffer.wrap(byteArrayObject));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в методе Client.sendObject()");
        }
    }

    //отправляем длину десериализованной команды
    private static void sendLenOfObject(int lenArr) {
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
    //читаем сообщение
    public static ObjectToClient readMessage() throws IOException, ClassNotFoundException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(Modules.readLenOfMessage());
        while (byteBuffer.hasRemaining()) {
            sock.read(byteBuffer);
        }
        byte[] b = byteBuffer.array();
        return (ObjectToClient) BytesHandler.deserialize(b);
    }

    //читаем длину сообщения
    private static int readLenOfMessage() throws IOException{
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
}
