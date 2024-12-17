package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateKursiController {

    @FXML
    private TextField idKursiField;

    @FXML
    private TextField nomorKursiField;

    @FXML
    private TextField statusField;

    // Handle aksi tombol "Perbarui"
    @FXML
    private void handlePerbarui(ActionEvent event) {
        String idKursi = idKursiField.getText();
        String nomorKursi = nomorKursiField.getText();
        String status = statusField.getText();

        if (idKursi.isEmpty() || nomorKursi.isEmpty() || status.isEmpty()) {
            showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String sql = "UPDATE kursi SET nomor_kursi = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nomorKursi);
                stmt.setString(2, status);
                stmt.setInt(3, Integer.parseInt(idKursi));
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    showAlert("Sukses", "Kursi berhasil diperbarui!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "ID Kursi tidak ditemukan.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal memperbarui kursi: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Handle aksi tombol "Kembali"
    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }

    // Metode untuk menampilkan alert
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
