package com.ovapp;

import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.*;
import java.time.format.DateTimeFormatter;

public class LoggedInController {
	private final Data data = new Data();

	@FXML
	private Button logOutButton;
	@FXML
	private Button GOButton;
	@FXML
	private Button showTravelHistoryButton;
	@FXML
	private Button setFavourite;

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
	private Tooltip logOutButtonToolTip;
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
	private Tooltip travelHistoryLabelToolTip;
	@FXML
	private Tooltip dateLabelToolTip;
	@FXML
	private Tooltip showTravelHistoryButtonToolTip;

	@FXML
	private VBox parent;


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
	private ImageView imgMode;

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

	private List<String> favouriteList = new ArrayList<>();
	private Train train = new Train("Trein", Arrays.asList(0, 15, 30, 45, 60));
	private Bus bus = new Bus("Bus", Arrays.asList(25, 55, 85));
	private ResourceBundle bundle;
	private City currentCity;
	private boolean isLightMode = true;

	public ArrayList<City> getCities() {
		ArrayList<City> cities = new ArrayList<>();
		cities.add(new City("Amersfoort", Arrays.asList("liften", "geleidenstroken")));
		cities.add(new City("Amsterdam", Arrays.asList("liften", "geleidenstroken", "trapmarkeringen")));
		cities.add(new City("Arnhem", Arrays.asList("geleidenstroken", "trapmarkeringen")));
		cities.add(new City("Den Bosch", Arrays.asList("liften", "trapmarkeringen")));
		cities.add(new City("Den Haag", Arrays.asList("geleidenstroken", "het hele station is gelijkvloers")));
		cities.add(new City("IJsselstein", Arrays.asList("geleidenstroken", "het hele station is gelijkvloers")));
		cities.add(new City("Nieuwegein", Arrays.asList("liften", "geleidenstroken")));
		cities.add(new City("Utrecht", Arrays.asList("liften", "geleidenstroken", "trapmarkeringen")));

		return cities;
	}

	public void initialize() {
		ObservableList<String> transport = getTransport();
		List<String> steden = getStation();
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
		setLightMode();
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
		ArrayList<City> cities = getCities();
		String departureAmenities = "";
		String arrivalAmenities = "";
		try {
			List<String> amenities = determineAmenities(cities);
			departureAmenities = amenities.get(0);
			arrivalAmenities = amenities.get(1);
		} catch (NullPointerException e) {
			departureLabel.setText("Selecteer alstublieft een vertrekplaats, aankomstplaats en vervoermiddel.");
		}
		try {
			if (transport.equals("Trein")) {
				departureTime = train.getDepartureTime(train.getTransportSchedule(), departureHours, departureMinutes);
			} else if (transport.equals("Bus")) {
				departureTime = bus.getDepartureTime(bus.getTransportSchedule(), departureHours, departureMinutes);
			}
		} catch (NullPointerException e) {
		}
		if (DepartureCity == null || ArrivalCity == null || transport == null) {
			departureLabel.setText("Selecteer alstublieft een vertrekplaats, aankomstplaats en vervoermiddel.");
		} else if (DepartureCity.equals(ArrivalCity)) {
			departureLabel.setText("De vertrekplaats en aankomstplaats kunnen niet hetzelfde zijn.");
		} else {
			LocalDate currentDate = LocalDate.now();
			String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String formattedCurrentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

			String formattedDate = (DepartureDate != null)
					? DepartureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
					: currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String departureLabelInfo = String.format(
					"Van %s naar %s om %s met de %s op %s. De voorzieningen op station %s zijn %s " +
							"De voorzieningen op station %s zijn %s", DepartureCity, ArrivalCity
					, departureTime.get(0), transport.toLowerCase(), formattedDate,
					DepartureCity, departureAmenities, ArrivalCity, arrivalAmenities);
			departureLabel.setText(departureLabelInfo);
        }
	}

	private List<String> getLanguages() {
		return Arrays.asList("Nederlands", "English", "Deutsch");
	}

