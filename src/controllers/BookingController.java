package controllers;

import admin.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Booking;
import models.SaveBooking;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingController {

    @FXML private TextField buyerNameField;
    @FXML private ComboBox<String> filmTitleComboBox; 
    @FXML private ComboBox<String> seatComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField totalField;
    @FXML private TextArea bookingDetailsArea;
    @FXML private ListView<CheckBox> seatListView; 


    private int ticketPrice; 
    private Timestamp filmSchedule; 
    private int filmId; // Menyimpan ID film yang dipilih

    @FXML
    public void initialize() {
        loadFilms();
        filmTitleComboBox.setOnAction(event -> loadFilmDetails());
    }

    // Load daftar film ke ComboBox
    private void loadFilms() {
        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String query = "SELECT judul FROM film";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                filmTitleComboBox.getItems().add(rs.getString("judul"));
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Gagal memuat daftar film!", e.getMessage());
        }
    }

    // Load detail film yang dipilih
    private void loadFilmDetails() {
        String selectedFilm = filmTitleComboBox.getValue();
        if (selectedFilm == null) return;

        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String query = "SELECT id, jadwal_tayang, harga_tiket FROM film WHERE judul = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedFilm);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                filmId = rs.getInt("id");
                filmSchedule = rs.getTimestamp("jadwal_tayang");
                ticketPrice = rs.getInt("harga_tiket");

                totalField.setText("Jadwal: " + filmSchedule.toString());
                loadSeats(); // Load kursi untuk film yang dipilih
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Gagal memuat detail film!", e.getMessage());
        }
    }

    // Load kursi yang tersedia
    private List<String> selectedSeats = new ArrayList<>();
    private void loadSeats() {
    seatListView.getItems().clear(); // Clear previous items
    selectedSeats.clear();

    try (Connection conn = DatabaseConnection.getDBConnection()) {
        String query = "SELECT nomor_kursi FROM kursi WHERE id_film = ? AND jadwal_tayang = ? AND status IS TRUE";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, filmId);
        stmt.setTimestamp(2, filmSchedule);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String seatNumber = rs.getString("nomor_kursi");
            CheckBox seatCheckBox = new CheckBox(seatNumber);

            // Tambahkan event listener untuk CheckBox
            seatCheckBox.setOnAction(event -> {
                if (seatCheckBox.isSelected()) {
                    selectedSeats.add(seatNumber); // Tambahkan ke list jika dipilih
                } else {
                    selectedSeats.remove(seatNumber); // Hapus dari list jika batal dipilih
                }
            });

            seatListView.getItems().add(seatCheckBox);
        }

        if (seatListView.getItems().isEmpty()) {
            showErrorDialog("Error", "Tidak ada kursi yang tersedia untuk film ini.", "");
        }
    } catch (Exception e) {
        showErrorDialog("Error", "Gagal memuat data kursi dari database!", e.getMessage());
    }
}



    @FXML
private void handleBook() {
    try {
        String buyerName = buyerNameField.getText();
        int quantity;

        if (buyerName.isEmpty() || selectedSeats.isEmpty()) {
            showErrorDialog("Error", "Semua field harus diisi dan kursi harus dipilih!", "");
            return;
        }

        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showErrorDialog("Error", "Jumlah tiket harus berupa angka positif!", "");
            return;
        }

        if (selectedSeats.size() != quantity) {
            showErrorDialog("Error", "Jumlah kursi yang dipilih harus sama dengan jumlah tiket!", "");
            return;
        }

        int total = ticketPrice * quantity;
        String bookingNumber = generateBookingNumber(); // Generate nomor pemesanan

        bookingDetailsArea.setText(String.format(
            "Nomor Pemesanan: %s\nNama Pembeli: %s\nFilm: %s\nJadwal: %s\nJumlah Tiket: %d\nKursi: %s\nTotal Harga: Rp %d",
            bookingNumber, buyerName, filmTitleComboBox.getValue(), filmSchedule, quantity, selectedSeats, total
        ));

        Booking booking = new Booking(filmId, buyerName, filmSchedule, quantity, total, bookingNumber);
        SaveBooking.saveBooking(booking);

        for (String seat : selectedSeats) {
            updateSeatStatus(seat); // Perbarui status setiap kursi
        }

        showInfoDialog("Sukses", "Pemesanan berhasil disimpan!\nNomor Pemesanan: " + bookingNumber);
        handleReset(); // Reset form
    } catch (Exception e) {
        showErrorDialog("Error", "Terjadi kesalahan!", e.getMessage());
    }
}


    @FXML
private void handleReset() {
    buyerNameField.clear();
    filmTitleComboBox.getSelectionModel().clearSelection();
    seatListView.getItems().clear();
    selectedSeats.clear();
    quantityField.clear();
    totalField.clear();
    bookingDetailsArea.clear();
}

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String generateBookingNumber() {
    return "BOOK-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    private void updateSeatStatus(String selectedSeat) {
    try (Connection conn = DatabaseConnection.getDBConnection()) {
        String query = "UPDATE kursi SET status = FALSE WHERE nomor_kursi = ? AND id_film = ? AND jadwal_tayang = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, selectedSeat);
        stmt.setInt(2, filmId);
        stmt.setTimestamp(3, filmSchedule);
        stmt.executeUpdate();
    } catch (SQLException e) {
        showErrorDialog("Error", "Gagal memperbarui status kursi!", e.getMessage());
    }
}


}
