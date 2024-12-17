/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteFilm {
    public static void hapusFilm() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            try {
                System.out.print("Masukkan ID Film yang ingin dihapus: ");
                int idFilm = scanner.nextInt();

                String sql = "DELETE FROM film WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, idFilm);
                    stmt.executeUpdate();
                    System.out.println("Film berhasil dihapus.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                conn.close();
            }
        }
    }
}

