    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateKursi {
    public static void updateKursi() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            System.out.print("Masukkan ID Kursi yang ingin diperbarui: ");
            int idKursi = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("========== PERBARUI KURSI ==========");
            System.out.print("Nomor Kursi Baru : ");
            String nomorKursi = scanner.nextLine();
            System.out.print("Status Baru      : ");
            String status = scanner.nextLine();
            System.out.println("================================");

            String sql = "UPDATE kursi SET nomor_kursi = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nomorKursi);
                stmt.setString(2, status);
                stmt.setInt(3, idKursi);
                stmt.executeUpdate();
                System.out.println("Kursi berhasil diperbarui.");
            } finally {
                conn.close();
            }
        }
    }
}
