package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import admin.DatabaseConnection;
import models.Booking;

public class SaveBooking {
    public static void saveBooking(Booking booking) {
        String query = "INSERT INTO booking (film_id, nama_pembeli, jadwal, jumlah_tiket, total_harga, nomor_pemesanan) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, booking.getFilmId());
            stmt.setString(2, booking.getBuyerName());
            stmt.setTimestamp(3, booking.getSchedule());
            stmt.setInt(4, booking.getQuantity());
            stmt.setInt(5, booking.getTotal());
            stmt.setString(6, booking.getBookingNumber());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal menyimpan booking: " + e.getMessage());
        }
    }
}
