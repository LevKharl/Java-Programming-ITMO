package com.example.lab8client;


import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import DragonStuff.DragonCave;
import DragonStuff.DragonCharacter;
import ObjectToSend.ObjectToClient;
import ObjectToSend.ObjectToServer;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.lab8client.Creator.stage;
import static com.example.lab8client.CurrLocale.locale;
import static com.example.lab8client.Main.PASSWORD;
import static com.example.lab8client.Main.USERNAME;
import static com.example.lab8client.Modules.readMessage;

public class TableController {

    @FXML
    private Button exitButton;

    @FXML
    private Label ownerLabel;

    @FXML
    private Button reloadTableButton;

    @FXML
    private TableView<Dragon> tableView;

    @FXML
    private Button visualizationButton;


    static ObjectToServer objectToServer = new ObjectToServer();
    static ObjectToClient objectToClient = new ObjectToClient();
    static ArrayList<Dragon> array;
    public boolean isTableCreated = false;
    AddController addController;
    UpdateController updateController;
    FilterController filterController;
    TableFilter tableFilter;
    static ResourceBundle resourceBundle;

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

    public static ContextMenu createTableContextMenu() {
        ContextMenu tableContextMenu = new ContextMenu();

        tableContextMenu.getItems().addAll(
                new MenuItem(resourceBundle.getString("CollectionInfo")),
                new MenuItem(resourceBundle.getString("FilterTable")),
                new MenuItem(resourceBundle.getString("AddDragon")),
                new MenuItem(resourceBundle.getString("UpdateById")),
                new MenuItem(resourceBundle.getString("ClearMyDragons")),
                new MenuItem(resourceBundle.getString("Delete")),
                new MenuItem(resourceBundle.getString("ExecuteScript"))
        );
        return tableContextMenu;
    }

