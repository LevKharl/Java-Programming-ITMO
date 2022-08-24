package TechStuff;


import DragonStuff.Dragon;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;


public class CollectionHelper implements Serializable {
    public ArrayList<Dragon> sort_сollection(ArrayList<Dragon> coll) {
        ArrayList<Dragon> buffer = new ArrayList<>(coll);
        buffer.sort(Comparator.comparing(Dragon::getId));
        return new ArrayList<>(buffer);
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
