package Main;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import static DataBase.FromDataBase.readFromDB;

public class Main implements Serializable {
    public static void main(String[] args) throws SQLException, IOException {
        readFromDB();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (ServerSocket server = new ServerSocket(6789)) {
            while (!server.isClosed()) {
                Socket client = server.accept();
                Thread thread = new Thread(new Receiver(client));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
