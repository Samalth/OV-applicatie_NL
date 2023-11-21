package com.ovapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class Controller {
	@FXML
	private ChoiceBox<String> departure;
	private Button welcomeText;

	@FXML
	public void initialize() {
		initDeparture();
	}

	protected void initDeparture() {
		ObservableList<String> choices = FXCollections.observableArrayList(
				"Option 1", "Option 2", "Option 3");
		departure.setItems(choices);
		departure.setValue(choices.get(0));
	}

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Voorbeeld");
	}
}