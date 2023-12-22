module com.example.ov_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.fasterxml.jackson.databind;


	opens com.ovapp to javafx.fxml;
    exports com.ovapp;
}