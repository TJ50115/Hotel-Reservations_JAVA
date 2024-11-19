package com.joshuajardine.finalproject_hotelreservations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private TextField txtArrival;
    
    @FXML 
    private TextField txtDeparture;
    
    @FXML 
    private TextField txtPrice;
    
    @FXML
    private TextField txtNights;
    
    @FXML
    private Button btnCalc;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnBook;
    
    //calculate button
    public void handleCalculate() {
   
         try {
        // date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // check if both fields are non-empty
        if (txtArrival.getText().isEmpty() || txtDeparture.getText().isEmpty()) {
            showAlert("Error", "Enter both Arrival and Departure dates.");
            return;
        }

        // parse dates
        LocalDate arrivalDate = LocalDate.parse(txtArrival.getText(), formatter);
        LocalDate departureDate = LocalDate.parse(txtDeparture.getText(), formatter);
        
        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Validate dates
        if (!arrivalDate.isAfter(currentDate) || !departureDate.isAfter(currentDate)) {
            showAlert("Error", "Must be before current date.");
            return;
        }

        // calc # of nights
        long nights = ChronoUnit.DAYS.between(arrivalDate, departureDate);
        
        // departure has to be after arrival
        if (nights <= 0) {
            showAlert("Error", "Departure date must be after the arrival date.");
            return;
        }

        // calc price by 120 a night
        double totalPrice = nights * 120;

        // display results
        txtNights.setText(String.valueOf(nights));
        txtPrice.setText(String.format("$%.2f", totalPrice));

    } catch (Exception e) {
        showAlert("Error", "Invalid date format. Please use YYYY-MM-DD.");
    }
   }
    // my alert method
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // exit handler
    @FXML
    public void handleExit() {
    System.exit(0);
    }
    //book it handler
    @FXML
    public void handleBookIt() {
         try {
        // validate that dates are provided
        if (txtArrival.getText().isEmpty() || txtDeparture.getText().isEmpty()) {
            showAlert("Error", "Please enter both Arrival and Departure dates.");
            return;
        }

        // parse dates 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate arrivalDate = LocalDate.parse(txtArrival.getText(), formatter);
        LocalDate departureDate = LocalDate.parse(txtDeparture.getText(), formatter);

        // create reservation object
        HotelReservation reservation = new HotelReservation(arrivalDate, departureDate, Integer.parseInt(txtNights.getText()), Double.parseDouble(txtPrice.getText().replace("$", "").replace(",", "")));

        // create hoteldb instance
        HotelDB hotelDB = new HotelDB();

        // call reservation
        if (hotelDB.AddReservation(reservation)) {
            // load into reservation list
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent root = loader.load();

            // display stage
            Stage stage = new Stage();
            stage.setTitle("Reservations");
            stage.setScene(new Scene(root));

            // show pop up
            stage.initModality(Modality.APPLICATION_MODAL); // must be modal
            stage.show();
        } else {
            showAlert("Error", "Failed to add reservation. Please try again.");
        }
    } catch (IOException e) { //failed to load error
        showAlert("Error", "Failed to load reservations: " + e.getMessage());
    } catch (NumberFormatException e) { //invalid date error
        showAlert("Error", "Invalid date format. Please check your input.");
    } catch (Exception e) { //unknown exception error
        showAlert("Error", "An unexpected error occurred: " + e.getMessage());
    }
    // I used this for debugging, thought id leave it in for you to see
    //System.out.println("Booking List Loading...");
    }
  }

