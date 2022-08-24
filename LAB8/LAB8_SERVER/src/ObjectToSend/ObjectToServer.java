package ObjectToSend;

import java.io.Serializable;

public class ObjectToServer implements Serializable {
    private static final long serialVersionUID = 1;
    public String name;
    public Object object;
    public String username;
    public String password;

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
    public String getUsername() {
        return username;
    }
    //public void setUsername(String username) {
    //    this.username = USERNAME;
    //}
    public String getPassword() {
        return password;
    }
    //public void setPassword(String password) {
    //    this.password = PASSWORD;
    //}
}
