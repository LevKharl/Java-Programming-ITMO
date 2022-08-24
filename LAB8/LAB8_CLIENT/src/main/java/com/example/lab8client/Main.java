package com.example.lab8client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.lab8client.CurrLocale.locale;

public class Main extends Application {

    static int port = 5432;
    static SocketAddress address;
    static SocketChannel sock;
    public static String USERNAME;
    public static String PASSWORD;
    ResourceBundle resourceBundle;

    @Override
    public void start(Stage stage) throws IOException {
        locale = new Locale("ru");
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        Creator creator = new Creator(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Enter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle(resourceBundle.getString("Enter.fxml"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
