package DragonStuff;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

import Main.Main;

public class Dragon implements Comparable<Dragon>, Serializable {
    private static final long serialVersionUID = 2;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private Integer wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле может быть null
    private String username;
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
        return ("Дракон [id: " + id + "]\n\t" + "Название: " + name + "\n\tКоординаты:\n\t\tx = " +
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

    @Override
    public int compareTo(Dragon o) {
        Integer integer = id;
        return integer.compareTo(o.getId());
    }
}



