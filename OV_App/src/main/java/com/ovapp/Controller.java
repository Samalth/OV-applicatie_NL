package com.ovapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import java.util.*;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Voorbeeld");
    }

    @FXML
    private ComboBox<String> vertrekComboBox;

    @FXML
    private ComboBox<String> bestemmingComboBox;

    public void initialize() {

        List<String> steden = getSteden();
        vertrekComboBox.getItems().addAll(steden);
        bestemmingComboBox.getItems().addAll(steden);
    }
    private List<String> getTijden() {
        List<String> tijden = new ArrayList<>();
        for (int uur = 0; uur <= 23; uur++) {
            for (int minuut = 0; minuut <= 59; minuut += 15) {
                tijden.add(String.format("%02d:%02d", uur, minuut));
            }
        }
        return tijden;
    }

    private List<String> getSteden() {
        return Arrays.asList("Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
    }
}