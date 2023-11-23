package com.ovapp;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class Controller  {

    @FXML
    private Label departurelabel;

    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private String Departure;

    @FXML
    private String Arrival;

    @FXML
    private ComboBox<String> departureCityComboBox;

    @FXML
    private ComboBox<String> arrivalCityComboBox;

    @FXML
    protected void onGOClick() {
        Departure = departureCityComboBox.getValue();
        Arrival = arrivalCityComboBox.getValue();
        departurelabel.setText(String.format("Vertrek station: %s naar %s", Departure, Arrival));
    }

    public void initialize() {
        List<String> tijden = getTijden();
        departureComboBox.getItems().addAll(tijden);

        List<String> steden = getSteden();
        departureCityComboBox.getItems().addAll(steden);
        arrivalCityComboBox.getItems().addAll(steden);
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