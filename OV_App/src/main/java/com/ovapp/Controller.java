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
    private final Bus bus = new Bus("Bus", Arrays.asList(25, 55, 85));
    private final Train train = new Train("Trein", Arrays.asList(0, 15, 30, 45, 60));

    @FXML
    protected void onPlanButtonClick() {
        String departure = departureComboBox.getValue();
        String arrival = arrivalComboBox.getValue();
        String transport = transportComboBox.getValue();
        int departureHours = departureTimeHours.getValue();
        int departureMinutes = departureTimeMinutes.getValue();
        ArrayList<String> departureTime = new ArrayList<>();
        try {
            if (transport.equals("Trein")) {
                departureTime = train.getDepartureTime(train.getTransportSchedule(), departureHours, departureMinutes);
            } else if (transport.equals("Bus")) {
                departureTime = bus.getDepartureTime(bus.getTransportSchedule(), departureHours, departureMinutes);
            }
        }catch (NullPointerException e){
        }
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
    private List<String> getCities() {
        return Arrays.asList("Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
    }

    private ObservableList<String> getTransport(){
        return FXCollections.observableArrayList(train.getTransportName(), bus.getTransportName());
    }
}