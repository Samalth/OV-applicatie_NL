package com.ovapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

public class ParentController {

    @FXML
    private VBox parent;

    @FXML
    private ImageView imgMode;

    @FXML
    private Label dateLabel;
    @FXML
    private Label clockLabel;

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
