package Users;

import ObjectToSend.ObjectToServer;

import java.io.Serializable;
import java.util.Scanner;

import static com.example.lab8client.Main.*;
public class Users implements Serializable {
    static Registration object = new Registration();
    static ObjectToServer obj = new ObjectToServer();
    public static ObjectToServer check_user(String username, String password){
        object.setUsername(username);
        object.setPassword(password);
        USERNAME = username;
        PASSWORD = password;
        obj.setName("user");
        obj.setObject(object);
        return obj;
    }
    public static ObjectToServer add_user(String username, String password){
        object.setUsername(username);
        object.setPassword(password);
        obj.setName("register");
        obj.setObject(object);
        return obj;
    }
}
