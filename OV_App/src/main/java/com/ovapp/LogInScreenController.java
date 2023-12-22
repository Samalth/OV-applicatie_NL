package com.ovapp;

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
import java.util.Objects;

public class LogInScreenController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox parent;
    @FXML
    private ImageView imgMode;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private boolean validateLogin(String username, String password) {
        if (username.equals("user") && password.equals("1234") || (username.equals("1") && password.equals("1")))
            return true;
        else
            return false;
    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {
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
                goToHelloView();
            else
                loginMessageLabel.setText("Gegevens onjuist.");
        }
    }

    private boolean isLightMode = true;
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
    public void goToHelloView() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_LoggedIn.fxml")));
        scene = new Scene(root);
        stage = (Stage) (loginButton.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
