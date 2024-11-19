module com.joshuajardine.finalproject_hotelreservations {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.joshuajardine.finalproject_hotelreservations to javafx.fxml;
    exports com.joshuajardine.finalproject_hotelreservations;
}