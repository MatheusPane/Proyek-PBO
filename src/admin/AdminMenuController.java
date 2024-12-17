package admin;

import admin.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminMenuController {

    @FXML
    private void handleTambahFilm(ActionEvent event) {
        SceneSwitcher.switchScene("/views/TambahFilm.fxml");
    }

    @FXML
    private void handleLihatDataFilm(ActionEvent event) {
        SceneSwitcher.switchScene("/views/LihatFilm.fxml");
    }

    @FXML
    private void handlePerbaruiDataFilm(ActionEvent event) {
        SceneSwitcher.switchScene("/views/UpdateFilm.fxml");
    }

    @FXML
    private void handleHapusDataFilm(ActionEvent event) {
        SceneSwitcher.switchScene("/views/HapusDataFilm.fxml");
    }

    @FXML
    private void handleTambahKursi(ActionEvent event) {
        SceneSwitcher.switchScene("/views/TambahKursi.fxml");
    }

    @FXML
    private void handleLihatDataKursi(ActionEvent event) {
        SceneSwitcher.switchScene("/views/LihatKursi.fxml");
    }

    @FXML
    private void handlePerbaruiDataKursi(ActionEvent event) {
        SceneSwitcher.switchScene("/views/UpdateKursi.fxml");
    }

    @FXML
    private void handleHapusDataKursi(ActionEvent event) {
        SceneSwitcher.switchScene("/views/HapusKursi.fxml");
    }

    @FXML
    private void handleKeluar(ActionEvent event) {
        System.exit(0);
    }

    private void showDialog(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
