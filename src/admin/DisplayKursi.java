/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayKursi {
    public static void tampilKursi() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        
        if (conn != null) {
            String sql = "SELECT * FROM kursi";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet resultSet = stmt.executeQuery()) {
                System.out.println("=============================================");
                System.out.println("DAFTAR KURSI");
                System.out.println("=============================================");

                while (resultSet.next()) {
                    System.out.println("ID Kursi      : " + resultSet.getInt("id"));
                    System.out.println("Nomor Kursi    : " + resultSet.getString("nomor_kursi"));
                    System.out.println("Status         : " + resultSet.getString("status"));
                    System.out.println("=========================================");
                }
            } finally {
                conn.close();
            }
        }
    }
}
