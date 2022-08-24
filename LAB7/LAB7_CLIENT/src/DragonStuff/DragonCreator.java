package DragonStuff;

import DragonStuff.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DragonCreator implements Serializable {
    public final Scanner scanner = new Scanner(System.in);
    public Dragon create(String id) throws SQLException {
        Dragon dragon = new Dragon();
        if (id.equals("free")) {
            dragon.setId((int) DragonCollection.getFreeId());
        } else {
            dragon.setId(Integer.parseInt(id));
        }
        this.setName(dragon);
        Coordinates coordinates = new Coordinates();
        this.setCoordinateX(coordinates);
        this.setCoordinateY(coordinates);
        dragon.setCoordinates(coordinates);
        dragon.setCreationDate(ZonedDateTime.now());
        this.setAge(dragon);
        this.setDescription(dragon);
        this.setWingspan(dragon);
        this.setCharacter(dragon);
        DragonCave cave = new DragonCave();
        this.setDepth(cave);
        this.setNumberOfTreasures(cave);
        dragon.setCave(cave);
        return dragon;
    }


    public void setName(Dragon dragon) {
        try {
            System.out.println("Введите имя дракона: ");
            String name = scanner.nextLine();
            if (name.equals(" ") || name.isEmpty()) {
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
                this.setName(dragon);
            } else
                dragon.setName(name);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
            this.setName(dragon);
        }
    }
    public void setCoordinateY(Coordinates coords) {
        try {
            System.out.println("Введите координату Y: ");
            String y = scanner.nextLine();
            if (y.equals(null)) {
                this.setCoordinateY(coords);
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
            } else {
                float coordY = Float.parseFloat(y);
                coords.setY(coordY);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"float\"");
            this.setCoordinateY(coords);
        }
    }
    public void setCoordinateX(Coordinates coords) {
        try {
            System.out.println("Введите координату X: ");
            String x = scanner.nextLine();
            if (x.equals(null)) {
                this.setCoordinateX(coords);
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
            } else {
                float coordX = Float.parseFloat(x);
                if (coordX > 0) {
                    coords.setX(coordX);
                } else {
                    System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ БОЛЬШЕ НУЛЯ");
                    this.setCoordinateX(coords);
                }
                coords.setX(coordX);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"Float\"");
            this.setCoordinateX(coords);
        }
    }
    public void setAge(Dragon dragon) {
        try {
            System.out.println("Введите возраст: ");
            String c = scanner.nextLine();
            int age = Integer.parseInt(c);
            if (age <= 0) {
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЖНО БЫТЬ БОЛЬШЕ НУЛЯ");
                this.setAge(dragon);
            } else dragon.setAge(age);

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"int\"");
            this.setAge(dragon);
        }
    }
    public void setWingspan(Dragon dragon) {
        try {
            System.out.println("Введите размах крыла: ");
            String str = scanner.nextLine();
            Integer wingspan = Integer.parseInt(str);
            if (wingspan <= 0) {
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЖНО БЫТЬ БОЛЬШЕ НУЛЯ");
                this.setWingspan(dragon);
            } else dragon.setWingspan(wingspan);

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"Integer\"");
            this.setWingspan(dragon);
        }
    }
    public void setDescription(Dragon dragon){
        try {
            System.out.println("Введите описание");
            String description = scanner.nextLine();
            if (description.equals(null)) {
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЖНО БЫТЬ НЕ ПУСТО");
                this.setDescription(dragon);
            } else {
                dragon.setDescription(description);
            }

        } catch (InputMismatchException | DateTimeParseException d) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"String\"");
            this.setDescription(dragon);
        }
    }
    public void setCharacter(Dragon dragon) {
        System.out.println("Введите характер дракона { CUNNING, EVIL, CHAOTIC, FICKLE } : ");
        String type = scanner.nextLine();
        Pattern.compile("");
        try {
            if (type.isEmpty()) {
                dragon.setCharacter(null);
            }
            else
                dragon.setCharacter(DragonCharacter.valueOf(type.toUpperCase()));
        } catch (Exception e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ УКАЗАНО НЕКОРРЕКТНО");
            this.setCharacter(dragon);
        }
    }
    public void setDepth(DragonCave depth){
        try {
            System.out.println("Введите глубину пещеры: ");
            String y = scanner.nextLine();
            Integer d = Integer.parseInt(y);
            if (y.equals(null)) {
                this.setDepth(depth);
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
            } else {
                depth.setDepth(d);
            }
        } catch (NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"String\"");
            this.setDepth(depth);
        }
    }
    public void setNumberOfTreasures(DragonCave numberOfTreasures){
        try {
            System.out.println("Введите кол-во трофеев: ");
            String y = scanner.nextLine();
            Float number = Float.parseFloat(y);
            if (y.equals(null)) {
                this.setNumberOfTreasures(numberOfTreasures);
                System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ НЕ ДОЛЖНО БЫТЬ NULL");
            } else {
                numberOfTreasures.setNumberOfTreasures(number);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("{ERROR}: ЗНАЧЕНИЕ ПОЛЯ ДОЛЖНО БЫТЬ ТИПА \"Float\"");
            this.setNumberOfTreasures(numberOfTreasures);
        }
    }
}