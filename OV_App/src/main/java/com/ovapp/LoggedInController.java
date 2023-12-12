package com.ovapp;
import java.time.LocalDate;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.*;
import java.time.format.DateTimeFormatter;
public class LoggedInController  {

    @FXML
    private Text DepartureText;
    @FXML
    private Text ArrivalText;
    @FXML
    private Text MeansOfTransportText;
    @FXML
    private Text DepartureDateText;
    @FXML
    private Text DepartureTimeText;

    @FXML
    private Button logOutButton;
    @FXML
    private Button GOButton;
    @FXML
    private Button showTravelHistoryButton;
    @FXML
    private Button switchButton;
    @FXML
    private ComboBox<String> departureCityComboBox;
    @FXML
    private ComboBox<String> arrivalCityComboBox;
    @FXML
    private ComboBox<String> transportComboBox;

    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private Spinner<Integer> departureTimeHours;
    @FXML
    private Spinner<Integer> departureTimeMinutes;

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
    private LocalDate DepartureDate;
    private String transport;

    private List<String> travelHistory = new ArrayList<>();
    private Train train = new Train("Trein", Arrays.asList(0, 15, 30, 45, 60));
    private Bus bus = new Bus("Bus", Arrays.asList(25, 55, 85));
    private ResourceBundle bundle;


    public void initialize() {
        ObservableList<String> transport = getTransport();
        List<String> steden = getCity();
        departureCityComboBox.getItems().addAll(steden);
        arrivalCityComboBox.getItems().addAll(steden);
        transportComboBox.getItems().addAll(transport);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
                updateDate();
            }
        }, 0, 1000);

        travelHistoryLabel.setVisible(false);
        switchLanguage("Nederlands");
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
    public void onSwitchButtonClick(ActionEvent actionEvent) {
        String temp = departureCityComboBox.getValue();
        departureCityComboBox.setValue(arrivalCityComboBox.getValue());
        arrivalCityComboBox.setValue(temp);
    }

    @FXML
    protected void onGOClick() {
        DepartureCity = departureCityComboBox.getValue();
        ArrivalCity = arrivalCityComboBox.getValue();
        DepartureDate = departureDatePicker.getValue();
        transport = transportComboBox.getValue();
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
        if(DepartureCity == null || ArrivalCity == null || transport == null) {
            departureLabel.setText("Selecteer alstublieft een vertrekplaats, aankomstplaats en vervoermiddel.");
        } else if (DepartureCity.equals(ArrivalCity)){
            departureLabel.setText("De vertrekplaats en aankomstplaats kunnen niet hetzelfde zijn.");
        } else {
            LocalDate currentDate = LocalDate.now();
            String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String formattedCurrentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

            String formattedDate = (DepartureDate != null)
                    ? DepartureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    : currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String departureLabelInfo = String.format(
                    "Van %s naar %s om %s met de %s op %s", DepartureCity, ArrivalCity, departureTime.get(0), transport.toLowerCase(), formattedDate);
            departureLabel.setText(departureLabelInfo);

            String travelHistoryInfo = String.format("Op %s om %s%nVan %s naar %s om %s met de %s op %s"
                    , formattedCurrentDate, formattedCurrentTime, DepartureCity
                    , ArrivalCity, departureTime.get(0), transport.toLowerCase(), formattedDate);
            updateTravelHistory(travelHistoryInfo);
        }
    }

    private List<String> getLanguages() {
        return Arrays.asList("Nederlands", "English", "Deutsch");
    }

    public void switchLanguage(String newLanguage) {
        Locale locale = new Locale(newLanguage);
        bundle = ResourceBundle.getBundle("Messages", locale);

        DepartureText.setText(bundle.getString("Departuretxt"));
        ArrivalText.setText(bundle.getString("Destinationtxt"));
        DepartureTimeText.setText(bundle.getString("DepartureTimetxt"));
        DepartureDateText.setText(bundle.getString("DepartureDatetxt"));
        MeansOfTransportText.setText(bundle.getString("MeansOfTransporttxt"));
        showTravelHistoryButton.setText(bundle.getString("showTravelHistoryButtontxt"));

        logOutButton.setText(bundle.getString("LogOutButtontxt"));
        GOButton.setText(bundle.getString("RouteButtontxt"));

        arrivalCityComboBox.setPromptText(bundle.getString("ArrivalComboBoxPromt"));
        departureCityComboBox.setPromptText(bundle.getString("DepartureComboBoxPromt"));
        departureDatePicker.setPromptText(bundle.getString("DepartureDatePickerPrompt"));
        transportComboBox.setPromptText(bundle.getString("MeansOfTransportComboBoxPromt"));
    }

    public void onDuLanguageButtonClick(){
        switchLanguage("Deutsch");
    }
    public void onNlLanguageButtonClick(){
        switchLanguage("Nederlands");
    }
    public void onEnLanguageButtonClick(){
        switchLanguage("English");
    }

    private void updateTravelHistory(String travelInfo) {
        travelHistory.add(travelInfo);

        StringBuilder historyText = new StringBuilder();
        for (String entry : travelHistory) {
            historyText.append(entry).append("\n\n");
        }
        travelHistoryLabel.setText(historyText.toString());
    }

    private void updateClock() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("      HH:mm");
        String formattedTime = dateFormat.format(new Date());

        Platform.runLater(() -> {
            clockLabel.setText(formattedTime);
        });
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd-MM-yyyy");
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
        return Arrays.asList(
                "Amersfoort", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
    }
    private ObservableList<String> getTransport() {
        return FXCollections.observableArrayList(train.getTransportName(), bus.getTransportName());
    }
}