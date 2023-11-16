module com.example.ov_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ovapp to javafx.fxml;
    exports com.ovapp;
}