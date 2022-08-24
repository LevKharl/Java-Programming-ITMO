package Users;

import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
public class Users implements Serializable {

    public static String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, bytes);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            generatedPassword = hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:50002/studs", "s337029", "zps137");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static ObjectToClient containsUrPd(Registration name) throws SQLException {
        Connection connection = getDBConnection();
        ObjectToClient obj = new ObjectToClient();
        String selectTableSQL = "SELECT username, password from users1";
        try {
            Integer flag = 0;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {
                String username = rs.getString("username");
                String passwrd = rs.getString("password");
                if (username.equals(name.getUsername()) && passwrd.equals(getSecurePassword(name.getPassword(), name.getUsername()))) {
                    flag = 1;
                    break;
                } else if (username.equals(name.getUsername()) && !passwrd.equals(name.getPassword())) {
                    flag = 2;
                }
            }
            if (flag == 1) {
                obj.setMessage("Пользователь инициализирован. ");
            }
            if (flag == 2) {
                obj.setMessage("Неверный пароль. ");
            }
            if (flag == 0) {
                obj.setMessage("Пользователь не зарегистрирован. ");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connection.close();
        return obj;
    }

    public static ObjectToClient addnew(Registration name) throws SQLException {
        ObjectToClient obj = new ObjectToClient();
        String u = name.getUsername();
        String p = getSecurePassword(name.getPassword(), name.getUsername());
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("INSERT INTO users1 (username, password) VALUES ('%s', '%s')", u, p));
        preparedStatement.execute();
        obj.setMessage("Пользователь зарегистрирован. ");
        connection.close();
        return obj;
    }

    public static Boolean checkuser(ObjectToServer obj) {
        String usrname = obj.getUsername();
        String passw = obj.getPassword();
        Connection connection = getDBConnection();
        String selectTableSQL = "SELECT username, password from users1";
        boolean flag = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                if (username.equals(usrname) && password.equals(passw)) {
                    flag = true;
                    break;
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
