package DataBase;

import DragonStuff.DragonCharacter;
import DragonStuff.DragonCollection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Users.Users.getDBConnection;

public class FromDataBase {
    public static void readFromDB() {
        Connection connection = getDBConnection();
        DragonCollection collection = new DragonCollection();
        String selectTableSQL = "SELECT id, name, coordx, coordy, creationDate, age, description, wingspan, character, depth, numberOfTreasures, username from data1";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Integer id = Integer.parseInt(rs.getString("id"));
                String name = rs.getString("name");
                float coordX = Float.parseFloat(rs.getString("coordx"));
                Float coordY = Float.parseFloat(rs.getString("coordy"));
                String creationDate = rs.getString("creationdate");
                int age = Integer.parseInt(rs.getString("age"));
                String description = rs.getString("description");
                Integer wingspan = Integer.parseInt(rs.getString("wingspan"));
                DragonCharacter character = DragonCharacter.valueOf(rs.getString("character"));
                Integer depth = Integer.parseInt(rs.getString("depth"));
                Float numberOfTreasures = Float.parseFloat(rs.getString("numberoftreasures"));
                String username = rs.getString("username");
                collection.addC(id, name, coordX, coordY, creationDate, age, description,
                        wingspan, character, depth, numberOfTreasures, username);
            }
        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\nКоллекция успешно заполнена!");
    }
}
