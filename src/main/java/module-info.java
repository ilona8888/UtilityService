module com.example.utilityservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.utilityservice to javafx.fxml;
    exports com.example.utilityservice;
}
