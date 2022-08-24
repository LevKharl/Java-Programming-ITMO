package Main;

import DragonStuff.Dragon;
import ObjectToSend.ObjectToClient;
import TechStuff.Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static Main.Main.*;

public class Modules implements Serializable {

    static void goConsole() throws IOException, IllegalAccessException {
        Scanner in = new Scanner(System.in);
        String arg = in.nextLine();
        String EXIT = "exit";
        String SAVE = "save";
        if (arg.equals(EXIT)) {
            System.exit(1);
        } else if (arg.equals(SAVE)) {
            collectionSave();
        } else {
            System.out.println("Либо save, либо exit. Другого не дано. ");
        }
    }

    static void connectToClient() throws IOException {
        try {
            serv = new ServerSocket(port);
            sock = serv.accept();
            is = sock.getInputStream();
            os = sock.getOutputStream();
        } catch (IOException e) {
            System.out.println("Проблема в методе Server.connectToClient()");
        }
    }

    static byte[] readObject() throws IOException {
        byte[] commandBytes = new byte[readLengthOfObject(is)];
        is.read(commandBytes);
        return commandBytes;
    }

    private static int readLengthOfObject(InputStream is) throws IOException, NumberFormatException {
        byte[] len = new byte[0];
        try {
            byte[] arrCnt = new byte[1];
            is.read(arrCnt);
            len = new byte[arrCnt[0]];
            is.read(len);
        } catch (IOException e) {
            collectionSave();
            System.exit(0);
            System.out.println(" ");
        } catch (NumberFormatException e) {
            System.out.println(" ");
        }
        return Integer.parseInt(new String(len));
    }

    static void sendMessage(ObjectToClient message) throws IOException {
        try {
            os = sock.getOutputStream();
            byte[] serializableMessage = BytesHandler.serialize(message);
            Modules.sendLengthOfMessage(serializableMessage.length);
            os.write(serializableMessage);
        } catch (IOException e) {
            System.out.println("Проблема в методе Server.sendMessage()");
        }
    }

    private static void sendLengthOfMessage(int lenOfMessage) throws IOException {
        try {
            String lenOfMessageToString = "" + lenOfMessage;
            byte[] lenlen = {(byte) lenOfMessageToString.length()};
            os.write(lenlen);
            byte[] lenOfMessageByteArray = lenOfMessageToString.getBytes(StandardCharsets.UTF_8);
            os.write(lenOfMessageByteArray);
        } catch (IOException e) {
            System.out.println("Проблема в методе Server.sendLenOfMessage()");
        }

    }

    static void collectionSave() throws IOException {
        PrintWriter printWriter = new PrintWriter(Main.database);
        printWriter.write("");
        printWriter.flush();
        printWriter.close();
        if (objects.size() != 0) {
            System.out.println("Начата запись в файл новых объектов коллекции.");
            for (Dragon dragon : objects) {
                System.out.println(dragon.getName());
                Commands.file_writer(dragon);
            }
        } else System.out.println("Коллекция пуста.");
        }
    }

