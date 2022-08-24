package com.example.lab8client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

import static com.example.lab8client.CurrLocale.locale;

public class UpdateController {
    private String setcharacter;
    ResourceBundle resourceBundle;
    @FXML
    private TextField ageField;

    @FXML
    private MenuItem cha;

    @FXML
    private MenuItem cun;

    @FXML
    private TextField depthField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button editButton;

    @FXML
    private MenuItem evil;

    @FXML
    private MenuItem fick;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField treasuresField;

    @FXML
    private TextField wingspanField;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;


    public String getName() {
        return String.valueOf(nameField.getText());
    }

    public String getCoordX() {
        return String.valueOf(xField.getText());
    }

    public String getCoordY() {
        return String.valueOf(yField.getText());
    }

    public String getAge() {
        return String.valueOf(ageField.getText());
    }

    public String getWingspan() {
        return String.valueOf(wingspanField.getText());
    }

    public String getDescription() {
        return String.valueOf(descriptionField.getText());
    }

    public String getCharacter() {
        return setcharacter;
    }

    public String getDepth() {
        return String.valueOf(depthField.getText());
    }

    public String getTreasures() {
        return String.valueOf(treasuresField.getText());
    }

    public Button getUpdateButton() {
        return editButton;
    }

    public String getId() {
        return String.valueOf(idField.getText());
    }

    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        editButton.setText(resourceBundle.getString("Update.editButton"));
        evil.setOnAction(actionEvent -> setcharacter = "EVIL");
        cun.setOnAction(actionEvent -> setcharacter = "CUNNING");
        cha.setOnAction(actionEvent -> setcharacter = "CHAOTIC");
        fick.setOnAction(actionEvent -> setcharacter = "FICKLE");
    }
}
