package com.joshuajardine.finalproject_hotelreservations;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SecondaryController implements Initializable {
    
    @FXML
    private ListView<String> lstBookings;
    
     @FXML
     private Button btnClose;
     
     @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load reservations
        loadReservations();
    }
    private void loadReservations() {
        HotelDB hotelDB = new HotelDB();
        List<HotelReservation> reservations = hotelDB.GetReservations();

        // clear list
        lstBookings.getItems().clear();

        // loop reservations
        for (HotelReservation reservation : reservations) {
            lstBookings.getItems().add(reservation.toString());
        }
        
    }
    public void handleClose() {
        // close stage
         Stage stage = (Stage) lstBookings.getScene().getWindow();
        stage.close();
    }
}