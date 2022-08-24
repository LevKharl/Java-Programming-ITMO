package TechStuff;

import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class CommandListener implements Serializable {
    public static final HashMap<String, Method> commands = new HashMap<>();
    private static final Commands commander = new Commands();
    ObjectToClient objectToClient = new ObjectToClient();
    ObjectToServer objectToServer = new ObjectToServer();
    Commands command2 = new Commands();

    static {
        for (Method m : commander.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Annotation.class)) {
                Annotation annotation = m.getAnnotation(Annotation.class);
                commands.put(annotation.name(), m);
            }
        }
    }

    public ObjectToClient receiveMessage(Object o) {
        objectToServer = (ObjectToServer) o;
        try {
            String[] args;
            String command;
            String[] commandArgs = new String[0];
            if ( objectToServer.getName().split(" ").length > 1)
            {
                args = objectToServer.getName().split(" ");
                command = args[0].toLowerCase();
                commandArgs = Arrays.copyOfRange(args, 1, args.length);
            }
            else
            {
                command = objectToServer.getName();
            }

            if (command.equals("add")) {
                command2.add(objectToServer.getObject());
                objectToClient.setMessage("Объект был успешно добавлен в коллекцию!");
            } else {
                Method m = commands.get(command);
                objectToClient = (ObjectToClient) m.invoke(commander, (Object) commandArgs);
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            objectToClient.setMessage("Проверьте правильность введённой комманды, её аргументов и полей, если создавался объект. Пользуйтесь справкой help.");
            e.printStackTrace();
        }

        return objectToClient;
    }
}
