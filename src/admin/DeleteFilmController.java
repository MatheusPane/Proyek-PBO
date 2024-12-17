package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFilmController {

    private static final Logger LOGGER = Logger.getLogger(DeleteFilmController.class.getName());

    @FXML
    private TextField idFilmField;  // Field untuk input ID Film
    @FXML
    private Label statusLabel;      // Label untuk status hasil penghapusan

    // Fungsi untuk menangani tombol hapus film
    @FXML
    private void handleHapusFilm(ActionEvent event) throws SQLException {
        statusLabel.setText(""); // Reset status label

        String idFilmText = idFilmField.getText();

        // Validasi input ID Film
        if (idFilmText == null || idFilmText.trim().isEmpty()) {
            showErrorDialog("Input Tidak Valid", "ID Film tidak boleh kosong.");
            return;
        }

        try {
            int idFilm = Integer.parseInt(idFilmText); // Konversi ID Film ke Integer
            deleteFilmFromDatabase(idFilm);           // Hapus film dari database
        } catch (NumberFormatException e) {
            showErrorDialog("Input Tidak Valid", "ID Film harus berupa angka.");
        }
    }

    // Metode untuk menghapus film dari database
    private void deleteFilmFromDatabase(int idFilm) throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();

        if (conn == null) {
            showErrorDialog("Database Error", "Koneksi ke database gagal.");
            return;
        }

        try {
            String sql = "DELETE FROM film WHERE id = ?"; // Query SQL
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idFilm);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    showInfoDialog("Sukses", "Film berhasil dihapus.");
                    statusLabel.setText("Film berhasil dihapus.");
                } else {
                    showErrorDialog("Gagal", "ID Film tidak ditemukan.");
                    statusLabel.setText("ID Film tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: {0}", e.getMessage()); // Logging
            showErrorDialog("Error", "Gagal menghapus film. Coba lagi nanti.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.WARNING, "Gagal menutup koneksi database: {0}", ex.getMessage());
                }
            }
        }
    }

    // Fungsi menampilkan dialog informasi
    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Fungsi menampilkan dialog error
    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Fungsi kembali ke menu utama
    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }
}
