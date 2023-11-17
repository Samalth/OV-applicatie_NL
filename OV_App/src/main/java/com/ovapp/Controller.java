package com.ovapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Voorbeeld");
    }

    @FXML
    protected <T> void populateChoicebox() {
        choiceboxDepart.ObservableList<T> items = FXCollections.observableArrayList();
    }
}