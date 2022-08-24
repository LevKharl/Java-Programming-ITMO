package ObjectToSend;

import DragonStuff.Dragon;
import DragonStuff.DragonCollection;
import Users.Registration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandListener implements Serializable {
    public static List<String> list = new LinkedList<>();
    public boolean comparison(String message, Pattern pattern) {
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }
    ObjectToClient objectToClient = new ObjectToClient();
    ObjectToServer objectToServer = new ObjectToServer();

    public ObjectToClient receiveMessage(Object o) throws SQLException {

        DragonCollection collection = new DragonCollection();

        objectToServer = (ObjectToServer) o;
        String message = objectToServer.getName().toLowerCase();

        Pattern HELP = Pattern.compile("\\s*help\\s*");
        Pattern INFO = Pattern.compile("\\s*info\\s*");
        Pattern SHOW = Pattern.compile("\\s*show\\s*");
        Pattern ADD = Pattern.compile("\\s*add\\s*");
        Pattern UPDATE = Pattern.compile("\\s*update_id\\d*");
        Pattern REMOVE_BY_ID = Pattern.compile("\\s*remove_by_id\\d*");
        Pattern CLEAR = Pattern.compile("\\s*clear\\s*");
        Pattern EXECUTE_SCRIPT = Pattern.compile("\\s*execute_script\\s[a-zA-Z0-9]+\\s*");
        Pattern EXIT = Pattern.compile("\\s*exit\\s*");
        Pattern REMOVE_GREATER = Pattern.compile("\\s*remove_greater\\d*");
        Pattern SHUFFLE = Pattern.compile("\\s*shuffle\\s*");
        Pattern FILTER_CONTAINS_NAME = Pattern.compile("\\s*filter_contains_name\\s*");
        Pattern COUNT_LESS_THAN_DESCRIPTION = Pattern.compile("\\s*count_less_than_description\\s*");
        Pattern REMOVE_ALL_BY_AGE= Pattern.compile("\\s*remove_all_by_age\\s*");
        Pattern SORT = Pattern.compile("\\s*sort\\s*");
        Pattern USER = Pattern.compile("\\s*user\\s*");
        Pattern REGISTER = Pattern.compile("\\s*register\\s*");

        if (comparison(message, HELP)) {
            objectToClient = collection.help();
            list.add("help");
        }
        else if (comparison(message, INFO)) {
            objectToClient = collection.info();
            list.add("info");
        }
        else if (comparison(message, SHOW)) {
            objectToClient = collection.show();
            list.add("show");
        }
        else if (comparison(message, ADD)) {
            objectToClient = collection.add(objectToServer);
            list.add("add");
        }
        else if (comparison(message, UPDATE)) {
            objectToClient = collection.update_id((Dragon) objectToServer.getObject(), objectToServer.getUsername());
            list.add("update_id");
        }
        else if (comparison(message, REMOVE_BY_ID)) {
            objectToClient = collection.remove_by_id((Integer) objectToServer.getObject(), objectToServer.getUsername());
            list.add("remove_by_id");
        }
        else if (comparison(message, CLEAR)) {
            objectToClient = collection.clear(objectToServer.getUsername());
            list.add("clear");
        }
        else if (comparison(message, EXECUTE_SCRIPT)) {
            String[] symbols = message.split(" ");
            String file = symbols[1];
            collection.execute_script(file);
            list.add("execute_script");
        }
        else if (comparison(message, EXIT)) {
            collection.exit();
            list.add("exit");
        }
        else if (comparison(message, SHUFFLE)) {
            objectToClient = collection.shuffle();
            list.add("shuffle");
        }
        else if (comparison(message, REMOVE_GREATER)) {
            try {
                objectToClient = collection.remove_greater((int) objectToServer.getObject(), objectToServer.getUsername());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            list.add("remove_greater");
        }
        else if (comparison(message, FILTER_CONTAINS_NAME)) {
            objectToClient = collection.filter_contains_name((String)objectToServer.getObject());
            list.add("filter_contains_name");
        }
        else if (comparison(message, COUNT_LESS_THAN_DESCRIPTION)) {
            objectToClient = collection.count_less_than_description((String) objectToServer.getObject());
            list.add("average_of_number_of_participants");
        }
        else if (comparison(message, REMOVE_ALL_BY_AGE)) {
            try {
                objectToClient = collection.remove_all_by_age((int) objectToServer.getObject(), objectToServer.getUsername());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            list.add("count_less_than_description");
        }
        else if (comparison(message, SORT)) {
            objectToClient = collection.sort();
            list.add("sort");
        }
        else if (comparison(message, USER)){
            objectToClient = collection.username((Registration) objectToServer.getObject());
        }
        else if (comparison(message, REGISTER)){
            objectToClient = collection.register((Registration) objectToServer.getObject());
        }
        else{
            System.out.println("команда не распознана, введите «help» чтобы ознакомиться со списком команд");
            objectToClient.setMessage("команда не распознана, введите «help» чтобы ознакомиться со списком команд");
        }
        return objectToClient;
    }
}
