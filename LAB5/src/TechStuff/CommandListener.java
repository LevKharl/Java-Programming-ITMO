package TechStuff;


import java.util.*;
import java.lang.reflect.*;


public class CommandListener {
    public static final HashMap<String, Method> commands = new HashMap<>();
    private static final Commands commander = new Commands();

    static {
        for (Method m : commander.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Annotation.class)) {
                Annotation annotation = m.getAnnotation(Annotation.class);
                commands.put(annotation.name(), m);
            }
        }
    }

    public String receive_message() {
        System.out.println("Введите команду:");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void check_message(String message) {
        try {
            String[] args = message.split(" ");
            String command = args[0].toLowerCase();
            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            Method m = commands.get(command);

            m.invoke(commander, (Object) commandArgs);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Проверьте правильность введённой комманды, её аргументов и полей, если создавался объект. Пользуйтесь справкой help.");
        }
    }
}
