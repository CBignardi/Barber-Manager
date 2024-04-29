module com.bignardi.barbermanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bignardi.barbermanager to javafx.fxml;
    exports com.bignardi.barbermanager;
}