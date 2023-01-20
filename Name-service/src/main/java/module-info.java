module com.nameservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.nameservice to javafx.fxml;
    exports com.nameservice;
}