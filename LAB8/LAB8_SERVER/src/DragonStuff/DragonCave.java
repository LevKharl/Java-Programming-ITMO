package DragonStuff;

import java.io.Serializable;

public class DragonCave implements Serializable {
    private Integer depth; //Поле может быть null
    private Float numberOfTreasures; //Поле не может быть null, Значение поля должно быть больше 0

    public Integer getDepth() {
        return depth;
    }
    public void setDepth(Integer depth) {
        this.depth = depth;
    }
    public Float getNumberOfTreasures() {
        return numberOfTreasures;
    }
    public void setNumberOfTreasures(Float numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }

}
