package com.ovapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

    public class Application extends javafx.application.Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("OVapp_GUI.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1440, 900);
		stage.initStyle(StageStyle.UTILITY);
		stage.setTitle("Ov-app");
		stage.setScene(scene);
		stage.show();
	}
}
