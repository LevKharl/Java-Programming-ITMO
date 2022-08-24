package DragonStuff;

import TechStuff.CollectionHelper;

import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

import Main.Main;

public class Dragon {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private Integer wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле может быть null

    public Integer getId() {
        return id;
    }

    public void setId() throws NoSuchFieldException, IllegalAccessException {
        Integer id = (Integer) ((int) (Math.random() * (Math.pow(2, 31))));

        CollectionHelper helper = new CollectionHelper();
        while (helper.get_object_by_id(Main.objects, id) != null) {
            id = (int) (Math.random() * (Math.pow(2, 31)));
        }
        this.id = id;
    }

    public void set_id_manually(int id) throws NoSuchFieldException, IllegalAccessException {
        CollectionHelper helper = new CollectionHelper();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        while (age <= 0) {
            System.out.println("Значение поля должно быть больше 0! Попробуйте ещё раз: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            while (!Pattern.compile("\\d+").matcher(input).matches()) {
                System.out.println("Значение введено неправильно! Попробуйте ещё раз: ");
                input = scanner.nextLine();
            }
            age = Integer.parseInt(input);
        }
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWingspan() {
        return wingspan;
    }

    public void setWingspan(Integer wingspan) {
        while (wingspan <= 0) {
            System.out.println("Значение поля должно быть больше 0! Попробуйте ещё раз: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            while (!Pattern.compile("\\d+").matcher(input).matches()) {
                System.out.println("Значение введено неправильно! Попробуйте ещё раз: ");
                input = scanner.nextLine();
            }
            wingspan = Integer.parseInt(input);
        }
        this.wingspan = wingspan;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public DragonCave getCave() {
        return cave;
    }

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    @Override
    public String toString() {
        return ("Транспортное средство [id: " + id + "]\n\t" + "Название: " + name + "\n\tКоординаты:\n\t\tx = " +
                coordinates.getX() + "\n\t\ty = " + coordinates.getY() + "\n\tДата cоздания: " + creationDate.getDayOfMonth() + "." +
                creationDate.getMonth() + "." + creationDate.getYear() + " " + creationDate.getHour() + ":" + creationDate.getMinute() +
                ":" + creationDate.getSecond() + "\n\tПещера дракона: " + cave + "\n\tРазмах крыла: " + wingspan +
                "\n\tХарактер дракона: " + character + "\n\tОписание: " + description);
    }

    public String getInfo() {
        return (id + "   " + name + "   " + coordinates.getX() + "   " + coordinates.getY() + "   " + creationDate.getDayOfMonth() + "." +
                creationDate.getMonth() + "." + creationDate.getYear() + "  " + creationDate.getHour() + ":" + creationDate.getMinute() +
                ":" + creationDate.getSecond() + "   " + wingspan + "   " + character + "   " + cave + "   " + description + "\n");
    }

}



