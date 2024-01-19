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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.*;
import java.time.format.DateTimeFormatter;

public class LoggedInController {
	private final Data data = new Data();
	Integer favouriteID;
	@FXML
	private Button logOutButton;
	@FXML
	private Button GOButton;
	@FXML
	private Button showFavouriteButton;
	@FXML
	private Button setFavourite;
	@FXML
	private Button favourite0;
	@FXML
	private Button favourite1;
	@FXML
	private Button favourite2;
	@FXML
	private Button favourite3;
	@FXML
	private Button favourite4;
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
	private Tooltip favouriteLabelToolTip;
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
	private Label favouriteLabel;
	private boolean isFavouriteVisible = false;
	@FXML
	private String departureCity;
	@FXML
	private String arrivalCity;
	@FXML
	private LocalDate DepartureDate;
	private String transport;
	@FXML
	private Button amenitiesFavorites;
	@FXML
	private Pane amenities;
	@FXML
	private Pane favorites;
	@FXML
	private ImageView amenityLiftDeparture;
	@FXML
	private ImageView amenityStairMarkingsDeparture;
	@FXML
	private ImageView amenityTactilePavementDeparture;
	@FXML
	private ImageView amenityLiftArrival;
	@FXML
	private ImageView amenityStairMarkingsArrival;
	@FXML
	private ImageView amenityTactilePavementArrival;
	private List<String> favouriteList = new ArrayList<>();
	private List<String> displayFavouriteList = new ArrayList<>();
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
		cities.add(new City("Den Haag", Arrays.asList("liften", "geleidenstroken")));
		cities.add(new City("IJsselstein", Arrays.asList("geleidenstroken", "trapmarkeringen")));
		cities.add(new City("Nieuwegein", Arrays.asList("liften", "geleidenstroken")));
		cities.add(new City("Utrecht", Arrays.asList("liften", "geleidenstroken", "trapmarkeringen")));
		cities.add(new City("Rotterdam", Arrays.asList("liften", "trapmarkeringen")));
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

		favouriteLabel.setVisible(false);
		switchLanguage("Nederlands");
		setLightMode();
		amenityLiftDeparture.setVisible(false);
		amenityStairMarkingsDeparture.setVisible(false);
		amenityTactilePavementDeparture.setVisible(false);
		amenityLiftArrival.setVisible(false);
		amenityStairMarkingsArrival.setVisible(false);
		amenityTactilePavementArrival.setVisible(false);
		amenities.setVisible(false);
		favorites.setVisible(false);
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
	protected void onShowFavouriteVisible() {
		isFavouriteVisible = !isFavouriteVisible;
		favouriteLabel.setVisible(isFavouriteVisible);
		favourite0.setVisible(isFavouriteVisible);
		favourite1.setVisible(isFavouriteVisible);
		favourite2.setVisible(isFavouriteVisible);
		favourite3.setVisible(isFavouriteVisible);
		favourite4.setVisible(isFavouriteVisible);

		if (isFavouriteVisible) {
			System.out.println("Favorieten zichtbaar");
		} else {
			System.out.println("Favorieten verborgen");
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
		departureCity = departureCityComboBox.getValue();
		arrivalCity = arrivalCityComboBox.getValue();
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
			if (departureAmenities != null && arrivalAmenities != null && transport != null) {
				setAmenityImages(departureAmenities, arrivalAmenities);
			}
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
		if (departureCity == null || arrivalCity == null || transport == null) {
			departureLabel.setText("Selecteer alstublieft een vertrekplaats, aankomstplaats en vervoermiddel.");
		} else if (departureCity.equals(arrivalCity)) {
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
							"De voorzieningen op station %s zijn %s", departureCity, arrivalCity
					, departureTime.get(0), transport.toLowerCase(), formattedDate,
					departureCity, departureAmenities, arrivalCity, arrivalAmenities);
			departureLabel.setText(departureLabelInfo);
		}
	}

	public void onDepartureCityComboBoxClick() {
		updateCityComboBox(departureCityComboBox, arrivalCityComboBox);
	}

	public void onArrivalCityComboBoxClick() {
		updateCityComboBox(arrivalCityComboBox, departureCityComboBox);
	}

	private void updateCityComboBox(ComboBox<String> sourceComboBox, ComboBox<String> otherComboBox) {
		String selectedCity = sourceComboBox.getValue();

		if (selectedCity != null) {
			otherComboBox.getItems().remove(selectedCity);

			sourceComboBox.setOnAction(event -> {
				otherComboBox.getItems().add(selectedCity);
				sourceComboBox.setOnAction(null);
			});
		}
	}

	private List<String> getLanguages() {
		return Arrays.asList("Nederlands", "English", "Deutsch");
	}

