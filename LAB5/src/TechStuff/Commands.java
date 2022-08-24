package TechStuff;

import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import DragonStuff.DragonCave;
import DragonStuff.DragonCharacter;
import Main.Main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static Main.Main.objects;

public class Commands {

    public static void file_writer(Dragon dragon) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(Main.database, true));
        XStream xstream = new XStream();
        xstream.alias("Dragon", Dragon.class);
        xstream.alias("Coordinates", Coordinates.class);
        printWriter.write(xstream.toXML(dragon));
        printWriter.flush();
        printWriter.close();
        System.out.println("Дракон с ID:" + dragon.getId() + "был успешно записан в файл");
    }

    public static void file_reader(String filename) throws IOException, IllegalAccessException, NoSuchFieldException {
        StringBuilder data = new StringBuilder();
        ArrayList<String> data_to_read = new ArrayList<>();
        int total_dragons = 0, added_dragons = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));;
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
        } catch(IOException e) {
        System.out.println("Ошибка ввода/вывода: " + e.getMessage());
    }

        XStream xs = new XStream();
        xs.alias("Dragon", Dragon.class);
        xs.addPermission(AnyTypePermission.ANY);
        for(int i = 0; i  < total_dragons; ++i) {
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

        if(total_dragons -added_dragons >0)
            System.out.println("Объектов, которые не могут быть созданы: "+(total_dragons -added_dragons));
        if(total_dragons ==0)System.out.println("Файл был пуст.");
        else if(added_dragons >0)
            System.out.println("Десериализация всех объектов была выполнена успешно.\nБыло добавлено объектов: "+added_dragons);
        else System.out.println("Не было добавлено ни одного объекта.");
}


    @Annotation(name = "help")
    public void help(String[] args) {
        if (args.length != 0) {
            System.out.println("У команды help нет аргумента.");
        } else {
            System.out.println("help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add {element} : добавить новый элемент в коллекцию\n" +
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить сеанс\n" +
                    "shuffle : перемешать элементы коллекции в случайном порядке\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                    "sort : отсортировать коллекцию в естественном порядке\n" +
                    "remove_all_by_age age : удалить из коллекции все элементы, значение поля age которого эквивалентно заданному\n" +
                    "count_less_than_description description : вывести количество элементов, значение поля description которых меньше заданного\n" +
                    "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\n");
        }
    }

    @Annotation(name = "info")
    public void info(String[] args) {
        if (args.length != 0) {
            System.out.println("У команды info нет аргумента.");
        } else {
            System.out.println(" Информация о коллекции:\n" +
                    "Тип коллекции - ArrayList\n" +
                    "Дата инициализации - " + LocalDate.now() + "\n" +
                    "Количество эллементов - " + objects.size());
        }
    }

    @Annotation(name = "show")
    public void show(String[] args) throws IllegalAccessException {
        if (args.length != 0) System.out.println("Данная команда не имеет аргументов! Попробуйте ещё раз: ");
        else {
            if (objects.size() != 0) {
                long totalObjects = 1;
                for (Dragon dragon : objects) {
                    System.out.println("\nОбъект " + totalObjects + "\n");
                    for (Field f : dragon.getClass().getDeclaredFields()) {
                        f.setAccessible(true);

                        if (f.getType().getSimpleName().equals("Coordinates") || f.getType().getSimpleName().equals("LocationFrom") || f.getType().getSimpleName()
                                .equals("LocationTo")) {
                            Object tmp = (Object) f.get(dragon);
                            if (f.getType().getSimpleName().equals("Coordinates")) System.out.println("Coordinates:");
                            else if (f.getType().getSimpleName().equals("LocationFrom"))
                                System.out.println("LocationFrom:");
                            else System.out.println("LocationTo:");

                            for (Field subField : tmp.getClass().getDeclaredFields()) {
                                subField.setAccessible(true);
                                System.out.println("\t" + subField.getName() + ": " + subField.get(tmp));
                            }
                        } else System.out.println(f.getName() + ": " + f.get(dragon));
                    }
                    totalObjects++;
                }

            } else System.out.println("Коллекция пуста..");
        }
    }

    @Annotation(name = "add")
    public void add(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length != 0) System.out.println("У этой команды нет аргументов!");
        else {
            Scanner scanner = new Scanner(System.in);
            Dragon dragon = new Dragon();
            Coordinates coordinates = new Coordinates();
            DragonCave dragonCave = new DragonCave();

            String inputArg = "";
            System.out.println("По очереди введите значения всех полей: ");

            for (int i = 0; i < 10; ++i) {
                switch (i) {
                    case 0 -> {
                        dragon.setId();
                    }
                    case 1 -> {
                        System.out.println("Введите имя объекта: ");
                        inputArg = scanner.nextLine();
                        while (!Pattern.compile("\\S+").matcher(inputArg).matches()) {
                            System.out.println("Значение имени введено неправильно! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        dragon.setName(inputArg);
                    }
                    case 2 -> {
                        System.out.println("Введите координату х: ");
                        inputArg = scanner.nextLine();
                        while (!Pattern.compile("-*\\d+").matcher(inputArg).matches()) {
                            System.out.println("Значение координаты Х введено неправильно! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        coordinates.setX(Float.parseFloat(inputArg));
                    }
                    case 3 -> {
                        System.out.println("Введите координату y: ");
                        inputArg = scanner.nextLine();
                        while (!Pattern.compile("-*\\d+").matcher(inputArg).matches()) {
                            System.out.println("Значение координаты Y введено неправильно! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        coordinates.setY(Float.parseFloat(inputArg));
                        dragon.setCoordinates(coordinates);
                    }
                    case 4 -> {
                        System.out.println("Введите характер дракона: ");
                        inputArg = scanner.nextLine();
                        while (!inputArg.equals("CUNNING") && !inputArg.equals("EVIL") && !inputArg.equals("CHAOTIC") && !inputArg.equals("FICKLE")) { //прописать сравнение
                            System.out.println("Значение характера дракона введено неправильно! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        dragon.setCharacter(DragonCharacter.valueOf((inputArg)));
                    }
                    case 5 -> {
                        System.out.println("Введите глубину пещеры дракона: ");
                        inputArg = scanner.nextLine();
                        if (inputArg.equals("")) {
                            try {
                                dragonCave.setDepth(null);
                            } catch (NullPointerException | InputMismatchException e) {
                                System.out.println("Полю с глубиной пещеры будет присвоено значние null.");
                            }
                        } else {
                            while (!Pattern.compile("\\d+").matcher(inputArg).matches()) {
                                System.out.println("Значение глубины пещеры дракона ведено неправильно! Попробуйте ещё раз: ");
                                inputArg = scanner.nextLine();
                            }
                            dragonCave.setDepth(Integer.parseInt(inputArg));
                        }
                    }
                    case 6 -> {
                        System.out.println("Введите кол-во трофеев в пещере: ");
                        inputArg = scanner.nextLine();
                        while (!Pattern.compile("\\d+").matcher(inputArg).matches()) {
                            System.out.println("Кол-во трофеев не может быть пустым! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        dragonCave.setNumberOfTreasures(Float.valueOf(inputArg));

                        dragon.setCave(dragonCave);
                    }
                    case 7 -> {
                        System.out.println("Введите размах крыла: ");
                        inputArg = scanner.nextLine();
                        if (inputArg.equals("")) {
                            try {
                                dragon.setWingspan(null);
                            } catch (NullPointerException | InputMismatchException e) {
                                System.out.println("Полю с размахом крыла будет присвоено значние null.");
                            }
                        } else {
                            while (!Pattern.compile("\\d+").matcher(inputArg).matches()) {
                                System.out.println("Значение размаха крыла введено неправильно! Попробуйте ещё раз: ");
                                inputArg = scanner.nextLine();
                            }
                            dragon.setWingspan(Integer.parseInt(inputArg));
                        }
                    }
                    case 8 -> {
                        System.out.println("Введите описание дракона: ");
                        inputArg = scanner.nextLine();
                        if (inputArg.equals("")) {
                            try {
                                dragon.setDescription(null);
                            } catch (NullPointerException | InputMismatchException e) {
                                System.out.println("Полю с описанием дракона будет присвоено значние null.");
                            }
                        } else {
                            while (!Pattern.compile("\\S+").matcher(inputArg).matches()) {
                                System.out.println("Значение описания введено неправильно! Попробуйте ещё раз: ");
                                inputArg = scanner.nextLine();
                            }
                            dragon.setDescription(inputArg);
                        }
                    }
                    case 9 -> {
                        System.out.println("Введите возраст дракона: ");
                        inputArg = scanner.nextLine();
                        while (!Pattern.compile("\\d+").matcher(inputArg).matches()) {
                            System.out.println("Значение возраста дракона введено неправильно! Попробуйте ещё раз: ");
                            inputArg = scanner.nextLine();
                        }
                        dragon.setAge(Integer.parseInt(inputArg));
                    }
                    default -> System.out.println("Что-то пошло не так.");
                }
            }

            dragon.setCreationDate(ZonedDateTime.now());
            objects.add(0, dragon);

            CollectionHelper helper = new CollectionHelper();
            objects = helper.sort_сollection(objects);

            System.out.println("Объект был успешно добавлен в коллекцию!");
        }
    }

    @Annotation(name = "update")
    public void update(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("-*\\d+").matcher(String.valueOf(args[0])).matches())
            System.out.println("Не был введён ID объекта! Попробуйте ещё раз: ");
        else {
            if (objects.size() != 0) {
                Integer idBuffer = 0;
                CollectionHelper helper = new CollectionHelper();

                Dragon updatedObj = helper.get_object_by_id(objects, Integer.parseInt(args[0]));

                if (updatedObj != null) {
                    System.out.println("Объект получен.");
                    idBuffer = updatedObj.getId();
                    String[] arg = new String[0];
                    add(arg);
                    updatedObj = objects.get(0);
                    Field id = updatedObj.getClass().getDeclaredField("id");
                    id.setAccessible(true);
                    id.set(updatedObj, idBuffer);
                } else System.out.println("Такого объекта в коллекции нет.");
            } else System.out.println("Коллекция пуста.");
        }
    }


    @Annotation(name = "remove_by_id")
    public void removeById(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("-*\\d+").matcher(args[0]).matches())
            System.out.println("Не был введён ID объекта! Попробуйте ещё раз: ");
        else if (objects.size() != 0) {
            CollectionHelper helper = new CollectionHelper();
            Integer objID = Integer.parseInt(args[0]);
            objects.remove(helper.get_object_by_id(objects, objID));
            System.out.println("Объект был удалён.");
        } else System.out.println("Коллекция пуста");
    }

    @Annotation(name = "clear")
    public void clear(String[] args) {
        if (args.length != 0) {
            System.out.println("У команды clear нет аргумента. ");
        } else {
            objects.clear();
            System.out.println("Была произведена очистка коллекции. ");
        }
    }

    @Annotation(name = "save")
    public void save(String[] args) throws IOException, IllegalAccessException {
        if (args.length != 0) {
            System.out.println("У команды save нет аргумента. ");
        } else {
            PrintWriter printWriter = new PrintWriter(Main.database);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            if (objects.size() != 0) {
                System.out.println("Начата запись в файл новых объектов коллекции.");
                for (Dragon dragon : objects) file_writer(dragon);
            } else System.out.println("Коллекция пуста.");
        }
    }

    @Annotation(name = "execute_script")
    public void executeScript(String[] args) throws
            IOException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if (args.length == 0 || !Pattern.compile("[a-zA-Z0-9]+\\.[a-zA-Z0-9]+").matcher(args[0]).matches())
            System.out.println("Имя файла не было введено, либо введено неверно! Попробуйте ещё раз: ");
        else {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(args[0]));
            CommandListener listener = new CommandListener();
            String line;
            while ((line = String.valueOf(bis.read())) != null) {
                String[] checkLine = line.split(" ");
                String command = checkLine[0];
                String[] commandArgs = Arrays.copyOfRange(checkLine, 1, checkLine.length);
                Method m = CommandListener.commands.get(command);

                System.out.println("Результат выполнения комманды " + command + ":");
                if (Pattern.compile(" *add *").matcher(command).matches() || Pattern.compile(" *update *").matcher(command).matches()) {
                    boolean able_to_create = true;

                    Dragon dragon = new Dragon();
                    Coordinates coordinates = new Coordinates();
                    DragonCave dragonCave = new DragonCave();

                    String lineForCheck = "";
                    for (int i = 0; i < 11; ++i) {
                        if (i != 0) lineForCheck = String.valueOf(bis.read());
                        switch (i) {
                            case 0 -> {
                                if (Objects.equals(command, "update")) {
                                    if (Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                        dragon.set_id_manually(Integer.parseInt(lineForCheck));
                                    } else {
                                        System.out.println("По данным для поля ID невозможно создать объект!");
                                        able_to_create = false;
                                    }
                                } else {
                                    dragon.setId();
                                    System.out.println("ID был установлен автоматически. Он равен " + dragon.getId());
                                }

                            }
                            case 1 -> {
                                if (lineForCheck.equals("") || !Pattern.compile(".*").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля name невозможно создать объект!");
                                    able_to_create = false;
                                } else dragon.setName(lineForCheck);
                            }
                            case 2 -> {
                                if (lineForCheck.equals("") || !Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля coordinates невозможно создать объект!");
                                    able_to_create = false;
                                } else coordinates.setX(Float.parseFloat(lineForCheck));
                            }
                            case 3 -> {
                                if (!Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля coordinates невозможно создать объект!");
                                    able_to_create = false;
                                } else {
                                    coordinates.setY(Float.parseFloat(lineForCheck));
                                    dragon.setCoordinates(coordinates);
                                }
                            }
                            case 4 -> {
                                if (!Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля creationDate невозможно создать объект!");
                                    able_to_create = false;
                                } else {
                                    dragon.setCreationDate(ZonedDateTime.parse(lineForCheck));
                                }
                            }
                            case 5 -> {
                                if (lineForCheck.equals("") || !Pattern.compile("\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля age невозможно создать объект!");
                                    able_to_create = false;
                                } else dragon.setAge(Integer.parseInt(lineForCheck));
                            }
                            case 6 -> {
                                if (lineForCheck.equals("") || !Pattern.compile("-*").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля description невозможно создать объект!");
                                    able_to_create = false;
                                } else dragon.setDescription(lineForCheck);
                            }
                            case 7 -> {
                                if (lineForCheck.equals("") || !Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля wingspan невозможно создать объект!");
                                    able_to_create = false;
                                } else {
                                    dragon.setWingspan(Integer.parseInt(lineForCheck));
                                }
                            }
                            case 8 -> {
                                if (!lineForCheck.equals("CUNNING") && !lineForCheck.equals("EVIL") && !lineForCheck.equals("CHAOTIC") && !lineForCheck.equals("FICKLE")) {
                                    System.out.println("По данным для поля dragonCharacter невозможно создать объект!");
                                    able_to_create = false;
                                } else dragon.setCharacter(DragonCharacter.valueOf(lineForCheck));
                            }
                            case 9 -> {
                                if (lineForCheck.equals("") || !Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля depth невозможно создать объект!");
                                    able_to_create = false;
                                } else dragonCave.setDepth(Integer.parseInt(lineForCheck));
                            }
                            case 10 -> {
                                if (!Pattern.compile("-*\\d+").matcher(lineForCheck).matches()) {
                                    System.out.println("По данным для поля NumberOfTreasures невозможно создать объект!");
                                    able_to_create = false;
                                } else {
                                    dragonCave.setNumberOfTreasures(Float.parseFloat(lineForCheck));
                                    dragon.setCave(dragonCave);
                                }
                            }
                        }
                    }
                    if (able_to_create) {
                        objects.add(dragon);
                        System.out.println("Объект из файла был создан.");
                    } else System.out.println("Объект из файла не был создан.");
                }
                if (!Objects.equals(command, "add") && !Objects.equals(command, "update")) {
                    try {
                        m.invoke(this, (Object) commandArgs);
                    } catch (NullPointerException e) {
                        System.out.println("Неправильная комманда в файле!");
                    }
                }
                System.out.println("\n");
            }
        }

    }

    @Annotation(name = "exit")
    public void exit(String[] args) {
        if (args.length != 0) {
            System.out.println("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            System.out.println("Работа программы завершается без сохранения данных.");
            System.exit(0);
        }
    }

    @Annotation(name = "remove_all_by_age")
    public void remove_all_by_age(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("\\d+").matcher(args[0]).matches())
            System.out.println("Не было введено значение age! Попробуйте ещё раз: ");
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
                System.out.println("Все объекты с age, равным " + args[0] + " были удалены. Таких объектов было " + totalDeleted + ".");
            else System.out.println("Объектов с age, равным " + args[0] + " не оказалось.");
        }
    }

    @Annotation(name = "count_less_than_description")
    public void count_less_than_description(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0) {
            System.out.println("Не было введено значение description! Попробуйте ещё раз: ");
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
                System.out.println("Похоже таких объектов не нашлось\n");
            else {
                System.out.println("кол-во элементов коллекции значение поля description которых меньше " + args[0]);
                System.out.println(spisok.size());
                System.out.println(" ");
            }
        } else {
            System.out.println("коллекция пуста, чтобы воспользоваться командой заполните коллекцию\n");
        }
    }

    @Annotation(name = "filter_contains_name")
    public void filter_contains_name(String[] args) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        if (args.length == 1) {
            String str = args[0];
            int sizeOfCollection = objects.size();

            if (sizeOfCollection == 0) {
                System.out.println("В коллекции нет объектов.");
            }
            for (int b = 0; b <= sizeOfCollection; b++) {
                Dragon dragon = objects.get(b);

                if (dragon != null) {
                    if (str.equals(String.valueOf(dragon.getName()))) {

                        System.out.println("Номер iD: " + dragon.getId());
                        System.out.println("Имя дракона: " + dragon.getName());
                        System.out.println("Координата x: " + dragon.getCoordinates().getX());
                        System.out.println("Координата y: " + dragon.getCoordinates().getY());
                        System.out.println("Дата: " + dragon.getCreationDate());
                        System.out.println("Возраст: " + dragon.getAge());
                        System.out.println("Описание : " + dragon.getDescription());
                        System.out.println("Размах крыла: " + dragon.getWingspan());
                        System.out.println("Характер: " + dragon.getCharacter());
                        System.out.println("Пещера " + dragon.getCave());
                    }
                }
            }
            System.out.println("Все драконы с именем " + str + "были успешно выведены");
        } else System.out.println("У этой команды должен быть один аргумент. Повторите попытку.");
    }

    @Annotation(name = "sort")
    public void sort(String[] args) {
        if (objects.size() == 0) {
            System.out.println("Коллекция пуста, сортировать нечего.");
        } else if (args.length != 0) {
            System.out.println("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            ArrayList<Dragon> buffer = new ArrayList<>(objects);
            buffer.sort(Comparator.comparing(Dragon::getId));
            System.out.println("Коллекция отсортирована по умолчанию.");
        }
    }

    @Annotation(name = "shuffle")
    public void shuffle(String[] args) {
        int sizeOfCollection = objects.size();
        if (sizeOfCollection == 0) {
            System.out.println("Коллекция пуста, перемешивать нечегою.");
        } else if (args.length != 0) {
            System.out.println("У этой команды нет аргумента, попробуйте ещё раз.");
        } else {
            Collections.shuffle(objects);
            System.out.println("Коллекция перемешана успешно.");
        }
    }

    @Annotation(name = "remove_greater")
    public void remove_greater(String[] args) throws NoSuchFieldException, IllegalAccessException {
        if (args.length == 0 || !Pattern.compile("-*\\d+").matcher(args[0]).matches())
            System.out.println("Не было введено значение age! Попробуйте ещё раз: ");
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
                System.out.println("Все объекты с age, большим " + args[0] + " были удалены. Таких объектов было " + totalDeleted + ".");
            } else {
                System.out.println("Объектов с age, большим " + args[0] + " не оказалось.");
            }
        }
    }

}