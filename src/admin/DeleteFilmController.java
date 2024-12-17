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

public class DeleteFilmController {

    @FXML
    private TextField idFilmField;  // Field untuk ID Film
    @FXML
    private Label statusLabel;  // Label untuk status

    // Fungsi untuk menangani penghapusan film
    @FXML
    private void handleHapusFilm(ActionEvent event) {
        try {
            // Ambil ID Film dari TextField
            int idFilm = Integer.parseInt(idFilmField.getText());

            // Lakukan penghapusan data film
            deleteFilm(idFilm);

            // Update status label
            statusLabel.setText("Film berhasil dihapus.");
        } catch (NumberFormatException e) {
            showErrorDialog("Input Tidak Valid", "Pastikan ID Film diisi dengan benar.");
        } catch (SQLException e) {
            showErrorDialog("Error", e.getMessage());
        }
    }

    // Fungsi untuk menghapus film dari database
    private void deleteFilm(int idFilm) throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();

        if (conn != null) {
            try {
                String query = "DELETE FROM film WHERE id_film = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idFilm);
                    int rowsDeleted = stmt.executeUpdate();

                    if (rowsDeleted > 0) {
                        showInfoDialog("Sukses", "Film berhasil dihapus.");
                    } else {
                        showErrorDialog("Gagal", "ID Film tidak ditemukan.");
                    }
                }
            } finally {
                if (conn != null) conn.close();
            }
        }
    }

    // Fungsi untuk menampilkan dialog informasi
    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Fungsi untuk menampilkan dialog error
    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Fungsi untuk kembali ke menu admin
    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }
}
