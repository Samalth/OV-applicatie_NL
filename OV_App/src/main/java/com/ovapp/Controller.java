package com.ovapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller  {
    private final Data data = new Data();

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
    private Tooltip clockLabelToolTip;
    @FXML
    private Tooltip departureLabelToolTip;
    @FXML
    private Tooltip logInButtonToolTip;
    @FXML
    private Tooltip GOButtonToolTip;
    @FXML
    private Tooltip departureCityComboBoxToolTip;
    @FXML
    private Tooltip arrivalCityComboBoxToolTip;
    @FXML
    private Tooltip departureDatePickerTooltip;
    @FXML
    private Tooltip departureTimeHoursToolTip;
    @FXML
    private Tooltip departureTimeMinutesToolTip;
    @FXML
    private Tooltip modeToolTip;
    @FXML
    private Tooltip transportComboBoxToolTip;
    @FXML
    private Tooltip dateLabelToolTip;

    @FXML
    private Button logInButton;
    @FXML
    private Button GOButton;

    @FXML
    private Button switchButton;

    @FXML
    private Label departureLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;

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
    private ImageView imgMode;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private LocalDate DepartureDate;
	@FXML
    private VBox parent;

    private Train train = new Train("Trein", Arrays.asList(0, 15, 30, 45, 60));
    private Bus bus = new Bus("Bus", Arrays.asList(25, 55, 85));
    private ResourceBundle bundle;

    public void initialize() {
        ObservableList<String> transport = getTransport();
        List<String> city = getCity();
        departureCityComboBox.getItems().addAll(city);
        arrivalCityComboBox.getItems().addAll(city);
        transportComboBox.getItems().addAll(transport);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
                updateDate();
            }
        }, 0, 1000);

        switchLanguage("Nederlands");
        setLightMode();
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
    public void onSwitchButtonClick(ActionEvent actionEvent) {
        String temp = departureCityComboBox.getValue();
        departureCityComboBox.setValue(arrivalCityComboBox.getValue());
        arrivalCityComboBox.setValue(temp);
    }

    @FXML
    protected void onGOClick()
    { if (switchButton.isPressed()) {
        String temp = DepartureCity;
        DepartureCity = ArrivalCity;
        ArrivalCity = temp;
    }
        DepartureCity = departureCityComboBox.getValue();
        ArrivalCity = arrivalCityComboBox.getValue();
        DepartureDate = departureDatePicker.getValue();
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
            departureLabel.setText("Selecteer alstublieft een vervoermiddel.");
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("      HH:mm");
        String formattedTime = dateFormat.format(new Date());

        Platform.runLater(() -> clockLabel.setText(formattedTime));
    }

    private List<String> getLanguages() {
        return Arrays.asList("Nederlands", "English", "Deutsch");
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

    public void switchLanguage(String newLanguage) {
        Locale locale = new Locale(newLanguage);
        bundle = ResourceBundle.getBundle("Messages", locale);

        ArrivalText.setText(bundle.getString("Destinationtxt"));
        DepartureDateText.setText(bundle.getString("DepartureDatetxt"));
        DepartureText.setText(bundle.getString("Departuretxt"));
        DepartureTimeText.setText(bundle.getString("DepartureTimetxt"));
        MeansOfTransportText.setText(bundle.getString("MeansOfTransporttxt"));

        logInButton.setText(bundle.getString("LogInButtontxt"));
        GOButton.setText(bundle.getString("RouteButtontxt"));

        arrivalCityComboBox.setPromptText(bundle.getString("ArrivalComboBoxPromt"));
        departureCityComboBox.setPromptText(bundle.getString("DepartureComboBoxPromt"));
        departureDatePicker.setPromptText(bundle.getString("DepartureDatePickerPrompt"));
        transportComboBox.setPromptText(bundle.getString("MeansOfTransportComboBoxPromt"));

        arrivalCityComboBoxToolTip.setText(bundle.getString("ArrivalCityComboBoxToolTiptxt"));
        clockLabelToolTip.setText(bundle.getString("ClockLabelToolTiptxt"));
        departureLabelToolTip.setText(bundle.getString("DepartureLabelToolTiptxt"));
        logInButtonToolTip.setText(bundle.getString("LogInButtonToolTiptxt"));
        GOButtonToolTip.setText(bundle.getString("GOButtonToolTiptxt"));
        departureCityComboBoxToolTip.setText(bundle.getString("DepartureCityComboBoxToolTiptxt"));
        departureDatePickerTooltip.setText(bundle.getString("DepartureDatePickerTooltiptxt"));
        departureTimeHoursToolTip.setText(bundle.getString("DepartureTimeHoursToolTiptxt"));
        departureTimeMinutesToolTip.setText(bundle.getString("DepartureTimeMinutesToolTiptxt"));
        modeToolTip.setText(bundle.getString("ModeToolTiptxt"));
        transportComboBoxToolTip.setText(bundle.getString("TransportComboBoxToolTiptxt"));
        dateLabelToolTip.setText(bundle.getString("DateLabelToolTiptxt"));
    }

    private boolean isLightMode=true ;


    public void onChangeModeClick(ActionEvent event){
        isLightMode = !isLightMode;

        parent.getStylesheets().remove("darkmode.css");
        parent.getStylesheets().remove("lightmode.css");

        if (isLightMode){
            setLightMode();
        }
        else {
            setDarkMode();
        }

    }
    private void setLightMode () {
        parent.getStylesheets().remove ("darkmode.css");
        parent.getStylesheets().add ("lightmode.css");
        Image image = new Image("moon.png");
        imgMode.setImage(image);
    }

    private void setDarkMode (){
        parent.getStylesheets().remove ("lightmode.css");
        parent.getStylesheets().add ("darkmode.css");
        Image image = new Image ("sun.png");
        imgMode.setImage(image);
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd-MM-yyyy");
        String formattedDate = dateFormat.format(new Date());

        Platform.runLater(() -> {
           dateLabel.setText(formattedDate);
        });
    }

    private List<String> getCity() {
	    try {
		    return data.returnCity();
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }

     private ObservableList<String> getTransport() {
         return FXCollections.observableArrayList(train.getTransportName(), bus.getTransportName());
     }
}
