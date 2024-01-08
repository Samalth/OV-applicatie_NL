package com.ovapp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.*;

abstract class ParentController {

    @FXML
    private VBox parent;

    @FXML
    private ImageView imgMode;

    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;
    @FXML
    Button previousButton;

    @FXML
    Button logInButton;

    @FXML
    Button makeAccountButton;

    @FXML
    Label loginMessageLabel;
    @FXML
    Label descriptionLabel;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordPasswordField;

    ResourceBundle bundle;
    private boolean isLightMode = true;
    @FXML
    private Tooltip modeToolTip;
    @FXML
    private Tooltip passwordPasswordFieldToolTip;
    @FXML
    private Tooltip previousButtonTooltip;
    @FXML
    private Tooltip makeAccountButtonToolTip;
    @FXML
    private Tooltip logInButtonToolTip;
    @FXML
    private Tooltip usernameTextFieldToolTip;

    @FXML
    private Tooltip dateLabelToolTip;

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

    abstract String getDescriptionLabelText(ResourceBundle bundle);
    abstract String getPasswordTooltipText(ResourceBundle bundle);
    abstract String getUsernameTooltipText(ResourceBundle bundle);

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
        modeToolTip.setText(bundle.getString("ModeToolTiptxt"));
        logInButton.setText(bundle.getString("logInButton"));
        makeAccountButton.setText(bundle.getString("makeAccountButton"));
        usernameTextField.setPromptText(bundle.getString("usernameTextField"));
        passwordPasswordField.setPromptText(bundle.getString("passwordPasswordField"));
        previousButton.setText(bundle.getString("previousButton"));

        // Tooltips for new buttons
        makeAccountButtonToolTip.setText(bundle.getString("makeAccountButtonToolTip"));
        logInButtonToolTip.setText(bundle.getString("logInButtonToolTip"));
        passwordPasswordFieldToolTip.setText(getPasswordTooltipText(bundle));
        usernameTextFieldToolTip.setText(getUsernameTooltipText(bundle));
        previousButtonTooltip.setText(bundle.getString("previousButtonTooltip"));
        descriptionLabel.setText(getDescriptionLabelText(bundle));
        dateLabelToolTip.setText(bundle.getString("DateLabelToolTiptxt"));


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
}
