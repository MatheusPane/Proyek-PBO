package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bioskop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getDBConnection() throws SQLException {
        try {
            System.out.println("Mencoba membuka koneksi ke database...");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Error menutup koneksi: " + e.getMessage());
            }
        }
    }
}
