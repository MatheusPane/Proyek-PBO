package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HapusKursiController {

    @FXML
    private TextField idKursiField;

    // Handle aksi tombol "Hapus"
    @FXML
    private void handleHapus(ActionEvent event) {
        String idKursi = idKursiField.getText();

        if (idKursi.isEmpty()) {
            showAlert("Error", "ID Kursi harus diisi!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String sql = "DELETE FROM kursi WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(idKursi));
                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    showAlert("Sukses", "Kursi berhasil dihapus!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "ID Kursi tidak ditemukan.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal menghapus kursi: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Handle aksi tombol "Kembali"
    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml"); // Path ke menu admin
    }

    // Metode untuk menampilkan alert
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
