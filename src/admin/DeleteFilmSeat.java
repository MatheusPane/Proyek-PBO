/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteFilmSeat {
    static Connection conn;

    // Fungsi untuk menghapus data Film
    public static void deleteFilm() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        conn = DatabaseConnection.getDBConnection();

        if (conn != null) {
            try {
                System.out.println("========== HAPUS DATA FILM ==========");
                System.out.print("ID Film yang akan dihapus: ");
                int idFilm = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                System.out.println("\n=====================================");

                String query = "DELETE FROM film WHERE id_film = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idFilm);
                    int rowsDeleted = stmt.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        System.out.println("Data film berhasil dihapus!");
                    } else {
                        System.out.println("ID Film tidak ditemukan.");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                if (conn != null) conn.close();
            }
        }
    }

    // Fungsi untuk menghapus data Kursi
    public static void deleteKursi() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        conn = DatabaseConnection.getDBConnection();

        if (conn != null) {
            try {
                System.out.println("========== HAPUS DATA KURSI ==========");
                System.out.print("ID Kursi yang akan dihapus: ");
                int idKursi = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                System.out.println("\n======================================");

                String query = "DELETE FROM kursi WHERE id_kursi = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idKursi);
                    int rowsDeleted = stmt.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        System.out.println("Data kursi berhasil dihapus!");
                    } else {
                        System.out.println("ID Kursi tidak ditemukan.");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                if (conn != null) conn.close();
            }
        }
    }
}
