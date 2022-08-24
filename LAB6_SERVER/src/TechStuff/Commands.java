package TechStuff;

import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import Main.Main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import ObjectToSend.*;

import static Main.Main.objects;

public class Commands implements Serializable {
    ObjectToClient objectToClient = new ObjectToClient();
    ObjectToServer objectToServer = new ObjectToServer();

    public static void file_writer(Dragon dragon) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(Main.database, true));
        XStream xstream = new XStream();
        xstream.alias("Dragon", Dragon.class);
        xstream.alias("Coordinates", Coordinates.class);
        printWriter.write(xstream.toXML(dragon));
        printWriter.flush();
        printWriter.close();
        System.out.println("Дракон с ID:" + dragon.getId() + " был успешно записан в файл.");
    }

    public static void file_reader(String filename) throws IOException, IllegalAccessException, NoSuchFieldException {
        StringBuilder data = new StringBuilder();
        ArrayList<String> data_to_read = new ArrayList<>();
        int total_dragons = 0, added_dragons = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            ;
            String line = "";
            while ((line = br.readLine()) != null) {
                data.append(line).append("\n");
                if (line.equals("</Dragon>")) {
                    data_to_read.add(String.valueOf(data));
                    data.delete(0, data.length());
                    total_dragons += 1;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }

        XStream xs = new XStream();
        xs.alias("Dragon", Dragon.class);
        xs.addPermission(AnyTypePermission.ANY);
        for (int i = 0; i < total_dragons; ++i) {
            try {
                Dragon newObject = (Dragon) xs.fromXML(data_to_read.get(i));
                CollectionHelper helper = new CollectionHelper();
                try {
                    if (helper.ReaderChecker(newObject)) {
                        objects.add(newObject);
                        objects = helper.sort_сollection(objects);
                        added_dragons++;
                    } else {
                        System.out.println("Объект с ID " + newObject.getId() + " не был добавлен, так как не удовлетворял требованиям!");
                    }
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    System.out.println(e.getMessage());
                }

            } catch (ConversionException e) {
                System.out.println("По данным из файла не может быть создан объект!");
            }

        }

        if (total_dragons - added_dragons > 0)
            System.out.println("Объектов, которые не могут быть созданы: " + (total_dragons - added_dragons));
        if (total_dragons == 0) System.out.println("Файл был пуст.");
        else if (added_dragons > 0)
            System.out.println("Десериализация всех объектов была выполнена успешно.\nБыло добавлено объектов: " + added_dragons);
        else System.out.println("Не было добавлено ни одного объекта.");
    }


    @Annotation(name = "help")
    public ObjectToClient help(String[] args) {
        String str = "";
        if (args.length != 0) {
            str = "У команды help нет аргумента.";
        } else {
            str = "help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add {element} : добавить новый элемент в коллекцию\n" +
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить сеанс клиента\n" +
                    "shuffle : перемешать элементы коллекции в случайном порядке\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                    "sort : отсортировать коллекцию в естественном порядке\n" +
                    "remove_all_by_age age : удалить из коллекции все элементы, значение поля age которого эквивалентно заданному\n" +
                    "count_less_than_description description : вывести количество элементов, значение поля description которых меньше заданного\n" +
                    "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\n";
        }
        objectToClient.setMessage(str);
        return objectToClient;
    }

    @Annotation(name = "info")
    public ObjectToClient info(String[] args) {
        String str = "";
        if (args.length != 0) {
            str = ("У команды info нет аргумента.");
        } else {
            str = (" Информация о коллекции:\n" +
                    "Тип коллекции - ArrayList\n" +
                    "Дата инициализации - " + LocalDate.now() + "\n" +
                    "Количество эллементов - " + objects.size());
        }
        objectToClient.setMessage(str);
        return objectToClient;
    }

    @Annotation(name = "show")
    public ObjectToClient show(String[] args) throws IllegalAccessException {
        if (args.length != 0) objectToClient.setMessage("Данная команда не имеет аргументов! Попробуйте ещё раз: ");
        else {
            if (objects.size() != 0) {
                long totalObjects = 1;
                String str = "";
                for (Dragon dragon : objects) {
                    str += ("\nОбъект " + totalObjects + "\n");
                    str += dragon.toString();
//                    for (Field f : dragon.getClass().getDeclaredFields()) {
//                        f.setAccessible(true);
//                        if (f.getType().getSimpleName().equals("Coordinates") || f.getType().getSimpleName().equals("LocationFrom") || f.getType().getSimpleName().equals("LocationTo")) {
//                            Object tmp = (Object) f.get(dragon);
//                            if (f.getType().getSimpleName().equals("Coordinates"))
//                                str1 = ("Coordinates:");
//                            else if (f.getType().getSimpleName().equals("LocationFrom"))
//                                str1 = ("LocationFrom:");
//                            else str1 = ("LocationTo:");
//
//                            for (Field subField : tmp.getClass().getDeclaredFields()) {
//                                subField.setAccessible(true);
//                                objectToClient.setMessage(str0+ "\n"+ str1  + "\t" + subField.getName() + ": " + subField.get(tmp));
//                            }
//                        } else
//                            objectToClient.setMessage(str0  + "\n" + str1 +"\n" + f.getName() + ": " + f.get(dragon));
//                    }
                    totalObjects++;
                    objectToClient.setMessage(str);
                }

            } else objectToClient.setMessage("Коллекция пуста..");
        }
        return objectToClient;
    }

    @Annotation(name = "add")
    public ObjectToClient add(Object o) throws NoSuchFieldException, IllegalAccessException {
        objects.add((Dragon) o);
        CollectionHelper helper = new CollectionHelper();
        objects = helper.sort_сollection(objects);
        objectToClient.setMessage("Объект был успешно добавлен в коллекцию!");
        return objectToClient;
    }

    @Annotation(name = "update")
    public ObjectToClient update(String[] args) throws NoSuchFieldException, IllegalAccessException {
        return objectToClient;
    }


    @Annotation(name = "remove_by_id")
    public ObjectToClient removeById(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("-*\\d+").matcher(args[0]).matches())
            objectToClient.setMessage("Не был введён ID объекта! Попробуйте ещё раз: ");
        else if (objects.size() != 0) {
            CollectionHelper helper = new CollectionHelper();
            Integer objID = Integer.parseInt(args[0]);
            objects.remove(helper.get_object_by_id(objects, objID));
            objectToClient.setMessage("Объект был удалён.");
        } else objectToClient.setMessage("Коллекция пуста");
        return objectToClient;
    }


    @Annotation(name = "clear")
    public ObjectToClient clear(String[] args) {
        if (args.length != 0) {
            objectToClient.setMessage("У команды clear нет аргумента. ");
        } else {
            objects.clear();
            objectToClient.setMessage("Была произведена очистка коллекции. ");
        }
        return objectToClient;
    }
    @Annotation(name = "save")
    public static void save(String[] args) throws IOException {
        PrintWriter printWriter = new PrintWriter(Main.database);
        printWriter.write("");
        printWriter.flush();
        printWriter.close();
        if (objects.size() != 0) {
            System.out.println("Начата запись в файл новых объектов коллекции.");
            for (Dragon dragon : objects) {
                System.out.println(dragon.getName());
                file_writer(dragon);

            }
        } else System.out.println("Коллекция пуста.");
    }

    @Annotation(name = "execute_script")
    public ObjectToClient executeScript(Object o) {
        o = objectToServer.getObject();
        objects.add((Dragon) o);
        objectToClient.setMessage("Объект из файла был создан.");
        return objectToClient;
    }


    @Annotation(name = "exit")
    public void exit(String[] args) {
        if (args.length != 0) {
            System.out.println("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            System.out.println("Работа сервера завершится без сохранения данных. Вы хотите продолжить? (да/нет)");
            Scanner scan = new Scanner(System.in);
            String message = scan.nextLine().toLowerCase();
            switch (message) {
                case ("да"):
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    @Annotation(name = "remove_all_by_age")
    public ObjectToClient remove_all_by_age(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("\\d+").matcher(args[0]).matches())
            objectToClient.setMessage("Не было введено значение age! Попробуйте ещё раз: ");
        else {
            int totalDeleted = 0;
            for (Dragon dragon : objects) {
                Field age = dragon.getClass().getDeclaredField("age");
                age.setAccessible(true);

                if ((Integer) age.get(dragon) == Integer.parseInt(args[0])) {
                    objects.remove(dragon);
                    totalDeleted += 1;
                }
            }
            if (totalDeleted != 0)
                objectToClient.setMessage("Все объекты с age, равным " + args[0] + " были удалены. Таких объектов было " + totalDeleted + ".");
            else objectToClient.setMessage("Объектов с age, равным " + args[0] + " не оказалось.");
        }
        return objectToClient;
    }

    @Annotation(name = "count_less_than_description")
    public ObjectToClient count_less_than_description(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0) {
            objectToClient.setMessage("Не было введено значение description! Попробуйте ещё раз: ");
        } else if (objects.size() != 0) {
            boolean condition = true;
            ArrayList<Dragon> spisok = new ArrayList<>();

            for (Dragon dragon : objects) {
                if (args[0].compareTo(dragon.getDescription()) < 0) {
                    spisok.add(dragon);
                    condition = false;
                }
            }
            if (condition)
                objectToClient.setMessage("Похоже таких объектов не нашлось\n");
            else {
                objectToClient.setMessage("кол-во элементов коллекции значение поля description которых меньше " + args[0]);
                objectToClient.setMessage(String.valueOf(spisok.size()));
                objectToClient.setMessage(" ");
            }
        } else {
            objectToClient.setMessage("коллекция пуста, чтобы воспользоваться командой заполните коллекцию\n");
        }
        return objectToClient;
    }

    @Annotation(name = "filter_contains_name")
    public ObjectToClient filter_contains_name(String[] args) throws
            NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        if (args.length == 1) {
            String str = args[0];
            int sizeOfCollection = objects.size();
            if (sizeOfCollection == 0) {
                objectToClient.setMessage("В коллекции нет объектов.");
            }
            objects.stream().filter(dragon1 -> dragon1.getName().contains(str)).forEach(dragon1 -> objectToClient.setMessage(dragon1.toString()));
            System.out.println("Все драконы с именем " + str + " были успешно выведены");
        } else objectToClient.setMessage("У этой команды должен быть один аргумент. Повторите попытку.");
        return objectToClient;
    }

    @Annotation(name = "sort")
    public ObjectToClient sort(String[] args) {
        if (objects.size() == 0) {
            objectToClient.setMessage("Коллекция пуста, сортировать нечего.");
        } else if (args.length != 0) {
            objectToClient.setMessage("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            ArrayList<Dragon> buffer = new ArrayList<>(objects);
            buffer.sort(Comparator.comparing(Dragon::getId));
            objectToClient.setMessage("Коллекция отсортирована по умолчанию.");
        }
        return objectToClient;
    }

    @Annotation(name = "shuffle")
    public ObjectToClient shuffle(String[] args) {
        int sizeOfCollection = objects.size();
        if (sizeOfCollection == 0) {
            objectToClient.setMessage("Коллекция пуста, перемешивать нечегою.");
        } else if (args.length != 0) {
            objectToClient.setMessage("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            Collections.shuffle(objects);
            objectToClient.setMessage("Коллекция перемешана успешно.");
        }
        return objectToClient;
    }

    @Annotation(name = "remove_greater")
    public ObjectToClient remove_greater(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("-*\\d+").matcher(args[0]).matches())
            objectToClient.setMessage("Не было введено значение age! Попробуйте ещё раз: ");
        else {
            int totalDeleted = 0;
            for (Dragon dragon : objects) {
                Field age = dragon.getClass().getDeclaredField("age");
                age.setAccessible(true);

                if ((Integer) age.get(dragon) > Integer.parseInt(args[0])) {
                    objects.remove(dragon);
                    totalDeleted += 1;
                }
            }
            if (totalDeleted != 0) {
                objectToClient.setMessage("Все объекты с age, большим " + args[0] + " были удалены. Таких объектов было " + totalDeleted + ".");
            } else {
                objectToClient.setMessage("Объектов с age, большим " + args[0] + " не оказалось.");
            }
        }
        return objectToClient;
    }

}