package com.example.lab8client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ResourceBundle;

import static com.example.lab8client.CurrLocale.locale;

public class FilterController {

    @FXML
    private MenuItem age;

    @FXML
    private MenuItem character;

    @FXML
    private MenuItem coordx;

    @FXML
    private MenuItem coordy;

    @FXML
    private MenuItem date;

    @FXML
    private MenuItem depth;

    @FXML
    private MenuItem description;

    @FXML
    private MenuItem equal;

    @FXML
    private TextField field;

    @FXML
    private Button filterButton;

    @FXML
    private MenuButton filtertype;

    @FXML
    private MenuItem id;

    @FXML
    private MenuItem less;

    @FXML
    private MenuItem more;

    @FXML
    private MenuItem name;

    @FXML
    private MenuButton selectfield;

    @FXML
    private MenuItem treasures;

    @FXML
    private MenuItem wingspan;

    ResourceBundle resourceBundle;

    public MenuButton getSelectcolumn() {
        return selectfield;
    }
    public MenuButton getFiltertype() {
        return filtertype;
    }
    public Button getFilterbutton() {
        return filterButton;
    }
    public TextField getAimvalue() {
        return field;
    }
    public MenuItem getLess() {
        return less;
    }
    public MenuItem getMore() {
        return more;
    }
    public MenuItem getEqual() {
        return equal;
    }

    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        field.setText(resourceBundle.getString("filterTable.field"));
        filterButton.setText(resourceBundle.getString("filter"));
    }
}
