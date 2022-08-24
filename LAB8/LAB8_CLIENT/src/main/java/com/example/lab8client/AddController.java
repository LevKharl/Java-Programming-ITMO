package com.example.lab8client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

import static com.example.lab8client.CurrLocale.locale;

public class AddController {
    @FXML
    private Button addButton;

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
    private MenuItem evil;

    @FXML
    private MenuItem fick;

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

    String setcharacter;
    ResourceBundle resourceBundle;

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
    public Button getAddButton() {
        return addButton;
    }

    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        addButton.setText(resourceBundle.getString("Add.addButton"));


        evil.setOnAction(actionEvent -> setcharacter = "EVIL");
        cun.setOnAction(actionEvent -> setcharacter = "CUNNING");
        cha.setOnAction(actionEvent -> setcharacter = "CHAOTIC");
        fick.setOnAction(actionEvent -> setcharacter = "FICKLE");
    }

}