	public void switchLanguage(String newLanguage) {
		Locale locale = new Locale(newLanguage);
		bundle = ResourceBundle.getBundle("Messages", locale);

		//ArrivalText.setText(bundle.getString("Destinationtxt"));
		//DepartureText.setText(bundle.getString("Departuretxt"));
		//DepartureDateText.setText(bundle.getString("DepartureDatetxt"));
		//DepartureTimeText.setText(bundle.getString("DepartureTimetxt"));
		//MeansOfTransportText.setText(bundle.getString("MeansOfTransporttxt"));

		GOButton.setText(bundle.getString("RouteButtontxt"));
		setFavourite.setText(bundle.getString("setFavouriteButtontxt"));
		logOutButton.setText(bundle.getString("LogOutButtontxt"));
		showFavouriteButton.setText(bundle.getString("ShowFavouriteButtontxt"));

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
		favouriteLabelToolTip.setText(bundle.getString("favouriteTooltiptxt"));
		showTravelHistoryButtonToolTip.setText(bundle.getString("ShowTravelHistoryButtonToolTiptxt"));
		dateLabelToolTip.setText(bundle.getString("DateLabelToolTiptxt"));

		if (favouriteLabel != null){
			favouriteLabel.setText(bundle.getString("FavouriteLabelError"));
		}
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
		departureCity = departureCityComboBox.getValue();
		arrivalCity = arrivalCityComboBox.getValue();
		transport = transportComboBox.getValue();

		if (departureCity == null || arrivalCity == null || transport == null) {
			favouriteLabel.setText(bundle.getString("FavouriteLabelError"));
		} else {
			String addFavourite = String.format("%s,%s,%s,"
					, departureCity, arrivalCity, transport);
			favouriteList.add(addFavourite);
			{
				if (favouriteList.size() > 5) {
					favouriteList.remove(0);
				}
			}
			displayFavourite();
		}
	}

	private void displayFavourite() {
		try {
			String[] favouriteString = favouriteList.get(favouriteList.size() - 1).split(",");
			String departureString = favouriteString[0];
			String arrivalString = favouriteString[1];
			String transportString = favouriteString[2];

			String displayFavourite = String.format("   Van %s naar %s met de %s"
					, departureString, arrivalString, transportString.toLowerCase());
			displayFavouriteList.add(displayFavourite);
			{
				if (displayFavouriteList.size() > 5) {
					displayFavouriteList.remove(0);
				}
			}
		} catch (NullPointerException e) {
			favouriteLabel.setText(bundle.getString("FavouriteLabelError"));
		}
		showFavourite();
	}

	private void showFavourite() {
		for (int i = 0; i < displayFavouriteList.size(); i++) {
			switch (i) {
				case 0 -> {
					favourite0.setVisible(true);
					favourite0.setText(displayFavouriteList.get(i));
				}
				case 1 -> {
					favourite1.setVisible(true);
					favourite1.setText(displayFavouriteList.get(i));
				}
				case 2 -> {
					favourite2.setVisible(true);
					favourite2.setText(displayFavouriteList.get(i));
				}
				case 3 -> {
					favourite3.setVisible(true);
					favourite3.setText(displayFavouriteList.get(i));
				}
				case 4 -> {
					favourite4.setVisible(true);
					favourite4.setText(displayFavouriteList.get(i));
				}
			}
		}
	}

	@FXML
	private void setFavourite0(ActionEvent actionEvent) {
		favouriteID = 0;
		useFavourite(actionEvent);
	}

	@FXML
	private void setFavourite1(ActionEvent actionEvent) {
		favouriteID = 1;
		useFavourite(actionEvent);
	}

	@FXML
	private void setFavourite2(ActionEvent actionEvent) {
		favouriteID = 2;
		useFavourite(actionEvent);
	}

	@FXML
	private void setFavourite3(ActionEvent actionEvent) {
		favouriteID = 3;
		useFavourite(actionEvent);
	}

	@FXML
	private void setFavourite4(ActionEvent actionEvent) {
		favouriteID = 4;
		useFavourite(actionEvent);
	}

	@FXML
	private void useFavourite(ActionEvent actionEvent) {
		String[] favourite = favouriteList.get(favouriteID).split(",");
		departureCityComboBox.setValue(favourite[0]);
		arrivalCityComboBox.setValue(favourite[1]);
		transportComboBox.setValue(favourite[2]);
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
			if (departureCity.equals(currentCity.getName())) {
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
			if (arrivalCity.equals(currentCity.getName())) {
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

	private void setAmenityImages(String departureAmenities, String arrivalAmenities){
		if (departureAmenities.contains("liften")){
			amenityLiftDeparture.setVisible(true);
		} else {
			amenityLiftDeparture.setVisible(false);
		}
		if(departureAmenities.contains("trapmarkeringen")){
			amenityStairMarkingsDeparture.setVisible(true);
		} else {
			amenityStairMarkingsDeparture.setVisible(false);
		}
		if(departureAmenities.contains("geleidenstroken")){
			amenityTactilePavementDeparture.setVisible(true);
		} else {
			amenityTactilePavementDeparture.setVisible(false);
		}

		if (arrivalAmenities.contains("liften")){
			amenityLiftArrival.setVisible(true);
		} else {
			amenityLiftArrival.setVisible(false);
		}
		if(arrivalAmenities.contains("trapmarkeringen")){
			amenityStairMarkingsArrival.setVisible(true);
		} else {
			amenityStairMarkingsArrival.setVisible(false);
		}
		if(arrivalAmenities.contains("geleidenstroken")){
			amenityTactilePavementArrival.setVisible(true);
		} else {
			amenityTactilePavementArrival.setVisible(false);
		}
	}

	public void onAmenitiesFavoritesClick(){
		if(!amenities.isVisible() && !favorites.isVisible()){
			amenities.setVisible(true);
			favorites.setVisible(false);
		} else if (!favorites.isVisible() && amenities.isVisible()){
			amenities.setVisible(false);
			favorites.setVisible(true);
		}else if (favorites.isVisible() && !amenities.isVisible()){
			favorites.setVisible(false);
			amenities.setVisible(true);
		}

	}
}