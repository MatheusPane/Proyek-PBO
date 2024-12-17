package admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LihatFilmController {

    @FXML
    private TableView<Film> filmTable;
    @FXML
    private TableColumn<Film, String> judulColumn;
    @FXML
    private TableColumn<Film, String> genreColumn;
    @FXML
    private TableColumn<Film, Integer> tahunColumn;
    @FXML
    private TableColumn<Film, Integer> durasiColumn;
    @FXML
    private TableColumn<Film, String> sutradaraColumn;

    /**
     * Inisialisasi data tabel saat controller dimuat.
     */
    @FXML
    public void initialize() throws SQLException {
        // Hubungkan kolom tabel dengan properti dari model Film
        judulColumn.setCellValueFactory(new PropertyValueFactory<>("judul"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tahunColumn.setCellValueFactory(new PropertyValueFactory<>("tahunRilis"));
        durasiColumn.setCellValueFactory(new PropertyValueFactory<>("durasi"));
        sutradaraColumn.setCellValueFactory(new PropertyValueFactory<>("sutradara"));

        // Load data ke tabel dari database
        filmTable.setItems(getFilmData());
    }

    /**
     * Metode untuk mengambil data film dari database.
     */
    private ObservableList<Film> getFilmData() throws SQLException {
        ObservableList<Film> data = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getDBConnection();

        if (conn != null) {
            try {
                String sql = "SELECT judul, genre, tahun_rilis, durasi, sutradara FROM film";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    ResultSet resultSet = stmt.executeQuery();

                    while (resultSet.next()) {
                        String judul = resultSet.getString("judul");
                        String genre = resultSet.getString("genre");
                        int tahunRilis = resultSet.getInt("tahun_rilis");
                        int durasi = resultSet.getInt("durasi");
                        String sutradara = resultSet.getString("sutradara");

                        // Tambahkan data film ke ObservableList
                        data.add(new Film(judul, genre, tahunRilis, durasi, sutradara));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving data: " + e.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        return data;
    }

    /**
     * Handle tombol kembali ke menu utama.
     */
    @FXML
    private void handleKembali() {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }
}
