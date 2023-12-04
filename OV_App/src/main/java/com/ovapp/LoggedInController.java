package com.ovapp;
import java.time.LocalDate;
import java.util.*;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.text.*;
import java.time.format.DateTimeFormatter;
public class LoggedInController  {

    @FXML
    private Button logOutButton;
    @FXML
    private Button GOButton;
    @FXML
    private Button showTravelHistoryButton;

    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private ComboBox<String> departureCityComboBox;
    @FXML
    private ComboBox<String> arrivalCityComboBox;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label departureLabel;
    @FXML
    private Label travelHistoryLabel;
    private boolean isHistoryVisible = false;

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

        travelHistoryLabel.setVisible(false);
    }

    public void onLogOutButtonClick() {
        openOvappLogOut();
    }

    private void openOvappLogOut() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OVapp_GUI.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) logOutButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Uitgelogd scherm");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void onShowTravelHistoryButtonClick() {
        isHistoryVisible = !isHistoryVisible;

        travelHistoryLabel.setVisible(isHistoryVisible);

        if (isHistoryVisible) {
            System.out.println("Reisgeschiedenis zichtbaar");
        } else {
            System.out.println("Reisgeschiedenis verborgen");
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

    private List<String> getCity() {
        return Arrays.asList("Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
    }
}