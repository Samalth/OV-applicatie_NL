package com.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController extends ParentController {

	static HashMap<String, String> loginInfo = new HashMap<>();
	Stage stage;
	Scene scene;
    @FXML
    private Button logInButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;


    @FXML
    private ImageView imgMode;

    @FXML
    private VBox parent;


        Stage stage;
        Scene scene;
        static HashMap<String,String> loginInfo= new HashMap<>();


	public void initialize() {
		super.initialize();
		//hasmap login info Users
		loginInfo.put("1", "1");
		loginInfo.put("aaaa", "aaaa");
		loginInfo.put("Hellokittyfan", "blahBlah123");
        loginInfo.put("User", "1234");
        loginInfo.put(" "," ");
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
		String username = getUsername();
		String password = getPassword();
		if (username.isEmpty() || password.isEmpty()) {
			loginMessageLabel.setText(bundle.getString("login.validation.empty"));
		} else {
			if (ValidateLogin(username, password)) {
				openOVapp_LoggedIn();
			} else {
				loginMessageLabel.setText(bundle.getString("login.validation.invalid"));
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
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OVapp_GUI.fxml")));


		Stage currentStage = (Stage) previousButton.getScene().getWindow();
		currentStage.setScene(new Scene(root));
		currentStage.setTitle("Uitgelogd scherm");
		currentStage.show();
	}
}