    protected void refreshTable() {
        ObjectToServer objectToServer = new ObjectToServer();
        objectToServer.setName("show");
        objectToServer.setUsername(USERNAME);
        objectToServer.setPassword(PASSWORD);

        try {
            Modules.sendObject(objectToServer);
            objectToClient = readMessage();
            array = objectToClient.array;
            ObservableList<Dragon> obsObj = FXCollections.observableList(array);
            tableView.setItems(obsObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        ownerLabel.setText(resourceBundle.getString("ownerLabel") + USERNAME);
        visualizationButton.setText(resourceBundle.getString("visualizationButton"));
        exitButton.setText(resourceBundle.getString("exitButton"));
        reloadTableButton.setText(resourceBundle.getString("reloadTableButton"));

        exitButton.setOnAction(actionEvent -> {
            openWindow("Enter.fxml");
        });

        reloadTableButton.setOnAction(actionEvent -> {
            try {
                initialize();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        visualizationButton.setOnAction(actionEvent -> {
            openWindow("Visualization.fxml");
        });

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ContextMenu menu = createTableContextMenu();
        setTableContextItems(menu);
        tableView.setContextMenu(menu);

        objectToServer.setName("show");
        objectToServer.setUsername(USERNAME);
        objectToServer.setPassword(PASSWORD);
        Modules.sendObject(objectToServer);
        objectToClient = readMessage();
        array = objectToClient.array;

        ObservableList<Dragon> obsObj = FXCollections.observableList(array);
        tableView.setItems(obsObj);

        if (!isTableCreated) {
            tableView.getColumns().clear();

            TableColumn<Dragon, String> nameColumn = new TableColumn<>("name");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            tableView.getColumns().add(nameColumn);

            TableColumn<Dragon, Float> xCoordColumn = new TableColumn<>("coordx");
            xCoordColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getCoordinates().getX()).asObject());
            tableView.getColumns().add(xCoordColumn);

            TableColumn<Dragon, Float> yCoordColumn = new TableColumn<>("coordy");
            yCoordColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getCoordinates().getY()).asObject());
            tableView.getColumns().add(yCoordColumn);

            TableColumn<Dragon, ZonedDateTime> creationDate = new TableColumn<>("creationdate");
            creationDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreationDate()));
            tableView.getColumns().add(creationDate);

            TableColumn<Dragon, Integer> age = new TableColumn<>("age");
            age.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
            tableView.getColumns().add(age);

            TableColumn<Dragon, String> description = new TableColumn<>("description");
            description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            tableView.getColumns().add(description);

            TableColumn<Dragon, Integer> wingspan = new TableColumn<>("wingspan");
            wingspan.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWingspan()).asObject());
            tableView.getColumns().add(wingspan);

            TableColumn<Dragon, DragonCharacter> character = new TableColumn<>("character");
            character.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCharacter()));
            tableView.getColumns().add(character);

            TableColumn<Dragon, Integer> depth = new TableColumn<>("depth");
            depth.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCave().getDepth()).asObject());
            tableView.getColumns().add(depth);

            TableColumn<Dragon, Float> numberoftreasures = new TableColumn<>("numberoftreasures");
            numberoftreasures.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getCoordinates().getY()).asObject());
            tableView.getColumns().add(numberoftreasures);

            TableColumn<Dragon, String> ownerColumn = new TableColumn<>("username");
            ownerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            tableView.getColumns().add(ownerColumn);

            TableColumn<Dragon, Integer> idColumn = new TableColumn<>("id");
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            tableView.getColumns().add(idColumn);

            isTableCreated = true;
        }
    }

    protected void setTableContextItems(ContextMenu menu) {
        MenuItem info = menu.getItems().get(0);
        MenuItem filterBy = menu.getItems().get(1);
        MenuItem add = menu.getItems().get(2);
        MenuItem updateById = menu.getItems().get(3);
        MenuItem clearMy = menu.getItems().get(4);
        MenuItem removeBy = menu.getItems().get(5);


        info.setOnAction(actionEvent -> {
            try {
                ObjectToServer objectToServer = new ObjectToServer();
                objectToServer.setName("info");
                objectToServer.setUsername(USERNAME);
                objectToServer.setPassword(PASSWORD);

                try {
                    Modules.sendObject(objectToServer);
                    Alert jopa = new Alert(Alert.AlertType.CONFIRMATION);
                    jopa.setTitle(resourceBundle.getString("CollectionInfo"));
                    String str = readMessage().message;
                    System.out.println(str);
                    String[] Array = str.split(",");
                    jopa.setHeaderText(resourceBundle.getString("collectType") + "\n" +
                            resourceBundle.getString("collectSize") + Array[1] + "\n" +
                            resourceBundle.getString("colletcTime") + Array[0]);
                    jopa.setResizable(false);
                    jopa.showAndWait();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        add.setOnAction(actionEvent -> {
            try {
                Stage dialogStage = new Stage();
                dialogStage.setTitle("createTitleText");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(Creator.stage);

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Add.fxml"));
                dialogStage.setScene(new Scene(loader.load(), 600, 400));
                dialogStage.setTitle(resourceBundle.getString("Add.fxml"));
                dialogStage.setResizable(false);
                dialogStage.show();

                addController = loader.getController();

                addController.getAddButton().setOnAction(actionEvent1 -> {
                    Dragon dragon = new Dragon();
                    dragon.setId(0);
                    dragon.setName(addController.getName());
                    Coordinates coord = new Coordinates();
                    coord.setX(Long.parseLong(addController.getCoordX()));
                    coord.setY(Float.parseFloat(addController.getCoordY()));
                    dragon.setCoordinates(coord);
                    dragon.setCreationDate(ZonedDateTime.now());
                    dragon.setAge(Integer.parseInt(addController.getAge()));
                    dragon.setDescription(addController.getDescription());
                    dragon.setWingspan(Integer.parseInt(addController.getWingspan()));
                    dragon.setCharacter(DragonCharacter.valueOf(addController.getCharacter()));
                    DragonCave cave = new DragonCave();
                    cave.setDepth(Integer.parseInt(addController.getDepth()));
                    cave.setNumberOfTreasures(Float.parseFloat(addController.getTreasures()));
                    dragon.setCave(cave);
                    dragon.setUsername(USERNAME);

                    ObjectToServer objectToServer = new ObjectToServer();
                    objectToServer.setName("add");
                    objectToServer.setObject(dragon);
                    objectToServer.setUsername(USERNAME);
                    objectToServer.setPassword(PASSWORD);
                    try {
                        Modules.sendObject(objectToServer);
                        System.out.println(readMessage().message);
                        dialogStage.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            openWindow("Table.fxml");
            try {
                initialize();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        filterBy.setOnAction(actionEvent -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("filterTitleText");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Creator.stage);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Filter.fxml"));
            try {
                dialogStage.setScene(new Scene(fxmlLoader.load(), 600, 400));
                filterController = fxmlLoader.getController();

                tableFilter = new TableFilter(filterController.getFiltertype(), filterController.getSelectcolumn(), filterController.getAimvalue());

                for (MenuItem item : filterController.getSelectcolumn().getItems()) {
                    item.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            filterController.getSelectcolumn().setText(item.getText());
                            tableFilter.setChoice(item.getId());
                        }
                    });
                }

                for (MenuItem item : filterController.getFiltertype().getItems()) {
                    item.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            filterController.getFiltertype().setText(item.getText());
                            tableFilter.setPredicate(item.getId());
                        }
                    });
                }

                filterController.getFilterbutton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if ((tableFilter.getUserChoice() != null)) {
                            try {
                                ObservableList<Dragon> sorted = FXCollections.observableList(tableFilter.filterByUserChoice(tableView, tableFilter.getUserChoice()));
                                tableView.setItems(sorted);
                                dialogStage.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                });
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            dialogStage.show();
        });

        updateById.setOnAction(actionEvent -> {
            try {
                Stage dialogStage = new Stage();
                dialogStage.setTitle("createTitleText");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(Creator.stage);

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Update.fxml"));
                dialogStage.setScene(new Scene(loader.load(), 600, 400));
                dialogStage.setTitle("Update.fxml");
                dialogStage.setResizable(false);
                dialogStage.show();

                updateController = loader.getController();

                updateController.getUpdateButton().setOnAction(actionEvent1 -> {


                    Dragon dragon = new Dragon();
                    dragon.setId(Integer.parseInt(updateController.getId()));
                    dragon.setName(updateController.getName());
                    Coordinates coord = new Coordinates();
                    coord.setX(Long.parseLong(updateController.getCoordX()));
                    coord.setY(Float.parseFloat(updateController.getCoordY()));
                    dragon.setCoordinates(coord);
                    dragon.setCreationDate(ZonedDateTime.now());
                    dragon.setAge(Integer.parseInt(updateController.getAge()));
                    dragon.setDescription(updateController.getDescription());
                    dragon.setWingspan(Integer.parseInt(updateController.getWingspan()));
                    dragon.setCharacter(DragonCharacter.valueOf(updateController.getCharacter()));
                    DragonCave cave = new DragonCave();
                    cave.setDepth(Integer.parseInt(updateController.getDepth()));
                    cave.setNumberOfTreasures(Float.parseFloat(updateController.getTreasures()));
                    dragon.setCave(cave);
                    dragon.setUsername(USERNAME);

                    ObjectToServer objectToServer = new ObjectToServer();
                    objectToServer.setName("update_id");
                    objectToServer.setObject(dragon);
                    objectToServer.setUsername(USERNAME);
                    objectToServer.setPassword(PASSWORD);
                    try {
                        Modules.sendObject(objectToServer);
                        System.out.println(readMessage().message);
                        dialogStage.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshTable();
            openWindow("Table.fxml");
        });

        clearMy.setOnAction(actionEvent -> {
            try {
                ObjectToServer objectToServer = new ObjectToServer();
                objectToServer.setName("clear");
                objectToServer.setUsername(USERNAME);
                objectToServer.setPassword(PASSWORD);

                try {
                    Modules.sendObject(objectToServer);
                    System.out.println(readMessage().message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        removeBy.setOnAction(actionEvent -> {
            Dragon selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null){
                ObjectToServer objectToServer = new ObjectToServer();
                objectToServer.setName("remove_by_id");
                objectToServer.setObject(selected.getId());
                objectToServer.setUsername(USERNAME);
                objectToServer.setPassword(PASSWORD);

                try{
                    Modules.sendObject(objectToServer);
                    String str = readMessage().message;
                    if ((str.equals("{ERROR}: ЭЛЕМЕНТА С ТАКИМ id НЕ СУЩЕСТВУЕТ")) || (str.equals("это не ваш элемент, вы не можете его удалить. "))) {
                        Alert jopa = new Alert(Alert.AlertType.CONFIRMATION);
                        jopa.setResizable(false);
                        jopa.setHeaderText("onlyYours");
                        jopa.showAndWait();
                    }
                    //jopa.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

