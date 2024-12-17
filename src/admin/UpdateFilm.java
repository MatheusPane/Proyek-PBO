/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateFilm {
    public static void updateFilm() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            try {
                System.out.print("Masukkan ID Film yang ingin diperbarui: ");
                int idFilm = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.println("========== PERBARUI FILM ==========");
                System.out.print("Judul Film Baru : ");
                String judulFilm = scanner.nextLine();
                System.out.print("Genre Baru      : ");
                String genre = scanner.nextLine();
                System.out.print("Durasi Baru     : ");
                int durasi = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Sutradara Baru  : ");
                String sutradara = scanner.nextLine();
                System.out.println("\n================================");

                String sql = "UPDATE film SET judul = ?, genre = ?, durasi = ?, sutradara = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, judulFilm);
                    stmt.setString(2, genre);
                    stmt.setInt(3, durasi);
                    stmt.setString(4, sutradara);
                    stmt.setInt(5, idFilm);
                    stmt.executeUpdate();
                    System.out.println("Film berhasil diperbarui.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                conn.close();
            }
        }
    }
}
