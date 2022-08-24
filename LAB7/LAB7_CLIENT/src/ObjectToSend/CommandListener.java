package ObjectToSend;

import DragonStuff.DragonCollection;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Main.Main.USERNAME;

public class CommandListener implements Serializable {
    public static List<String> list = new LinkedList<>();
    public boolean comparison(String message, Pattern pattern) {
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }
    ObjectToServer objectToServer = new ObjectToServer();

    public ObjectToServer receiveMessage() throws SQLException {
        DragonCollection collection = new DragonCollection();

        System.out.println("Введите команду: ");
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine().toLowerCase();

        Pattern HELP = Pattern.compile("\\s*help\\s*");
        Pattern INFO = Pattern.compile("\\s*info\\s*");
        Pattern SHOW = Pattern.compile("\\s*show\\s*");
        Pattern ADD = Pattern.compile("\\s*add\\s*");
        Pattern UPDATE = Pattern.compile("\\s*update_id\\d*");
        Pattern REMOVE_BY_ID = Pattern.compile("\\s*remove_by_id\\d*");
        Pattern CLEAR = Pattern.compile("\\s*clear\\s*");
        Pattern EXIT = Pattern.compile("\\s*exit\\s*");
        Pattern REMOVE_GREATER = Pattern.compile("\\s*remove_greater\\d*");
        Pattern SHUFFLE = Pattern.compile("\\s*shuffle\\s*");
        Pattern FILTER_CONTAINS_NAME = Pattern.compile("\\s*filter_contains_name\\s*");
        Pattern COUNT_LESS_THAN_DESCRIPTION = Pattern.compile("\\s*count_less_than_description\\s*");
        Pattern REMOVE_ALL_BY_AGE= Pattern.compile("\\s*remove_all_by_age\\s*");
        Pattern SORT = Pattern.compile("\\s*sort\\s*");

        if (comparison(message, HELP)) {
            objectToServer = collection.help();
            list.add("help");
        }
        else if (comparison(message, INFO)) {
            objectToServer = collection.info();
            list.add("info");
        }
        else if (comparison(message, SHOW)) {
            objectToServer = collection.show();
            list.add("show");
        }
        else if (comparison(message, ADD)) {
            objectToServer = collection.add();
            list.add("add");
        }
        else if (comparison(message.split(" ")[0], UPDATE)) {
            String[] symbols = message.split(" ");
            Integer id = Integer.parseInt(symbols[1]);
            objectToServer = collection.update_id(id);
            list.add("update_id");
        }
        else if (comparison(message.split(" ")[0], REMOVE_BY_ID)) {
            String[] symbols = message.split(" ");
            Integer id = Integer.parseInt(symbols[1]);
            objectToServer = collection.remove_by_id(id);
            list.add("remove_by_id");
        }
        else if (comparison(message, CLEAR)) {
            objectToServer = collection.clear();
            list.add("clear");
        }

        else if (comparison(message, EXIT)) {
            collection.exit();
            list.add("exit");
        }
        else if (comparison(message, SHUFFLE)) {
            objectToServer = collection.shuffle();
            list.add("shuffle");
        }
        else if (comparison(message.split(" ")[0], REMOVE_GREATER)) {
            String[] symbols = message.split(" ");
            int age = Integer.parseInt(symbols[1]);
            objectToServer = collection.remove_greater(age);
            list.add("remove_greater");
        }
        else if (comparison(message.split(" ")[0], FILTER_CONTAINS_NAME)) {
            String[] symbols = message.split(" ");
            String name = symbols[1];
            objectToServer = collection.filter_contains_name(name);
            list.add("filter_contains_name");
        }
        else if (comparison(message.split(" ")[0], COUNT_LESS_THAN_DESCRIPTION)) {
            String[] symbols = message.split(" ");
            String description = symbols[1];
            objectToServer = collection.count_less_than_description(description);
            list.add("count_less_than_description");
        }
        else if (comparison(message.split(" ")[0], REMOVE_ALL_BY_AGE)) {
            String[] symbols = message.split(" ");
            int age = Integer.parseInt(symbols[1]);
            objectToServer = collection.remove_all_by_age(age);
            list.add("remove_all_by_age");
        }
        else if (comparison(message, SORT)) {
            objectToServer = collection.sort();
            list.add("sort");
        }
        else {
            objectToServer = collection.wrong();
        }
        objectToServer.setUsername(USERNAME);
        System.out.println(USERNAME);
        return objectToServer;
    }
}
