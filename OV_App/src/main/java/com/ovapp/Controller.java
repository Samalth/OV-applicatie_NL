package com.ovapp;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onGOClick() {
        welcomeText.setText("Just kidding, I like girls!");
    }

    @FXML
    private ComboBox<String> vertrekComboBox;

    @FXML
    private ComboBox<String> aankomstComboBox;

    @FXML
    private ComboBox<String> vertrekStadComboBox;

    @FXML
    private ComboBox<String> aankomstStadComboBox;


    public void initialize() {
        // Vul de ChoiceBox met tijden
        List<String> tijden = getTijden();
        vertrekComboBox.getItems().addAll(tijden);
        aankomstComboBox.getItems().addAll(tijden);

        List<String> steden = getSteden();
        vertrekStadComboBox.getItems().addAll(steden);
        aankomstStadComboBox.getItems().addAll(steden);
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