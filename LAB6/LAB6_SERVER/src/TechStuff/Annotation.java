package TechStuff;

import java.lang.annotation.*;

//Retention тип хранения
// RUNTIME т.е. может использоваться во время выполнения самой проги
@Retention(RetentionPolicy.RUNTIME)

//Таргет задает тип объекта над которым может указываться создаваемая аннотация(метод)
@Target(ElementType.METHOD)

//Описание нашей анатации
public @interface Annotation {
    //имя команды
    String name();
}