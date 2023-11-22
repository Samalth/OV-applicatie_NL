package com.ovapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {
	public static ObservableList<String> departureData() {
		return FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
	}
}
