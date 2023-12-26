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

public class LoginController extends ParentController {

        private Stage stage;
        private Scene scene;

        @FXML
        private Button logInButton;

        @FXML
        private Label loginMessageLabel;

        @FXML
        private TextField usernameTextField;
        @FXML
        private PasswordField passwordPasswordField;

        HashMap<String,String> loginInfo= new HashMap<>();

        public void initialize() {
            super.initialize();
            //hasmap login info gebruiker
            loginInfo.put("Hellokittyfan", "blahBlah123");
            loginInfo.put("Johony","Jo1234!");
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



    }


