package com.ovapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import java.util.*;
import java.text.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;


public class Controller {
    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private ComboBox<String> arrivalComboBox;
    @FXML
    private ComboBox<String> transportComboBox;
    @FXML
    private Label routeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Tooltip transportTooltip;
    @FXML
    private Spinner<Integer> departureTimeHours;
    @FXML
    private Spinner<Integer> departureTimeMinutes;
    @FXML
    protected void onPlanButtonClick() {
        String departure = departureComboBox.getValue();
        String arrival = arrivalComboBox.getValue();
        String transport = transportComboBox.getValue();
        int departureHours = departureTimeHours.getValue();
        int departureMinutes = departureTimeMinutes.getValue();
        ArrayList<String> departureTime = determineDepartureTime(transport, departureHours, departureMinutes);
        if(departure == null || arrival == null || transport == null) {
            routeLabel.setText("Selecteer alstublieft een vertrekplaats, aankomstplaats en vervoermiddel.");
        } else if (departure.equals(arrival)){
            routeLabel.setText("De vertrekplaats en aankomstplaats kunnen niet hetzelfde zijn.");
        } else {
            routeLabel.setText(String.format("De reis van %s naar %s met de %s om %s uur.", departure, arrival, transport.toLowerCase(), departureTime.get(0)));
        }
        transportTooltip.setText("Klik om uw vervoermiddel te selecteren");
    }

    public void initialize() {
        List<String> cities = getCities();
        ObservableList<String> transport = getTransport();
        departureComboBox.getItems().addAll(cities);
        arrivalComboBox.getItems().addAll(cities);
        transportComboBox.getItems().addAll(transport);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            updateClock();
            updateDate();
            }
        },0,1000);
    }
    private void updateClock() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
        String formattedTime = dateFormat.format(new Date());

        Platform.runLater(() -> {
            clockLabel.setText(formattedTime);
        });
    }
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(new Date());

        Platform.runLater(() -> {
            dateLabel.setText(formattedDate);
        });
    }

    private List<String> getTimes() {
        List<String> departureTimes = new ArrayList<>();
        for (int uur = 0; uur <= 23; uur++) {
            for (int minuut = 0; minuut <= 59; minuut += 15) {
                departureTimes.add(String.format("%02d:%02d", uur, minuut));
            }
        }
        return departureTimes;
    }

    private ArrayList<String> determineDepartureTime(String transport, int departureHours, int departureMinutes){
        List<Integer> numbersTrain = Arrays.asList(0, 15, 30, 45, 60);
        int trainListSpot = 0;
        if (transport.equals("Trein")){
            if (departureMinutes >= 45){
                departureHours += 1;
            }
            int trainDistance = 100;
            for(int i=0; i < numbersTrain.size(); i++){
                int closestTrainDistance = Math.abs(numbersTrain.get(i) - departureMinutes);
                if (closestTrainDistance < trainDistance){
                    if (numbersTrain.get(i) > departureMinutes) {
                        trainListSpot = i;
                        trainDistance = closestTrainDistance;
                    }
                }
            }
        }
        int definitiveDepartureMinutes = numbersTrain.get(trainListSpot);
        if(definitiveDepartureMinutes == 60){
            definitiveDepartureMinutes = 0;
        }
        ArrayList<String> departureTimes = new ArrayList<>();
        departureTimes.add(String.format("%02d:%02d", departureHours, definitiveDepartureMinutes));
        return departureTimes;
    }

    private List<String> getCities() {
        return Arrays.asList("Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
    }

    private ObservableList<String> getTransport(){
        return FXCollections.observableArrayList("Trein", "Bus");
    }
}