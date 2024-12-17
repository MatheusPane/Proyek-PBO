package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class TambahFilmController {

    @FXML
    private TextField judulField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField tahunField;
    @FXML
    private TextField durasiField;
    @FXML
    private TextField sutradaraField;
    @FXML
    private TextField jadwalField; // Tambahan: Jadwal Tayang
    @FXML
    private TextField hargaField; // Tambahan: Harga Tiket

    @FXML
    private void handleSimpanFilm(ActionEvent event) {
        String judul = judulField.getText();
        String genre = genreField.getText();
        String tahun = tahunField.getText();
        String durasi = durasiField.getText();
        String sutradara = sutradaraField.getText();
        String jadwal = jadwalField.getText(); // Ambil input jadwal tayang
        String harga = hargaField.getText();   // Ambil input harga tiket

        // Validasi input sebelum menyimpan
        if (judul.isEmpty() || genre.isEmpty() || tahun.isEmpty() || durasi.isEmpty() || 
            sutradara.isEmpty() || jadwal.isEmpty() || harga.isEmpty()) {
            showErrorDialog("Validasi Gagal", "Semua field harus diisi!", "");
            return;
        }

        // Simpan ke database
        try {
            // Parsing input yang perlu diubah ke integer
            int tahunRilis = Integer.parseInt(tahun);
            int durasiFilm = Integer.parseInt(durasi);
            int hargaTiket = Integer.parseInt(harga);

            // Panggil class InsertFilm untuk menyimpan data ke database
            InsertFilm.insertFilm(judul, genre, tahunRilis, durasiFilm, sutradara, jadwal, hargaTiket);
            showInfoDialog("Sukses", "Film berhasil ditambahkan!");
        } catch (NumberFormatException e) {
            showErrorDialog("Error", "Format angka tidak valid!", "Tahun, durasi, dan harga tiket harus berupa angka.");
        } catch (Exception e) {
            showErrorDialog("Error", "Gagal menambahkan film!", e.getMessage());
        }
    }

    @FXML
    private void handleKembali(ActionEvent event) {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
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
}
