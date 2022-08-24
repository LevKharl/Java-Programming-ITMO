package DragonStuff;

import ObjectToSend.ObjectToServer;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Hashtable;


public class DragonCollection implements Serializable {
    public static ArrayList<Dragon> collection = new ArrayList<>();
    public static ZonedDateTime creationDate;

    public DragonCollection() {
        creationDate = ZonedDateTime.now();
    }

    ObjectToServer objectToServer = new ObjectToServer();

    public static long getFreeId() {
        Hashtable<Integer, String> busyIds = new Hashtable<>();
        for (Dragon dragon : collection)
            busyIds.put(dragon.getId(), "id");
        Integer id = 1;
        while (true) {
            if (busyIds.get(id) == null) {
                return id;
            } else id++;
        }
    }

    public ObjectToServer help() {
        objectToServer.setName("help");
        objectToServer.setObject("");
        return objectToServer;
    }

    public ObjectToServer info() {
        objectToServer.setName("info");
        objectToServer.setObject("");
        return objectToServer;
    }

    public ObjectToServer show() {
        objectToServer.setName("show");
        objectToServer.setObject("");
        return objectToServer;
    }

    public ObjectToServer add() {
        objectToServer.setName("add");
        objectToServer.setObject(new DragonCreator().create("free"));
        return objectToServer;
    }

    public ObjectToServer update_id(Integer id) {
        objectToServer.setName("update_id");
        objectToServer.setObject(new DragonCreator().create(String.valueOf(id)));
        return objectToServer;
    }

    public ObjectToServer remove_by_id(Integer id) {
        objectToServer.setName("remove_by_id");
        objectToServer.setObject(id);
        return objectToServer;
    }

    public ObjectToServer clear() {
        objectToServer.setName("clear");
        objectToServer.setObject("");
        return objectToServer;
    }

    public void exit() {
        System.exit(0);
    }//не трогаем

    public ObjectToServer shuffle() {
        objectToServer.setName("shuffle");
        objectToServer.setObject("");
        return objectToServer;
    }

    public ObjectToServer remove_greater(int age) {
        objectToServer.setName("remove_greater");
        objectToServer.setObject(age);
        return objectToServer;
    }

    public ObjectToServer filter_contains_name(String name) {
        objectToServer.setName("filter_contains_name");
        objectToServer.setObject(name);
        return objectToServer;
    }

    public ObjectToServer count_less_than_description(String description) {
        objectToServer.setName("count_less_than_description");
        objectToServer.setObject(description);
        return objectToServer;
    }

    public ObjectToServer remove_all_by_age(Integer age) {
        objectToServer.setName("remove_all_by_age");
        objectToServer.setObject(age);
        return objectToServer;
    }

    public ObjectToServer sort() {
        objectToServer.setName("sort");
        objectToServer.setObject("");
        return objectToServer;
    }

    public ObjectToServer wrong(){
        objectToServer.setName("null");
        objectToServer.setObject("");
        return objectToServer;
    }
}
