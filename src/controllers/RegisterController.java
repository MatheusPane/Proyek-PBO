package controllers;

import admin.DatabaseConnection;
import admin.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    private Main mainApp; // Tambahkan untuk navigasi antar layar

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validasi input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Semua field harus diisi.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Password dan konfirmasi password tidak cocok.");
            return;
        }

        // Hash password
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            errorLabel.setText("Gagal memproses password.");
            return;
        }

        // Simpan ke database
        if (saveToDatabase(username, hashedPassword)) {
            errorLabel.setText("Registrasi berhasil! Silakan login.");
            handleGoToLogin();
        } else {
            errorLabel.setText("Gagal menyimpan data. Username mungkin sudah digunakan.");
        }
    }

    private boolean saveToDatabase(String username, String hashedPassword) {
        String insertQuery = "INSERT INTO pengguna (username, password, role) VALUES (?, ?, 'user')";

        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            return true; // Berhasil menyimpan
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Gagal menyimpan
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Gagal hashing
        }
    }

    @FXML
    private void handleGoToLogin() {
        if (mainApp != null) {
            mainApp.loadScene("/views/LoginView.fxml", null);
            mainApp.getPrimaryStage().setTitle("Login");
        } else {
            errorLabel.setText("Aplikasi utama tidak diinisialisasi.");
        }
    }
}
