package com.example.lab8client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ResourceBundle;

import static com.example.lab8client.Creator.stage;
import static Users.Users.add_user;
import static com.example.lab8client.CurrLocale.locale;
import static com.example.lab8client.Modules.readMessage;

public class RegistrationController {

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button RegistrationButton;

    @FXML
    private Button cancelRegistrationButton;

    @FXML
    private TextField regField;

    @FXML
    private Label regLabel;

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
        regLabel.setText(resourceBundle.getString("Registration.regLabel"));
        RegistrationButton.setText(resourceBundle.getString("Registration.RegistrationButton"));
        cancelRegistrationButton.setText(resourceBundle.getString("Registration.cancelRegistrationButton"));

        RegistrationButton.setOnAction(actionEvent -> {
            String username = regField.getText();
            String password = PasswordField.getText();
            if ((!username.isEmpty()) && (!password.isEmpty())) {
                try {
                    Modules.connectToServer();
                    Modules.sendObject(add_user(username, password));
                    String out = (readMessage().message);
                    System.out.println(out);
                    if (out.equals("Пользователь зарегистрирован. ")) {
                        openWindow("Enter.fxml");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        cancelRegistrationButton.setOnAction(actionEvent -> {
            openWindow("Enter.fxml");
        });

    }
}


