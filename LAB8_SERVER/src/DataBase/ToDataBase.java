package DataBase;

import DragonStuff.Dragon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static Users.Users.getDBConnection;


public class ToDataBase {
    public static void saveToDB(Dragon dragon) {
        try {
            Connection connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement(String.format("INSERT INTO data1 (id, name, coordx, coordy, creationdate, age, description, wingspan, character, depth, numberoftreasures, username) " +
                    "VALUES (nextval('seq_id'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
            ps.setString(1,dragon.getName());
            ps.setString(2, String.valueOf(dragon.getCoordinates().getX()));
            ps.setString(3, String.valueOf(dragon.getCoordinates().getY()));
            ps.setString(4, String.valueOf(dragon.getCreationDate()).substring(0,10));
            ps.setString(5, String.valueOf(dragon.getAge()));
            ps.setString(6, dragon.getDescription());
            ps.setString(7, String.valueOf(dragon.getWingspan()));
            ps.setString(8, String.valueOf(dragon.getCharacter()));
            ps.setString(9, String.valueOf(dragon.getCave().getDepth()));
            ps.setString(10, String.valueOf(dragon.getCave().getNumberOfTreasures()));
            ps.setString(11, dragon.getUsername());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteFromDB(Integer id){
        try {
            Connection connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM data1 WHERE id = ?");
            ps.setString(1, String.valueOf(id));
            ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createSequence(){
        try {
            Connection connection = getDBConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE SEQUENCE seq_id START WITH 1 INCREMENT BY 1;");
        } catch (SQLException e) {
            System.out.println("ошибка во время создания sequence: " + e.getMessage());

        }
    }
}
