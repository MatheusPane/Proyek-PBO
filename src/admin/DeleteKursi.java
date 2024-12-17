/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteKursi {
    public static void hapusKursi() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            System.out.print("Masukkan ID Kursi yang ingin dihapus: ");
            int idKursi = scanner.nextInt();
            System.out.println("================================");

            String sql = "DELETE FROM kursi WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idKursi);
                stmt.executeUpdate();
                System.out.println("Kursi berhasil dihapus.");
            } finally {
                conn.close();
            }
        }
    }
}
