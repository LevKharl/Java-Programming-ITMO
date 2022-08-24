package Main;

import DragonStuff.Dragon;
import TechStuff.Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main implements Serializable {
    static Socket sock;
    static ServerSocket serv;
    static OutputStream os;
    static InputStream is;
    static int port =1337;

    public static List<Integer> all_id = new ArrayList<>();
    public static ArrayList<Dragon> objects = new ArrayList<>();
    public static String database = "";

    public static void main(String[] args) throws IllegalAccessException {
        all_id.clear();
        if (!Objects.equals(args[0], "")) {
            database = args[0];
        } else System.out.println("Файл не указан.");
        try {
            Commands.file_reader(database);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Проблемы с чтением объекта: " + e.getMessage());
        }

        Thread main = new Thread(() -> {
            while (true) {
                Server.Go();
            }
        });

        Thread console = new Thread(() -> {
            while (true) {
                try {
                    Modules.goConsole();
                } catch (IOException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        main.start();
        console.start();
    }
}
