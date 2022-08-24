package Main;

import DragonStuff.Dragon;
import TechStuff.CommandListener;
import TechStuff.Commands;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;




public class Main {
    public static List<Integer> all_id = new ArrayList<>();
    public static ArrayList<Dragon> objects = new ArrayList<>();
    public static java.time.ZonedDateTime coll_cr_date = ZonedDateTime.now();
    public static String database = "";

    public static void main(String[] args) {
        all_id.clear();
        Commands commands = new Commands();
        if (args[0] != ""){
            database = args[0];
        }
        else {
            System.out.println("вы не ввели файл");
        }
        try {
            commands.file_reader(database);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Проблемы с чтением объекта: " + e.getMessage());
        }
        CommandListener commandListener = new CommandListener();
        while (true) {
            commandListener.check_message(commandListener.receive_message());
        }
    }
}


