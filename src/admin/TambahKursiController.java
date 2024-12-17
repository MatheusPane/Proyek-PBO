package admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // Benar
public class TambahKursiController {

    @FXML
    private ComboBox<String> judulFilmComboBox; // Dropdown untuk memilih judul film

    @FXML
    private void initialize() {
        // Muat daftar judul film ke ComboBox
        loadJudulFilm();
    }

    @FXML
    private void handleSimpan(ActionEvent event) {
        String judulFilm = judulFilmComboBox.getValue();

        if (judulFilm == null || judulFilm.isEmpty()) {
            showErrorDialog("Error", "Pilih judul film terlebih dahulu!");
            return;
        }

        try {
            // Ambil ID film berdasarkan judul
            int idFilm = getIdFilmByJudul(judulFilm);

            // Tambahkan 50 kursi otomatis untuk film tersebut
            insertKursi(idFilm);
            showInfoDialog("Sukses", "50 Kursi berhasil ditambahkan untuk film: " + judulFilm);
        } catch (SQLException e) {
            showErrorDialog("Database Error", "Gagal menambahkan kursi: " + e.getMessage());
        }
    }

    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }

    private void loadJudulFilm() {
        ObservableList<String> filmList = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String sql = "SELECT judul FROM film";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    filmList.add(rs.getString("judul"));
                }
                judulFilmComboBox.setItems(filmList);
            }
        } catch (SQLException e) {
            showErrorDialog("Database Error", "Gagal memuat daftar film: " + e.getMessage());
        }
    }

    private int getIdFilmByJudul(String judulFilm) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String sql = "SELECT id FROM film WHERE judul = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, judulFilm);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            }
        }
        throw new SQLException("Film tidak ditemukan!");
    }

    private void insertKursi(int idFilm) throws SQLException {
    try (Connection conn = DatabaseConnection.getDBConnection()) {
        // Ambil jadwal tayang dari film
        String jadwalQuery = "SELECT jadwal_tayang FROM film WHERE id = ?";
        Timestamp jadwalTayang;
        try (PreparedStatement jadwalStmt = conn.prepareStatement(jadwalQuery)) {
            jadwalStmt.setInt(1, idFilm);
            try (ResultSet rs = jadwalStmt.executeQuery()) {
                if (rs.next()) {
                    jadwalTayang = (Timestamp) rs.getTimestamp("jadwal_tayang");
                } else {
                    throw new SQLException("Jadwal tayang untuk film ini tidak ditemukan!");
                }
            }
        }

        // Masukkan 50 kursi dengan jadwal_tayang
        String sql = "INSERT INTO kursi (id_film, jadwal_tayang, nomor_kursi, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 50; i++) {
                String nomorKursi = "A" + i; // Kursi A1, A2, ..., A50
                stmt.setInt(1, idFilm);
                stmt.setTimestamp(2, (java.sql.Timestamp) jadwalTayang); // Menambahkan jadwal tayang
                stmt.setString(3, nomorKursi);
                stmt.setBoolean(4, true); // Status kursi tersedia
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}



    private void showInfoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
