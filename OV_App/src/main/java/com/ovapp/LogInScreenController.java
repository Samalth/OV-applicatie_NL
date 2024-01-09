package com.ovapp;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogInScreenController {
    private Stage stage;
    private Scene scene;

    @FXML
    private VBox parent;

    @FXML
    private ImageView imgMode;

    @FXML
    private Button cancelButton;
    @FXML
    private Button logInButton;

    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private ResourceBundle bundle;
    private boolean isLightMode = true;

    public void initialize() {
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

    private boolean validateLogin(String username, String password) {
        if (username.equals("user") && password.equals("1234") || (username.equals("1") && password.equals("1") || (username.equals(" ") && password.equals(" "))))
            return true;
        else
            return false;
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
        logInButton.setText(bundle.getString("LogInButtontxt"));
    }

    public void logInButtonOnAction(ActionEvent e) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (username.isEmpty() && password.isEmpty())
            loginMessageLabel.setText("Voer uw gegevens in.");
        else if (username.isEmpty())
            loginMessageLabel.setText("Voer uw gebruikersnaam in.");
        else if (password.isEmpty())
            loginMessageLabel.setText("Voer uw wachtwoord in.");
        else {
            if (validateLogin(username, password))
                openOVapp_LoggedIn();
            else
                loginMessageLabel.setText("Gegevens onjuist.");
        }
    }

    public void onChangeModeClick() {
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

    public void openOVapp_LoggedIn() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_LoggedIn.fxml")));
        scene = new Scene(root);
        stage = (Stage) (logInButton.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
