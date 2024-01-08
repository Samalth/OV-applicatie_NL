package com.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController extends ParentController {

        Stage stage;
        Scene scene;

        @FXML
        Button logInButton;

        @FXML
        Button makeAccountButton;

        @FXML
        Label loginMessageLabel;

        @FXML
        TextField usernameTextField;
        @FXML
        PasswordField passwordPasswordField;

        static HashMap<String,String> loginInfo= new HashMap<>();


    public void initialize() {
            super.initialize();
            //hasmap login info gebruiker
            loginInfo.put("Hellokittyfan", "blahBlah123");
            loginInfo.put("Johony","Jo1234!");
        }

    @Override
    String getDescriptionLabelText(ResourceBundle bundle) {
        return bundle.getString("descriptionMessage.logInInfo");
    }

    @Override
    String getPasswordTooltipText(ResourceBundle bundle) {
        return bundle.getString("login.passwordTooltip");
    }

    @Override
    String getUsernameTooltipText(ResourceBundle bundle) {
        return bundle.getString("login.usernameTooltip");
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
                loginMessageLabel.setText("Voer uw gebruikersnaam en wachtwoord in.");
            } else {
                if (ValidateLogin(username, password)) {
                    openOVapp_LoggedIn();
                } else {
                    loginMessageLabel.setText("Onjuiste inloggegevens. Probeer opnieuw.");
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
        makeAccount();
    }

    private void makeAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OVapp_MakeAccount.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) makeAccountButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Making account");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void previousButtonOnAction(ActionEvent event) throws IOException {
        // Get the existing login screen (OVapp_LogInScreen.fxml)
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_GUI.fxml")));

        // Get the current stage and set the existing scene
        Stage currentStage = (Stage) makeAccountButton.getScene().getWindow();
        currentStage.setScene(new Scene(root));  // You can also reuse the existing scene if needed
        currentStage.setTitle("Uitgelogd scherm");

        // Show the existing scene
        currentStage.show();
    }}

