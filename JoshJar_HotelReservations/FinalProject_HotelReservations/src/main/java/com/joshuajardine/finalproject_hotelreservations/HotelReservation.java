package com.joshuajardine.finalproject_hotelreservations;
/**
 * @author tripl
 */
import java.time.LocalDate;

public class HotelReservation {
    //variable definitions
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int numNights;
    private double price;

    // reservation constructor
    public HotelReservation(LocalDate arrivalDate, LocalDate departureDate, int numNights, double price) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numNights = numNights;
        this.price = price;
    }

    // my getters
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public int getNumNights() {
        return numNights;
    }

    public double getPrice() {
        return price;
    }

    // my setters
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setNumNights(int numNights) {
        this.numNights = numNights;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // override the tostring
    @Override
    public String toString() {
        return "Reservation from " + arrivalDate + " to " + departureDate + 
               ", " + numNights + " nights, Total Price: $" + String.format("%.2f", price);
    }
}
