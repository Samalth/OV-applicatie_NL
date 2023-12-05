package com.ovapp;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.text.*;
import org.json.JSONObject;

public class Controller  {

    @FXML
    private Button logInButton;
    @FXML
    private Button GOButton;

    @FXML
    private Label departureLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private ComboBox<String> departureCityComboBox;
    @FXML
    private ComboBox<String> arrivalCityComboBox;

    @FXML
    private String DepartureCity;
    @FXML
    private String ArrivalCity;
    @FXML
    private String DepartureTime;
    @FXML
    private LocalDate DepartureDate;

    public void initialize() {
        List<String> tijden = getTime();
        departureComboBox.getItems().addAll(tijden);

        List<String> steden = getCity();
        departureCityComboBox.getItems().addAll(steden);
        arrivalCityComboBox.getItems().addAll(steden);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
                updateDate();
            }
        }, 0, 1000);
    }

    public void onLogInButtonClick() {
        openOvappLoggedIn();
    }

    private void openOvappLoggedIn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OVapp_LoggedIn.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) logInButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Ingelogd scherm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGOClick() {
        DepartureCity = departureCityComboBox.getValue();
        ArrivalCity = arrivalCityComboBox.getValue();
        DepartureTime = departureComboBox.getValue();
        DepartureDate = departureDatePicker.getValue();

        if (DepartureCity == null || ArrivalCity == null || DepartureTime == null) {
            departureLabel.setText("Vul eerst de vertrek- en bestemmingsstations, en selecteer een vertrektijd.");
        } else {
            String formattedDate = (DepartureDate != null) ? DepartureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            departureLabel.setText(String.format("Vertrek station: %s naar %s om %s op %s", DepartureCity, ArrivalCity, DepartureTime, formattedDate));
        }
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

    private List<String> getTime() {
        List<String> tijden = new ArrayList<>();
        for (int uur = 0; uur <= 23; uur++) {
            for (int minuut = 0; minuut <= 59; minuut += 15) {
                tijden.add(String.format("%02d:%02d", uur, minuut));
            }
        }
        return tijden;
    }


    private List<String> getCity(){
        return Arrays.asList("Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
     }
 }
