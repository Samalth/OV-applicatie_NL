package com.ovapp;

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
    private ComboBox<String> vertrekComboBox;
    @FXML
    private ComboBox<String> bestemmingComboBox;
    @FXML
    private String Vertrek;
    @FXML
    private String Bestemming;
    @FXML
    private Label routeLabel;
    @FXML
    private Label datumLabel;
    @FXML
    private Label klokLabel;
    @FXML
    protected void onPlanButtonClick() {
        Vertrek = vertrekComboBox.getValue();
        Bestemming = bestemmingComboBox.getValue();
        routeLabel.setText(String.format("De reis van %s naar %s", Vertrek, Bestemming));
    }

    public void initialize() {

        List<String> steden = getSteden();
        vertrekComboBox.getItems().addAll(steden);
        bestemmingComboBox.getItems().addAll(steden);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateKlok();
                updateDatum();
            }
        },0,1000);
    }
    private void updateKlok() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
        String formattedTime = dateFormat.format(new Date());

        Platform.runLater(() -> {
            klokLabel.setText(formattedTime);
        });
    }
    private void updateDatum() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(new Date());

        Platform.runLater(() -> {
            datumLabel.setText(formattedDate);
        });
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