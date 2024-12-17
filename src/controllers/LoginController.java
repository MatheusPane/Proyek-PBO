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
import java.sql.ResultSet;

public class LoginController {

    private Main mainApp; // Referensi ke Main

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    /**
     * Digunakan untuk menangani proses login.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username dan password harus diisi.");
            return;
        }

        // Hash password sebelum dibandingkan
        String hashedPassword = hashPassword(password);

        try (Connection conn = DatabaseConnection.getDBConnection()) {
            String query = "SELECT role FROM pengguna WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword); // Gunakan password yang sudah di-hash

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if (mainApp != null) {
                    mainApp.handleLoginSuccess(role);
                } else {
                    System.err.println("mainApp belum diatur di LoginController.");
                }
            } else {
                errorLabel.setText("Username atau password salah.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Terjadi kesalahan.");
        }
    }

    /**
     * Hash password menggunakan SHA-256.
     *
     * @param password Password dalam teks biasa.
     * @return Password yang sudah di-hash.
     */
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
            return null;
        }
    }

    @FXML
    private void handleGoToRegister() {
        if (mainApp != null) {
            mainApp.loadScene("/views/RegisterView.fxml", null);
            mainApp.getPrimaryStage().setTitle("Register");
        } else {
            System.err.println("mainApp belum diatur di LoginController.");
        }
    }

    /**
     * Setter untuk mengatur referensi mainApp.
     *
     * @param mainApp Objek utama aplikasi.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
