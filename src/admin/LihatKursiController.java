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

public class LihatKursiController {

    @FXML
    private TableView<Kursi> kursiTable;

    @FXML
    private TableColumn<Kursi, String> nomorKursiColumn;

    @FXML
    private TableColumn<Kursi, String> statusColumn;

    @FXML
    public void initialize() {
        // Hubungkan kolom dengan properti Kursi
        nomorKursiColumn.setCellValueFactory(new PropertyValueFactory<>("nomorKursi"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Muat data dari database
        kursiTable.setItems(getKursiData());
    }

    private ObservableList<Kursi> getKursiData() {
        ObservableList<Kursi> data = FXCollections.observableArrayList();

        String query = "SELECT nomor_kursi, status FROM kursi";
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nomorKursi = rs.getString("nomor_kursi");
                String status = rs.getString("status");
                data.add(new Kursi(nomorKursi, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error jika perlu
        }
        return data;
    }

    @FXML
    private void handleKembali() {
        SceneSwitcher.switchScene("/views/AdminMenu.fxml");
    }
}
