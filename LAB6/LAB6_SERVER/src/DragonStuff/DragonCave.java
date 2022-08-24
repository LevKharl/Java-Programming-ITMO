package DragonStuff;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        while (numberOfTreasures <= 0) {
            System.out.println("Значение поля должно быть больше 0! Попробуйте ещё раз: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            while (!Pattern.compile("\\d+").matcher(input).matches()) {
                System.out.println("Значение введено неправильно! Попробуйте ещё раз: ");
                input = scanner.nextLine();
            }
            numberOfTreasures = Float.parseFloat(input);
        }
        this.numberOfTreasures = numberOfTreasures;
    }

}
