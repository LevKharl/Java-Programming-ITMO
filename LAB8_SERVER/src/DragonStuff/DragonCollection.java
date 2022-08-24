package DragonStuff;

import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;
import Users.Registration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static DataBase.ToDataBase.deleteFromDB;
import static DataBase.ToDataBase.saveToDB;
import static Users.Users.*;

public class DragonCollection implements Serializable {
    public static ArrayList<Dragon> collection = new ArrayList<>();
    public static ZonedDateTime creationDate;
    public DragonCollection() {
        creationDate = ZonedDateTime.now();
    }
    public int getSize() {
        return collection.size();
    }
    public static ArrayList<Dragon> getCollection() {
        return collection;
    }
    ObjectToClient objectToClient = new ObjectToClient();
    String str = "";
    public static long getFreeId() throws SQLException {
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

    public ObjectToClient username(Registration user) throws SQLException {
        return containsUrPd(user);
    }
    public ObjectToClient register(Registration user) throws SQLException {
        return addnew(user);
    }
    public boolean comparison(String message, Pattern pattern) {
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }
    public void addC(Integer id, String name, float coordX, Float coordY, String creationDate, int age, String description, Integer wingspan, DragonCharacter character, Integer depth, Float numberOfTreasures, String username) {
        collection.add(new DragonCreator().createINSave(id, name, coordX, coordY, creationDate, age, description, wingspan, character, depth, numberOfTreasures, username));
    }

    public ObjectToClient help() {
        str = """
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update_id id : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить сеанс клиента
                shuffle : перемешать элементы коллекции в случайном порядке
                remove_greater age : удалить из коллекции все элементы, превышающие заданный возраст
                sort : отсортировать коллекцию в естественном порядке
                remove_all_by_age age : удалить из коллекции все элементы, значение поля age которого эквивалентно заданному
                count_less_than_description description : вывести количество элементов, значение поля description которых меньше заданного
                filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку
                """;
        objectToClient.setMessage(str);
        return objectToClient;
    }

    public ObjectToClient info() {
        str = (LocalDate.now() + "," + this.getSize());
        objectToClient.setMessage(str);
        return objectToClient;
    }

    public ObjectToClient show() {
        if (collection.size() != 0) {
            long totalObjects = 1;
            for (Dragon dragon : collection) {
                str += ("\nОбъект " + totalObjects + "\n");
                str += dragon.toString();
                totalObjects++;
            }
        } else objectToClient.setMessage("Коллекция пуста..");
        objectToClient.setMessage("array of objects");
        objectToClient.setObject();
        return objectToClient;
    }

    public synchronized ObjectToClient add(ObjectToServer o) {
        Dragon dragon = (Dragon) o.getObject();
        dragon.setUsername(o.getUsername());
        saveToDB(dragon);
        Integer id = 0;
        try {
            Connection connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM data1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) id = rs.getInt(12);
            dragon.setId(id);
            collection.add(dragon);
            objectToClient.setMessage("Объект был успешно добавлен в коллекцию!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectToClient;
    }

    public synchronized ObjectToClient update_id(Dragon o, String username) {
        try {
            for (Dragon dragon : collection) {
                if ((dragon.getId().equals(o.getId())) && (dragon.getUsername().equals(username))) {
                        Dragon delete = DragonCollection.getCollection().stream().filter(x -> x.getId().equals(o.getId())).findFirst().get();
                        DragonCollection.getCollection().remove(delete);
                        collection.add(o);
                        str = "элемент [id = " + o.getId() + "] успешно обновлен\n";
                        break;
                    } else {
                        str = "Вы можете редактировать только свои объекты\n";
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            update_id(o, username);
        }
        objectToClient.setMessage(str);
        return objectToClient;
    }

    public synchronized ObjectToClient remove_by_id(Integer id, String username) {
        try {
            for (Dragon dragon : collection) {
                if (dragon.getUsername().equals(username)) {
                    Dragon delete = DragonCollection.getCollection().stream().filter(x -> x.getId().equals(id)).findFirst().get();
                    DragonCollection.getCollection().remove(delete);
                    deleteFromDB(id);
                    str = "элемент с [id = " + id + "] удален. \n";
                    break;
                } else {
                    str = "это не ваш элемент, вы не можете его удалить. \n";
                }
            }
        } catch (Exception e) {
            str = "элемента с таким id не сущетсвует. \n";
        }
        objectToClient.setMessage(str);
        return objectToClient;
    }


    public synchronized ObjectToClient clear(String username) {
        if (collection.size() == 0)
            str = "коллекция была пуста. ";
        else {
            for (Dragon dragon : collection) {
                if (dragon.getUsername().equals(username)) {
                    Dragon delete = DragonCollection.getCollection().stream().filter(x -> x.getId() == dragon.getId()).findFirst().get();
                    DragonCollection.getCollection().remove(delete);
                    deleteFromDB(dragon.getId());
                    str = "Была произведена очистка коллекции. \n";
                }
            }
        }
        objectToClient.setMessage(str);
        return objectToClient;
    }

    public ObjectToClient execute_script(Object o) {
        try {
            File file = new File(str + ".txt");
            BufferedReader br;
            if (!file.exists()) {
                System.out.println("файл не найден\n");
            } else {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String message = line.toLowerCase();
                    {
                        //создание шаблонов ввода команд
                        Pattern HELP = Pattern.compile("\\s*help\\s*");
                        Pattern INFO = Pattern.compile("\\s*info\\s*");
                        Pattern SHOW = Pattern.compile("\\s*show\\s*");
                        Pattern ADD = Pattern.compile("\\s*add\\s*");
                        Pattern UPDATE = Pattern.compile("\\s*update\\d*");
                        Pattern REMOVE_BY_ID = Pattern.compile("\\s*removeById\\d*");
                        Pattern CLEAR = Pattern.compile("\\s*clear\\s*");
                        Pattern EXECUTE_SCRIPT = Pattern.compile("\\s*executeScript\\s[a-zA-Z0-9]+\\s*");
                        Pattern EXIT = Pattern.compile("\\s*exit\\s*");
                        Pattern REMOVE_GREATER = Pattern.compile("\\s*remove_greater\\d*");
                        Pattern SHUFFLE = Pattern.compile("\\s*shuffle\\s*");
                        Pattern FILTER_CONTAINS_NAME = Pattern.compile("\\s*filter_contains_name\\s*");
                        Pattern COUNT_LESS_THAN_DESCRIPTION = Pattern.compile("\\s*count_less_than_description\\s*");
                        Pattern REMOVE_ALL_BY_AGE= Pattern.compile("\\s*remove_all_by_age\\s*");
                        Pattern SORT = Pattern.compile("\\s*sort\\s*");
                        Pattern USER = Pattern.compile("\\s*user\\s*");
                        Pattern REGISTER = Pattern.compile("\\s*register\\s*");
                        //проверка ввода и вызов соответствующих команд
                        if (comparison(message, HELP)) {
                            //help();
                        } else if (comparison(message, INFO)) {
                            info();
                        } else if (comparison(message, SHOW)) {
                            //show();
                        } else if (comparison(message, ADD)) {
                            //add();
                        } else if (comparison(message, EXIT)) {
                            exit();
                        } else if (comparison(message, CLEAR)) {
                            //clear();
                        } else if (comparison(message, REMOVE_GREATER)) {
                            //remove_greater();
                        } else if (comparison(message, UPDATE)) {
                            String[] symbols = message.split(" ");
                            Integer number = Integer.parseInt(symbols[symbols.length - 1]);
                            //update(number);
                        } else if (comparison(message, REMOVE_BY_ID)) {
                            String[] symbols = message.split(" ");
                            int number = Integer.parseInt(symbols[symbols.length - 1]);
                            //removeById(number);
                        } else if (comparison(message, SHUFFLE)) {
                            //shuffle();
                        } else if (comparison(message, COUNT_LESS_THAN_DESCRIPTION)) {
                            //count_less_than_description();
                        } else if (comparison(message, FILTER_CONTAINS_NAME)) {
                            //filter_contains_name();
                        } else if (comparison(message, REMOVE_ALL_BY_AGE)) {
                            String[] symbols = message.split(" ");
                            int number = Integer.parseInt(symbols[symbols.length - 1]);
                            //remove_all_by_age(number);
                        } else if (comparison(message, SORT)) {
                            //sort();
                        } else
                            System.out.println("команда не распознана.\n");
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения/записи файла.\n");
        }
        return objectToClient;
    }

    public void exit() {
        System.out.println("Работа сервера завершится без сохранения данных. Вы хотите продолжить? (да/нет)");
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine().toLowerCase();
        switch (message.toLowerCase()) {
            case ("да"):
                System.exit(0);
            default:
                break;
        }
    }

    public synchronized ObjectToClient remove_all_by_age(int agee, String username) throws NoSuchFieldException, IllegalAccessException {
            int totalDeleted = 0;
            for (Dragon dragon : collection) {
                Field age = dragon.getClass().getDeclaredField("age");
                age.setAccessible(true);
                if ((dragon.getUsername()).equals(username) && (Integer) age.get(dragon) == agee) {
                    deleteFromDB(dragon.getId());
                    totalDeleted += 1;
                } else System.out.println("вы не можете удалить элемент [id = " + dragon.getId() + "т.к. это не ваш элемент. ");
            }
            if (totalDeleted != 0)
                objectToClient.setMessage("Все объекты с age, равным " + agee + " были удалены. Таких объектов было " + totalDeleted + ".");
            else objectToClient.setMessage("Объектов с age, равным " + agee + " не оказалось.");
        return objectToClient;
    }

    public ObjectToClient count_less_than_description(String description) {
         if (collection.size() != 0) {
            boolean condition = true;
            ArrayList<Dragon> spisok = new ArrayList<>();
            for (Dragon dragon : collection) {
                if (description.compareTo(dragon.getDescription()) < 0) {
                    spisok.add(dragon);
                    condition = false;
                }
            }
            if (condition)
                objectToClient.setMessage("Похоже таких объектов не нашлось\n");
            else {
                str = "кол-во элементов коллекции значение поля description которых меньше " + description;
                str += "\n" + spisok.size();
                objectToClient.setMessage(str);
            }
        } else {
            objectToClient.setMessage("коллекция пуста, чтобы воспользоваться командой заполните коллекцию\n");
        }
        return objectToClient;
    }

    public ObjectToClient filter_contains_name(String name) throws IllegalArgumentException {
        str = name;
            if (collection.size() == 0) {
                objectToClient.setMessage("В коллекции нет объектов.");
            } else  {
                collection.stream().filter(dragon1 -> dragon1.getName().contains(str)).forEach(dragon1 -> objectToClient.setMessage(dragon1.toString()));
                System.out.println("Все драконы с именем " + str + " были успешно выведены");
            }
        return objectToClient;
    }

    public ObjectToClient sort() {
        if (collection.size() == 0) {
            objectToClient.setMessage("Коллекция пуста, сортировать нечего.");
        } else {
            ArrayList<Dragon> buffer = new ArrayList<>(collection);
            buffer.sort(Comparator.comparing(Dragon::getId));
            objectToClient.setMessage("Коллекция отсортирована по умолчанию.");
        }
        return objectToClient;
    }

    public ObjectToClient shuffle() {
        Collections.shuffle(collection);
        objectToClient.setMessage("Коллекция перемешана успешно.");
        return objectToClient;
    }

    public synchronized ObjectToClient remove_greater(int agee, String username) throws NoSuchFieldException, IllegalAccessException {
        int totalDeleted = 0;
        for (Dragon dragon : collection) {
            Field age = dragon.getClass().getDeclaredField("age");
            age.setAccessible(true);
            if (((Integer) age.get(dragon) > agee) && (dragon.getUsername().equals(username))) {
                collection.remove(dragon);
                deleteFromDB(dragon.getId());
                totalDeleted += 1;
            }
        }
        if (totalDeleted != 0) {
            objectToClient.setMessage("Все объекты с age, большим " + agee + " были удалены. Таких объектов было " + totalDeleted + ".");
        } else {
            objectToClient.setMessage("Объектов с age, большим " + agee + " не оказалось.");
        }
        return objectToClient;
    }


}
