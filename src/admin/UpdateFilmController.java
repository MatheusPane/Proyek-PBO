package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFilmController {

    @FXML
    private TextField idFilmField;  // Untuk ID film yang akan diperbarui
    @FXML
    private TextField judulField;  // Judul baru
    @FXML
    private TextField genreField;  // Genre baru
    @FXML
    private TextField durasiField; // Durasi baru
    @FXML
    private TextField sutradaraField; // Sutradara baru
    @FXML
    private TextField jadwalField; // Jadwal baru
    @FXML
    private TextField hargaField;  // Harga tiket baru


    @FXML
    private void handleUpdateFilm(ActionEvent event) {
        try {
            // Ambil input dari field
            int idFilm = Integer.parseInt(idFilmField.getText());
            String judulFilm = judulField.getText();
            String genre = genreField.getText();
            int durasi = Integer.parseInt(durasiField.getText());
            String sutradara = sutradaraField.getText();
            String jadwal = jadwalField.getText();
            int harga = Integer.parseInt(hargaField.getText());
            updateFilm(idFilm, judulFilm, genre, durasi, sutradara, jadwal, harga);


            // Tampilkan dialog sukses
            showInfoDialog("Sukses", "Film berhasil diperbarui!");
        } catch (NumberFormatException e) {
            // Tampilkan error jika input tidak valid
            showErrorDialog("Input Tidak Valid", "Pastikan semua data telah diisi dengan benar.");
        } catch (Exception e) {
            // Tangkap error lainnya
            showErrorDialog("Error", e.getMessage());
        }
    }

    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }

    private void updateFilm(int idFilm, String judulFilm, String genre, int durasi, String sutradara, String jadwal, int harga) throws Exception {
        // Pastikan DatabaseConnection sudah benar-benar tersedia di project Anda
        Connection conn = DatabaseConnection.getDBConnection();

        if (conn != null) {
            try {
                    String sql = "UPDATE film SET judul = ?, genre = ?, durasi = ?, sutradara = ?, jadwal = ?, harga = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, judulFilm);
                    stmt.setString(2, genre);
                    stmt.setInt(3, durasi);
                    stmt.setString(4, sutradara);
                    stmt.setString(5, jadwal);
                    stmt.setInt(6, harga);
                    stmt.setInt(7, idFilm);
                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated == 0) {
                        throw new Exception("ID Film tidak ditemukan.");
                    }
                }
            } finally {
                conn.close();
            }
        } else {
            throw new Exception("Gagal terhubung ke database.");
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
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
}
