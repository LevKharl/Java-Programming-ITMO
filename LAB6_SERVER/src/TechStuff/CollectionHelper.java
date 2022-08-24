package TechStuff;

import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import DragonStuff.DragonCave;
import Main.Main;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

public class CollectionHelper implements Serializable {
    public ArrayList<Dragon> sort_сollection(ArrayList<Dragon> coll) {
        ArrayList<Dragon> buffer = new ArrayList<>(coll);
        buffer.sort(Comparator.comparing(Dragon::getId));
        return new ArrayList<>(buffer);
    }

    public boolean ReaderChecker(Dragon dragon) throws IllegalAccessException, NoSuchFieldException {
        for (Field f : dragon.getClass().getDeclaredFields()) {
            f.setAccessible(true);

            String fieldName = f.getName();
            switch (fieldName) {
                case "id" -> {
                    if (get_object_by_id(Main.objects, (Integer) f.get(dragon)) != null) {
                        System.out.println("ID номер " + f.get(dragon) + "не является уникальным!");
                        return false;
                    }
                }
                case "name" -> {
                    if (!Pattern.compile(".+").matcher((String) f.get(dragon)).matches()) {
                        System.out.println("Недопустимое имя объекта!");
                        return false;
                    }
                }
                case "coordinates" -> {
                    Coordinates coords = (Coordinates) f.get(dragon);
                    for (Field subField : coords.getClass().getDeclaredFields()) {
                        subField.setAccessible(true);
                    }
                }
                case "cave" -> {
                    DragonCave dc = (DragonCave) f.get(dragon);
                    for (Field subField : dc.getClass().getDeclaredFields()) {
                        subField.setAccessible(true);
                        if (subField.getName().equals("numberOfTreasures") && subField.get(dc) == null) {
                            System.out.println("Значение кол-ва трофеев неверно!");
                            return false;
                        } else if (subField.getName().equals("depth") && String.valueOf(subField.get(dc)).equals("")) {
                            dc.setDepth(null);
                        }
                    }
                }
                case "wingspan" -> {
                    Integer obj_wing = (Integer) f.get(dragon);
                    if (obj_wing <= 0) {
                        System.out.println("Поле distance должно быть больше 0!");
                        return false;
                    }
                }
                case "age" -> {
                    int obj_age = (int) f.get(dragon);
                    if (obj_age <= 0) {
                        System.out.println("Поле age должно быть больше 0!");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Dragon get_object_by_id(ArrayList<Dragon> coll, Integer id) throws NoSuchFieldException, IllegalAccessException {
        if (coll.size() != 0) {
            for (Dragon obj : coll) {
                Field f = obj.getClass().getDeclaredField("id");
                f.setAccessible(true);
                if (Objects.equals(String.valueOf(f.get(obj)), String.valueOf(id))) return obj;
            }
        }
        return null;
    }
}
