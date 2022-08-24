package TechStuff;

import Main.Modules;
import ObjectToSend.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CommandListener implements Serializable {
    public static final HashMap<String, Method> commands = new HashMap<>();
    private static final Commands commander = new Commands();
    public ObjectToServer objectToServer = new ObjectToServer();

    static {
        for (Method m : commander.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Annotation.class)) {
                Annotation annotation = m.getAnnotation(Annotation.class);
                commands.put(annotation.name(), m);
            }
        }
    }

    public ObjectToServer receiveMessage() {
        System.out.println("Введите команду:");
        Scanner in = new Scanner(System.in);
        String str;
        try {
            str = in.nextLine();
            String[] args = str.split(" ");
            String command = args[0].toLowerCase();
            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            if ((command.equals("add")) || (command.equals("execute_script"))){
                Method m = commands.get(command);
                objectToServer = (ObjectToServer) m.invoke(commander, (Object) commandArgs);
                Modules.sendObject(objectToServer);
            } else if (command.equals("exit")) {
                objectToServer.setName("еxit");
                objectToServer.setObject("");
                Modules.sendObject(objectToServer);
                System.exit(0);
            }else if (command.equals("save")) {
                System.out.println("Вы не можете воспользоваться командой save. ");
                objectToServer.setName("sаve");
                objectToServer.setObject("");
                Modules.sendObject(objectToServer);
            } else {
                objectToServer.setName(str);
                objectToServer.setObject("");
                Modules.sendObject(objectToServer);
            }

        } catch (NullPointerException | ArrayIndexOutOfBoundsException | InvocationTargetException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return objectToServer;
    }
}
