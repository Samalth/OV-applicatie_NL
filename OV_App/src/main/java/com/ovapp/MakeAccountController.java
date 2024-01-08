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
import java.util.ResourceBundle;

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
    @Override
    String getDescriptionLabelText(ResourceBundle bundle) {
        return bundle.getString("descriptionMessage.newLogInInfo");
    }

    @Override
    String getPasswordTooltipText(ResourceBundle bundle) {
        return bundle.getString("makeAccount.passwordTooltip");
    }

    @Override
    String getUsernameTooltipText(ResourceBundle bundle) {
        return bundle.getString("makeAccount.usernameTooltip");
    }



    public String getUsername() {
        return super.getUsername();
    }

    public String getPassword() {
        return super.getPassword();
    }



    public void setMakeAccountButton() {
        MakeAccount();
    }

    private void MakeAccount() {

            String newUsername = getUsername();
            String newPassword = getPassword();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                loginMessageLabel.setText(bundle.getString("makeAccount.validation.empty"));
            } else if (newUsername.length() < 4 || newPassword.length() < 4) {
                loginMessageLabel.setText(bundle.getString("makeAccount.validation.length"));
            } else {
                // If everything is okay, add the new user to the loginInfo HashMap
                loginInfo.put(newUsername, newPassword);
                loginMessageLabel.setText(bundle.getString("makeAccount.success"));
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






