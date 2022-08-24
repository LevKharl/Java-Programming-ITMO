package ObjectToSend;

import java.io.Serial;
import java.io.Serializable;

public class ObjectToServer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    public String name;
    public Object object;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
}
