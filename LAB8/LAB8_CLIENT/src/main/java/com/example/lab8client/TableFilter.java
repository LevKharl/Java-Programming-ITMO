package com.example.lab8client;

import DragonStuff.Dragon;
import DragonStuff.DragonCharacter;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableFilter {
    private MenuButton menuButton;
    private MenuButton filterPredicate;
    private TextField textField;

    private String userChoice;
    private int predicate;

    public TableFilter(MenuButton menuButton, MenuButton filterPredicate, TextField textField) {
        this.menuButton = menuButton;
        this.filterPredicate = filterPredicate;
        this.textField = textField;
    }

    public TableFilter() {
    }

    public ArrayList<Dragon> filterByUserChoice(TableView<Dragon> tableView, String choice) {
        ArrayList<Dragon> result = new ArrayList<>();
        try {
            switch (choice) {
                case "id" -> result = filterTableById(tableView, getFilterPredicate());
                case "name" -> result = filterTableByName(tableView, getFilterPredicate());
                case "coordx" -> result = filterTableByCoordX(tableView, getFilterPredicate());
                case "coordy" -> result = filterTableByCoordY(tableView, getFilterPredicate());
                case "creationdate" -> result = filterTableByCreationDate(tableView, getFilterPredicate());
                case "age" -> result = filterTableByAge(tableView, getFilterPredicate());
                case "description" -> result = filterTableByDescription(tableView, getFilterPredicate());
                case "wingspan" -> result = filterTableByWingspan(tableView, getFilterPredicate());
                case "character" -> result = filterTableByCharacter(tableView, getFilterPredicate());
                case "depth" -> result = filterTableByDepth(tableView, getFilterPredicate());
                case "numberoftreasures" -> result = filterTableByTreasures(tableView, getFilterPredicate());
            }
            return result;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ArrayList<Dragon> filterTableById(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getId() < Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getId() > Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getId() == Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByName(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getName().compareTo(textField.getText()) < 0).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getName().compareTo(textField.getText()) > 0).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getName().equals(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByCoordX(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getX() < Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getX() > Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getX() == Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByCoordY(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getY() < Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getY() > Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCoordinates().getY() == Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByCreationDate(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCreationDate().compareTo(ChronoZonedDateTime.from(ZonedDateTime.parse(textField.getText()))) < 0).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCreationDate().compareTo(ChronoZonedDateTime.from(ZonedDateTime.parse(textField.getText()))) > 0).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCreationDate().compareTo(ChronoZonedDateTime.from(ZonedDateTime.parse(textField.getText()))) == 0).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByAge(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getAge() < Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getAge() > Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getAge() == Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByDescription(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
        switch (filterPredicate) {
            case 0 -> filtered = objects.stream().filter(dragon -> dragon.getDescription().compareTo(textField.getText()) < 0).collect(Collectors.toCollection(ArrayList::new));
            case 2 -> filtered = objects.stream().filter(dragon -> dragon.getDescription().compareTo(textField.getText()) > 0).collect(Collectors.toCollection(ArrayList::new));
            case 4 -> filtered = objects.stream().filter(dragon -> dragon.getDescription().equals(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
        }
        return filtered;
    }

    public ArrayList<Dragon> filterTableByWingspan(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getWingspan() < Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getWingspan() > Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getWingspan() == Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByCharacter(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCharacter().compareTo(DragonCharacter.valueOf(textField.getText())) < 0).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCharacter().compareTo(DragonCharacter.valueOf(textField.getText())) > 0).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCharacter().equals(DragonCharacter.valueOf(textField.getText()))).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByDepth(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getDepth() < Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getDepth() > Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getDepth() == Integer.parseInt(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public ArrayList<Dragon> filterTableByTreasures(TableView<Dragon> tableView, int filterPredicate) {
        List<Dragon> objects = tableView.getItems();
        ArrayList<Dragon> filtered = new ArrayList<>();
            switch (filterPredicate) {
                case 0 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getNumberOfTreasures() < Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 2 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getNumberOfTreasures() > Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
                case 4 -> filtered = objects.stream().filter(dragon -> dragon.getCave().getNumberOfTreasures() == Float.parseFloat(textField.getText())).collect(Collectors.toCollection(ArrayList::new));
            }
            return filtered;
    }

    public int getFilterPredicate() {
        return predicate;
    }

    public String getUserChoice() {
        return this.userChoice;
    }

    public void setPredicate(String predicate) {
        switch (predicate) {
            case "less" -> this.predicate = 0;
            case "more" -> this.predicate = 2;
            case "equals" -> this.predicate = 4;
        }
        System.out.println(this.predicate);
    }

    public void setChoice(String choice) {
        this.userChoice = choice;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
