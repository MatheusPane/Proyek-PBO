// File: Main.java
package admin;

import controllers.LoginController;
import controllers.RegisterController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private static Stage primaryStage; // Untuk menyimpan primary stage secara global
    private static String currentUserRole; // Untuk menyimpan role pengguna (admin/user)

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage; // Simpan primary stage

        // Muat tampilan login sebagai scene awal
        String fxmlPath = "/views/LoginView.fxml";
        loadScene(fxmlPath, null);

        // Atur properti jendela
        stage.setTitle("Login");
        stage.show();
    }

    public void loadScene(String fxmlPath, Object data) {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Ambil controller dari file FXML
        Object controller = loader.getController();

        // Jika controller memiliki metode setMainApp, atur mainApp
        if (controller instanceof LoginController) {
            ((LoginController) controller).setMainApp(this);
        } else if (controller instanceof RegisterController) {
            ((RegisterController) controller).setMainApp(this);
        }

        // Tambahkan pengaturan untuk controller lainnya jika ada

        // Atur scene dan tampilkan
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void handleLoginSuccess(String role) {
        currentUserRole = role; // Simpan role pengguna
        if ("admin".equalsIgnoreCase(role)) {
            loadScene("/views/AdminMenu.fxml", role);
            primaryStage.setTitle("Menu Admin");
        } else if ("user".equalsIgnoreCase(role)) {
            loadScene("/views/BookingView.fxml", role);
            primaryStage.setTitle("Pemesanan Tiket");
        } else {
            System.err.println("Role tidak dikenali: " + role);
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
