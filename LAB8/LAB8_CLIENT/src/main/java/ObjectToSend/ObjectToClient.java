package ObjectToSend;

import DragonStuff.Dragon;

import java.io.Serializable;
import java.util.ArrayList;

import static DragonStuff.DragonCollection.collection;

public class ObjectToClient implements Serializable {
    private static final long serialVersionUID = 1;
    public String message;
    public ArrayList<Dragon> array;

    public ObjectToClient(String message, ArrayList<Dragon> array) {
        this.message = message;
        this.array = array;
    }

    public ObjectToClient() {

    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setObject(){
        array.addAll(collection);
    }
    public ArrayList<Dragon> getObject(){
        return array;
    }
}
