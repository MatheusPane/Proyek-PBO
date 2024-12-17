package admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(String fxmlPath) {
        try {
            // Mengambil primary stage dari Main
            Stage stage = Main.getPrimaryStage();

            // Muat file FXML
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxmlPath));
            Scene scene = new Scene(root);

            // Atur scene baru ke primary stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Gagal memuat file FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
