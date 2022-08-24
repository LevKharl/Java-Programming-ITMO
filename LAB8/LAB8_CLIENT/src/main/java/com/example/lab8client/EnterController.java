package com.example.lab8client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.lab8client.Creator.stage;
import static Users.Users.check_user;
import static com.example.lab8client.CurrLocale.locale;
import static com.example.lab8client.Modules.readMessage;

public class EnterController {
    @FXML
    private MenuItem english;

    @FXML
    private Button enterButton;

    @FXML
    private Label enterLabel;

    @FXML
    private MenuItem finnish;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private MenuItem polish;

    @FXML
    private Button regButton;

    @FXML
    private MenuItem russian;

    @FXML
    private MenuButton selectLang;

    ResourceBundle resourceBundle;

    public void openWindow(String filename) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(filename));
            stage.setScene(new Scene(loader.load(), 600, 400));
            stage.setTitle(resourceBundle.getString(filename));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        selectLang.setText(resourceBundle.getString("Enter.selectLang"));
        russian.setText(resourceBundle.getString("Enter.russian"));
        polish.setText(resourceBundle.getString("Enter.polish"));
        finnish.setText(resourceBundle.getString("Enter.finnish"));
        english.setText(resourceBundle.getString("Enter.english"));
        enterButton.setText(resourceBundle.getString("Enter.enterButton"));
        regButton.setText(resourceBundle.getString("Enter.regButton"));
        enterLabel.setText(resourceBundle.getString("Enter.enterLabel"));

        enterButton.setOnAction(actionEvent -> {
            String username = loginField.getText();
            String password = passwordField.getText();
            if ((!username.isEmpty()) && (!password.isEmpty())) {
                try {
                    Modules.connectToServer();
                    Modules.sendObject(check_user(username, password));
                    String out = (readMessage().message);
                    System.out.println(out);
                    if (out.equals("Пользователь инициализирован. ")) {
                        openWindow("Table.fxml");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        regButton.setOnAction(actionEvent -> {
            openWindow("Registration.fxml");
        });

        russian.setOnAction(actionEvent -> {
            locale = new Locale("ru");
            openWindow("Enter.fxml");
        });
        english.setOnAction(actionEvent -> {
            locale = new Locale("en");
            openWindow("Enter.fxml");
        });
        finnish.setOnAction(actionEvent -> {
            locale = new Locale("fi");
            openWindow("Enter.fxml");
        });
        polish.setOnAction(actionEvent -> {
            locale = new Locale("po");
            openWindow("Enter.fxml");
        });
    }
}
