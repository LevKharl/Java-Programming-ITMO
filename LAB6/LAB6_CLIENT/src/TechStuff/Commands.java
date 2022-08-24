package TechStuff;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import DragonStuff.DragonCave;
import DragonStuff.DragonCharacter;
import ObjectToSend.*;

public class Commands implements Serializable {
    ObjectToServer objectToServer = new ObjectToServer();

    @Annotation(name = "add")
    public ObjectToServer add(String[] args) throws NoSuchFieldException, IllegalAccessException {
        objectToServer.setName("add");
            if (args.length != 0) System.out.println("У этой команды нет аргументов!");
            else {
                Scanner scanner = new Scanner(System.in);
                Dragon dragon = new Dragon();
                Coordinates coordinates = new Coordinates();
                DragonCave dragonCave = new DragonCave();

            String inputArg;
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
            objectToServer.setObject(dragon);
            System.out.println(objectToServer.getObject());
        }
        return objectToServer;
    }

    @Annotation(name = "exit")
    public void exit(String[] args) {
        System.out.println("Работа клиента прекращается. Все данные будут автоматически сохранены сервером.");
        System.exit(0);
    }

    @Annotation(name = "execute_script")
    public ObjectToServer executeScript(String[] args) throws
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
                        objectToServer.setObject(dragon);
                    } else System.out.println("Объект из файла не был создан.");
                }
                if (!Objects.equals(command, "add") && !Objects.equals(command, "update")) {
                    try {
                        m.invoke(this, (Object) commandArgs);
                    } catch (NullPointerException e) {
                        System.out.println("Неправильная команда в файле!");
                    }
                }
                System.out.println("\n");
            }
        }
        return objectToServer;
    }
}