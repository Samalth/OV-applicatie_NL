package com.ovapp;
import java.util.*;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.text.*;
public class Controller  {

    @FXML
    private Button logoutButton;

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
    private Label datumLabel;
    @FXML
    private Label klokLabel;


    public void initialize() {
        List<String> tijden = getTijden();
        departureComboBox.getItems().addAll(tijden);

        List<String> steden = getSteden();
        departureCityComboBox.getItems().addAll(steden);
        arrivalCityComboBox.getItems().addAll(steden);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateKlok();
                updateDatum();
            }
        }, 0, 1000);
    }

    public void onLogoutButtonClick() {
        openOVappinlog();
    }

    private void openOVappinlog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OVapp_inlog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Inlogscherm");
            stage.show();

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGOClick() {
        Departure = departureCityComboBox.getValue();
        Arrival = arrivalCityComboBox.getValue();
        departurelabel.setText(String.format("Vertrek station: %s naar %s", Departure, Arrival));
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