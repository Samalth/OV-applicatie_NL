package com.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MakeAccountController extends LoginController {
    @FXML
    private Button previousButton;
    public void initialize() {
        super.initialize();

}
    private boolean ValidateLogin(String username, String password) {
        if (loginInfo.containsKey(username)) {
            return loginInfo.get(username).equals(password);
        } else {
            return false;
        }
    }
    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordPasswordField.getText();
    }


    public void logInButtonOnAction(ActionEvent e) throws IOException {
        String username = getUsername(); //hier hebben we get method aangeroepen
        String password = getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Voer uw nieuwe gebruikersnaam en wachtwoord in en klik op account aanmaken en probeer opnieuw.");
        } else {
            if (ValidateLogin(username, password)) {
                openOVapp_LoggedIn();
            } else {
                loginMessageLabel.setText("Voer uw nieuwe inloggegevens in en klik op account aanmaken en Probeer opnieuw.");
            }
        }
    }


    public void openOVapp_LoggedIn() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_LoggedIn.fxml")));
        scene = new Scene(root);
        stage = (Stage) (logInButton.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void setMakeAccountButton() {
        MakeAccount();
    }

    private void MakeAccount() {

            String newUsername = getUsername();
            String newPassword = getPassword();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                loginMessageLabel.setText("Voer uw nieuwe gebruikersnaam en wachtwoord in.");
            } else if (newUsername.length() < 4 || newPassword.length() < 4) {
                loginMessageLabel.setText("Gebruikersnaam en wachtwoord moeten minstens 4 tekens lang zijn.");
            } else {
                // If everything is okay, add the new user to the loginInfo HashMap
                loginInfo.put(newUsername, newPassword);
                loginMessageLabel.setText("Account succesvol aangemaakt. U kunt nu inloggen.");
            }}

        public void previousButtonOnAction(ActionEvent event) throws IOException {
            // Get the existing login screen (OVapp_LogInScreen.fxml)
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_LogInScreen.fxml")));

            // Get the current stage and set the existing scene
            Stage currentStage = (Stage) previousButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));  // You can also reuse the existing scene if needed
            currentStage.setTitle("Log In");

            // Show the existing scene
            currentStage.show();
        }
    }






