package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertKursi {
    public static void insertKursi() throws SQLException {
        Connection conn = DatabaseConnection.getDBConnection();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            System.out.println("========== TAMBAH KURSI ==========");
            System.out.print("Nomor Kursi     : ");
            String nomorKursi = scanner.nextLine();

            // Memasukkan status dengan pilihan 'Tersedia' atau 'Tidak Tersedia'
            System.out.print("Status (Tersedia/Tidak Tersedia) : ");
            String statusInput = scanner.nextLine().trim().toLowerCase();
            boolean status = false;

            // Menentukan status berdasarkan input dari pengguna
            if ("tersedia".equals(statusInput)) {
                status = true;  // Kursi tersedia
            } else if ("tidak tersedia".equals(statusInput)) {
                status = false;  // Kursi tidak tersedia
            } else {
                System.out.println("Status tidak valid. Harap pilih 'Tersedia' atau 'Tidak Tersedia'.");
                return;  // Keluar dari fungsi jika input tidak valid
            }

            System.out.println("================================");

            // Query untuk memasukkan kursi ke dalam database
            String sql = "INSERT INTO kursi (nomor_kursi, status) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nomorKursi);
                stmt.setBoolean(2, status);  // Menyimpan nilai boolean untuk status kursi
                stmt.executeUpdate();
                System.out.println("Kursi berhasil ditambahkan.");
            } finally {
                conn.close();
            }
        } else {
            System.out.println("Koneksi ke database gagal.");
        }
    }
}

