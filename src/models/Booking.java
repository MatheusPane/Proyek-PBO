package models;

import java.sql.Timestamp;

public class Booking {
    private int filmId;
    private String buyerName;
    private Timestamp schedule;
    private int quantity;
    private int total;
    private String bookingNumber;

    public Booking(int filmId, String buyerName, Timestamp schedule, int quantity, int total, String bookingNumber) {
        this.filmId = filmId;
        this.buyerName = buyerName;
        this.schedule = schedule;
        this.quantity = quantity;
        this.total = total;
        this.bookingNumber = bookingNumber;
    }

    public int getFilmId() { return filmId; }
    public String getBuyerName() { return buyerName; }
    public Timestamp getSchedule() { return schedule; }
    public int getQuantity() { return quantity; }
    public int getTotal() { return total; }
    public String getBookingNumber() { return bookingNumber; }
}

