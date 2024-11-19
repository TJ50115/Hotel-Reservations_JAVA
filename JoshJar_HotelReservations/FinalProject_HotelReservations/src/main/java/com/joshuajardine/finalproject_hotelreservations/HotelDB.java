package com.joshuajardine.finalproject_hotelreservations;
/**
 * @author tripl
 */
// find a way to import sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelDB {
     private static final String DB_URL = "jdbc:mysql://localhost:3306/hoteldb";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    // add reservation method
    public static boolean AddReservation(HotelReservation hr) {
        String sql = "INSERT INTO reservations (arrival_date, departure_date, num_nights, price) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            // parameters
            preparedStatement.setTimestamp(1, Timestamp.valueOf(hr.getArrivalDate().atStartOfDay())); //fix value of
            preparedStatement.setTimestamp(2, Timestamp.valueOf(hr.getDepartureDate().atStartOfDay()));
            preparedStatement.setInt(3, hr.getNumNights());
            preparedStatement.setDouble(4, hr.getPrice());
            
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // return true if one row or more inserted
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // if error return false, used stack trace for debugging
        }
    }

    // method to get all reservations for display
    public static List<HotelReservation> GetReservations() {
        List<HotelReservation> reservations = new ArrayList<>();
        String sql = "SELECT arrival_date, departure_date, num_nights, price FROM reservations";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
             
            while (resultSet.next()) {
                LocalDate arrivalDate = resultSet.getTimestamp("arrival_date").toLocalDateTime().toLocalDate();
                LocalDate departureDate = resultSet.getTimestamp("departure_date").toLocalDateTime().toLocalDate();
                int numNights = resultSet.getInt("num_nights");
                double price = resultSet.getDouble("price");
                
                // create hotel reservations, add them to the list
                HotelReservation hr = new HotelReservation(arrivalDate, departureDate, numNights, price);
                reservations.add(hr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations; // return reservation list
    }
}
