package Users;

import ObjectToSend.ObjectToServer;

import java.io.Serializable;
import java.util.Scanner;
import static Main.Main.USERNAME;
import static Main.Main.PASSWORD;

public class Users implements Serializable {
    static Registration object = new Registration();
    static ObjectToServer obj = new ObjectToServer();
    public static ObjectToServer check_user(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите имя пользователя: ");
        String username = scan.nextLine();
        object.setUsername(username);
        System.out.println("Введите пароль: ");
        String passwrd = scan.nextLine();
        object.setPassword(passwrd);
        USERNAME = username;
        PASSWORD = passwrd;
        obj.setName("user");
        obj.setObject(object);
        return obj;
    }
    public static ObjectToServer add_user(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите имя пользователя: ");
        String username = scan.nextLine();
        object.setUsername(username);
        System.out.println("Введите пароль: ");
        String passwrd = scan.nextLine();
        object.setPassword(passwrd);
        obj.setName("register");
        obj.setObject(object);
        return obj;
    }
}
