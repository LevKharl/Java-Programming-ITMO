package com.example.lab8client;

import Animation.CircleAnimation;
import DragonStuff.Coordinates;
import DragonStuff.Dragon;
import DragonStuff.DragonCave;
import DragonStuff.DragonCharacter;
import ObjectToSend.ObjectToServer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.lab8client.Creator.stage;
import static com.example.lab8client.CurrLocale.locale;
import static com.example.lab8client.Main.PASSWORD;
import static com.example.lab8client.Main.USERNAME;
import static com.example.lab8client.Modules.readMessage;
import static com.example.lab8client.TableController.objectToClient;
import static java.lang.Math.sqrt;
import static javafx.scene.paint.Color.*;

public class VisualizationController {

    @FXML
    private Button backToTableButton;

    @FXML
    private Button exitVisButton;

    @FXML
    private Group group;

    @FXML
    private Label ownerVisLabel;

    @FXML
    private Button reloadVisButton;

    static ArrayList<Dragon> array;
    static ArrayList<Dragon> arrayNew;
    public static Dragon dragon1;
    static ResourceBundle resourceBundle;
    static ContextMenu contextMenu;
    UpdateController updateController;

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

    protected void createVisualObjects() throws IOException, InterruptedException, ClassNotFoundException {
        group.getChildren().clear();
        ObjectToServer objectToServer = new ObjectToServer();
        objectToServer.setName("show");
        objectToServer.setUsername(USERNAME);
        objectToServer.setPassword(PASSWORD);
        try {
            Modules.sendObject(objectToServer);
            objectToClient = readMessage();
            array = objectToClient.array;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Dragon dragon : array) {
            Circle circle = new Circle(dragon.getCoordinates().getX(), dragon.getCoordinates().getY(), sqrt(dragon.getWingspan()));
            generateColor(circle, dragon.getUsername());
            circle.setStrokeWidth(0.3);
            String name = dragon.getName();
            Text text = new Text(name);
            text.setX(circle.getCenterX() - name.length() * 3.4);
            text.setY(circle.getCenterY() + 5);
            circle.setUserData(dragon);
            group.getChildren().addAll(circle, text);
            CircleAnimation animation = new CircleAnimation(circle);
            animation.playAnimation();

            setTableContextItems(circle);
            circle.setOnContextMenuRequested(contextMenuEvent -> {
                setTableContextItems(circle);
                contextMenu.show(circle, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            });
        }

    }

    public void generateColor(Circle circle, String user) {
        if (user.equals("2")) {
            circle.setFill(LIGHTGRAY);
            circle.setStroke(BLUE);
        }
        if (user.equals("11")) {
            circle.setFill(LIGHTPINK);
            circle.setStroke(BLUE);
        }
        if (user.equals("22")) {
            circle.setFill(LIGHTSALMON);
            circle.setStroke(BLUE);
        }
        if (user.equals("3")) {
            circle.setFill(LIGHTCORAL);
            circle.setStroke(BLUE);
        }
        if (user.equals("1")) {
            circle.setFill(LIGHTSEAGREEN);
            circle.setStroke(DARKBLUE);
        }
    }

    public static ContextMenu createTableContextMenu() {
        ContextMenu circleContextMenu = new ContextMenu();

        circleContextMenu.getItems().addAll(
                new MenuItem(resourceBundle.getString("ObjectInfo")),
                new MenuItem(resourceBundle.getString("UpdateById")),
                new MenuItem(resourceBundle.getString("DeleteThis"))
        );
        return circleContextMenu;
    }

    protected void setTableContextItems(Circle circle) {
        ContextMenu menu = createTableContextMenu();
        MenuItem info = contextMenu.getItems().get(0);
        MenuItem updateById = contextMenu.getItems().get(1);
        MenuItem delete = contextMenu.getItems().get(2);

        info.setOnAction(actionEvent -> {
            dragon1 = (Dragon) circle.getUserData();
            Alert jopa = new Alert(Alert.AlertType.CONFIRMATION);
            jopa.setTitle(resourceBundle.getString("ObjectInfo"));
            String str = resourceBundle.getString("id") + dragon1.getId() + "\n" +
                    resourceBundle.getString("name") + dragon1.getName() + "\n" +
                    resourceBundle.getString("coordx") + dragon1.getCoordinates().getX() + "\n" +
                    resourceBundle.getString("coordy") + dragon1.getCoordinates().getY() + "\n" +
                    resourceBundle.getString("creationdate") + dragon1.getCreationDate() + "\n" +
                    resourceBundle.getString("age") + dragon1.getAge() + "\n" +
                    resourceBundle.getString("description") + dragon1.getDescription() + "\n" +
                    resourceBundle.getString("wingspan") + dragon1.getWingspan() + "\n" +
                    resourceBundle.getString("character") + dragon1.getCharacter() + "\n" +
                    resourceBundle.getString("depth") + dragon1.getCave().getDepth() + "\n" +
                    resourceBundle.getString("numberoftreasures") + dragon1.getCave().getNumberOfTreasures() + "\n";
            jopa.setHeaderText(str);
            jopa.setResizable(false);
            jopa.showAndWait();
        });

        updateById.setOnAction(actionEvent -> {
            try {
                Stage dialogStage = new Stage();
                dialogStage.setTitle("createTitleText");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(Creator.stage);

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Update.fxml"));
                dialogStage.setScene(new Scene(loader.load(), 600, 400));
                dialogStage.setTitle("Update.fxml" + " view");
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
            openWindow("Table.fxml");
        });

        delete.setOnAction(actionEvent -> {
            try {
                dragon1 = (Dragon) circle.getUserData();
                ObjectToServer objectToServer = new ObjectToServer();
                objectToServer.setName("remove_by_id");
                objectToServer.setObject(dragon1.getId());
                objectToServer.setUsername(USERNAME);
                objectToServer.setPassword(PASSWORD);
                try {
                    Modules.sendObject(objectToServer);
                    Alert jopa = new Alert(Alert.AlertType.CONFIRMATION);
                    jopa.setTitle("Delete");
                    String str = readMessage().message;
                    jopa.setHeaderText(str);
                    jopa.setResizable(false);
                    if (str.equals("Вы можете удалять только свои объекты!"))
                        jopa.showAndWait();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                createVisualObjects();
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void initialize() throws IOException, ClassNotFoundException, InterruptedException {
        resourceBundle = ResourceBundle.getBundle("com.example.lab8client.Locale", locale);
        ownerVisLabel.setText(resourceBundle.getString("ownerVisLabel") + USERNAME);
        backToTableButton.setText(resourceBundle.getString("backToTableButton"));
        exitVisButton.setText(resourceBundle.getString("exitVisButton"));
        reloadVisButton.setText(resourceBundle.getString("reloadVisButton"));

        contextMenu = createTableContextMenu();

        backToTableButton.setOnAction(event -> {
            openWindow("Table.fxml");
        });
        exitVisButton.setOnAction(actionEvent -> {
            openWindow("Enter.fxml");
        });

        createVisualObjects();

        reloadVisButton.setOnAction(actionEvent -> {
            try {
                createVisualObjects();
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Timeline refresh = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            ObjectToServer objectToServer = new ObjectToServer();
                            objectToServer.setName("show");
                            objectToServer.setUsername(USERNAME);
                            objectToServer.setPassword(PASSWORD);
                            try {
                                Modules.sendObject(objectToServer);
                                objectToClient = readMessage();
                                arrayNew = objectToClient.array;
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            if (array != arrayNew) {
                                try {
                                    createVisualObjects();
                                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
        refresh.setOnFinished(actionEvent -> {
            refresh.play();
        });
    }
}
