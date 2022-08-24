package ObjectToSend;

import java.io.Serial;
import java.io.Serializable;

public class ObjectToClient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    public String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
