package DragonStuff;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private Float y; //Поле не может быть null

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }
    public void setY(Float y) {
        this.y = y;
    }

}

