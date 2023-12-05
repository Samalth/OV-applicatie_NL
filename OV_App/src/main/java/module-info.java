module com.example.ov_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.json;


	opens com.ovapp to javafx.fxml;
    exports com.ovapp;
}