	public void switchLanguage(String newLanguage) {
		Locale locale = new Locale(newLanguage);
		bundle = ResourceBundle.getBundle("Messages", locale);

		ArrivalText.setText(bundle.getString("Destinationtxt"));
		DepartureText.setText(bundle.getString("Departuretxt"));
		DepartureDateText.setText(bundle.getString("DepartureDatetxt"));
		DepartureTimeText.setText(bundle.getString("DepartureTimetxt"));
		MeansOfTransportText.setText(bundle.getString("MeansOfTransporttxt"));

		GOButton.setText(bundle.getString("RouteButtontxt"));
		logOutButton.setText(bundle.getString("LogOutButtontxt"));
		showTravelHistoryButton.setText(bundle.getString("ShowTravelHistoryButtontxt"));

		arrivalCityComboBox.setPromptText(bundle.getString("ArrivalComboBoxPromt"));
		departureCityComboBox.setPromptText(bundle.getString("DepartureComboBoxPromt"));
		departureDatePicker.setPromptText(bundle.getString("DepartureDatePickerPrompt"));
		transportComboBox.setPromptText(bundle.getString("MeansOfTransportComboBoxPromt"));

		arrivalCityComboBoxToolTip.setText(bundle.getString("ArrivalCityComboBoxToolTiptxt"));
		clockLabelToolTip.setText(bundle.getString("ClockLabelToolTiptxt"));
		departureLabelToolTip.setText(bundle.getString("DepartureLabelToolTiptxt"));
		logOutButtonToolTip.setText(bundle.getString("LogOutButtonToolTiptxt"));
		GOButtonToolTip.setText(bundle.getString("GOButtonToolTiptxt"));
		departureCityComboBoxToolTip.setText(bundle.getString("DepartureCityComboBoxToolTiptxt"));
		departureDatePickerTooltip.setText(bundle.getString("DepartureDatePickerTooltiptxt"));
		departureTimeHoursToolTip.setText(bundle.getString("DepartureTimeHoursToolTiptxt"));
		departureTimeMinutesToolTip.setText(bundle.getString("DepartureTimeMinutesToolTiptxt"));
		modeToolTip.setText(bundle.getString("ModeToolTiptxt"));
		transportComboBoxToolTip.setText(bundle.getString("TransportComboBoxToolTiptxt"));
		travelHistoryLabelToolTip.setText(bundle.getString("TravelHistoryLabelToolTiptxt"));
		showTravelHistoryButtonToolTip.setText(bundle.getString("ShowTravelHistoryButtonToolTiptxt"));
		dateLabelToolTip.setText(bundle.getString("DateLabelToolTiptxt"));
	}

	public void onChangeModeClick(ActionEvent event) {
		isLightMode = !isLightMode;

		parent.getStylesheets().remove("darkmode.css");
		parent.getStylesheets().remove("lightmode.css");

		if (isLightMode) {
			setLightMode();
		} else {
			setDarkMode();
		}
	}

	private void setLightMode() {
		parent.getStylesheets().remove("darkmode.css");
		parent.getStylesheets().add("lightmode.css");
		Image image = new Image("moon.png");
		imgMode.setImage(image);
	}

	private void setDarkMode() {
		parent.getStylesheets().remove("lightmode.css");
		parent.getStylesheets().add("darkmode.css");
		Image image = new Image("sun.png");
		imgMode.setImage(image);
	}

	public void onDuLanguageButtonClick() {
		switchLanguage("Deutsch");
	}

	public void onNlLanguageButtonClick() {
		switchLanguage("Nederlands");
	}

	public void onEnLanguageButtonClick() {
		switchLanguage("English");
	}


	@FXML
	private void addFavourite(ActionEvent actionEvent) {
		DepartureCity = departureCityComboBox.getValue();
		ArrivalCity = arrivalCityComboBox.getValue();
		transport = transportComboBox.getValue();

        String addFavourite = String.format("Van %s naar %s met de %s"
                , DepartureCity, ArrivalCity, transport.toLowerCase());

		favouriteList.add(addFavourite);
		StringBuilder favourite = new StringBuilder();
		for (String entry : favouriteList) {
			favourite.append(entry).append("\n\n");
		}
		travelHistoryLabel.setText(favourite.toString());
	}

	@FXML
	private void useFavourite(ActionEvent actionEvent) {
		String[] favourite = favouriteList.get(0).split(" ");
		departureCityComboBox.setValue(favourite[1]);
		arrivalCityComboBox.setValue(favourite[3]);
		transportComboBox.setValue(favourite[6]);
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

	private List<String> getStation() {
		try {
			List<RouteData> routeDataList = data.getRouteData();
			return routeDataList.stream().map(RouteData::getStation).toList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ObservableList<String> getTransport() {
		return FXCollections.observableArrayList(train.getTransportName(), bus.getTransportName());
	}

	private List<String> determineAmenities(ArrayList<City> cities) {
		List<String> amenities = new ArrayList<>();
		int i = 0;
		int j = 0;
		String amenityString = "";
		List<String> formattedAmenities = new ArrayList<>();
		formattedAmenities.add("");
		formattedAmenities.add("");
		for (i = 0; i < cities.size(); i++) {
			currentCity = cities.get(i);
			if (DepartureCity.equals(currentCity.getName())) {
				amenities.addAll(currentCity.getAmenities());
				for (j = 0; j < amenities.size(); j++) {
					if (j < amenities.size() - 2) {
						amenityString += amenities.get(j) + ", ";
					}
					if (j == amenities.size() - 2) {
						amenityString += amenities.get(j) + " en ";
					}
					if (j == amenities.size() - 1) {
						amenityString += amenities.get(j) + ".";
					}
				}
				formattedAmenities.set(0, amenityString);
				amenityString = "";
				amenities.clear();
			}
			if (ArrivalCity.equals(currentCity.getName())) {
				amenities.addAll(currentCity.getAmenities());
				for (j = 0; j < amenities.size(); j++) {
					if (j < amenities.size() - 2) {
						amenityString += amenities.get(j) + ", ";
					}
					if (j == amenities.size() - 2) {
						amenityString += amenities.get(j) + " en ";
					}
					if (j == amenities.size() - 1) {
						amenityString += amenities.get(j) + ".";
					}
				}
				formattedAmenities.set(1, amenityString);
				amenityString = "";
				amenities.clear();
			}
		}
		return formattedAmenities;
	}
}