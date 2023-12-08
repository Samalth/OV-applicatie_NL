package com.ovapp;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.*;
public class Controller  {

    @FXML
    private Text VertrekText;
    @FXML
    private Text BestemmingText;
    @FXML
    private Text VervoermiddelText;
    @FXML
    private Text VertrekDatumText;
    @FXML
    private Text VertrekTijdText;


    @FXML
    private Button logInButton;
    @FXML
    private Button GOButton;

    @FXML
    private Button enLanguageButton;
    @FXML
    private Button nlLanguageButton;
    @FXML
    private Button duLanguageButton;


    @FXML
    private Label departureLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Spinner<Integer> departureTimeHours;
    @FXML
    private Spinner<Integer> departureTimeMinutes;
    @FXML
    private ComboBox<String> departureCityComboBox;
    @FXML
    private ComboBox<String> arrivalCityComboBox;
    @FXML
    private ComboBox<String> transportComboBox;

    @FXML
    private String DepartureCity;
    @FXML
    private String ArrivalCity;
    @FXML
    private LocalDate DepartureDate;
    private String transport;

    private Train train = new Train("Trein", Arrays.asList(0, 15, 30, 45, 60));
    private Bus bus = new Bus("Bus", Arrays.asList(25, 55, 85));

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
            String formattedDate = (DepartureDate != null)
                    ? DepartureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    : currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String departureLabelInfo = String.format(
                    "Van %s naar %s om %s met de %s op %s", DepartureCity, ArrivalCity, departureTime.get(0), transport.toLowerCase(), formattedDate);
            departureLabel.setText(departureLabelInfo);
        }
    }

    private void updateClock() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
            String formattedTime = dateFormat.format(new Date());

            Platform.runLater(() -> {
                clockLabel.setText(formattedTime);
            });
    }

    private List<String> getLanguages() {
        return Arrays.asList("Nederlands", "English", "Deutsch");
    }

    public void onDuLanguageButtonClick(){}
    public void onNlLanguageButtonClick(){}
    public void onEnLanguageButtonClick(){}

    public void switchLanguage(String newLanguage) {
//        Locale locale = new Locale(newLanguage); // For example: "en", "de", "nl"
//        ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);
//
//        // Change all the JavaFx elements which have text.
//        this.Vertrek = bundle.getString("department");
//        this.Bestemming = bundle.getString("destination");
//
//        this.Arrival.setText(this.Bestemming);
//        this.Departure.setText(this.Vertrek);
//
//        this.vertrekComboBox.setPromptText(bundle.getString("vertrekComboBoxPromt"));
//        this.bestemmingComboBox.setPromptText(bundle.getString("bestemmingComboBoxPromt"));
//        this.DepartureTime.setText(bundle.getString("DepartureTimetxt"));
    }

    public void switchLanguage(ActionEvent actionEvent) throws IOException {
//        String language = languageChoiceBox.getValue();
//        switchLanguage(language);
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(new Date());

        Platform.runLater(() -> {
           dateLabel.setText(formattedDate);
        });
    }

    private List<String> getCity() {
        return Arrays.asList(
                "Amersfoort Centraal Station", "Nieuwegein", "Amsterdam", "Den Haag", "Den Bosch", "Arnhem", "Utrecht", "IJsselstein");
     }

     private ObservableList<String> getTransport() {
         return FXCollections.observableArrayList(train.getTransportName(), bus.getTransportName());
    }
 }
