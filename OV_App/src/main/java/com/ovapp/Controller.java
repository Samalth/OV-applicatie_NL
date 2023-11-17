package com.ovapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label departure;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Voorbeeld");
    }

    @FXML
    protected void populateChoicebox() {
        departure.ObservableList<T> items = FXCollections.observableArrayList();
    }
}