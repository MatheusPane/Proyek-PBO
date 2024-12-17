/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package admin;

import java.sql.*;

public class JavaToMySQL {

    static Connection conn;
    static PreparedStatement stmt;

    public static void main(String[] args) {
        try {
            // Contoh penggunaan fungsi CRUD
            tampilFilm();
            tampilKursi();

            // Tambahkan film
            tambahkanFilm("Avatar 2", "Sci-Fi", 180, "James Cameron", 2022);

            // Tambahkan kursi
            tambahkanKursi("A10", "tersedia");

            // Update film
            updateFilm(1, "Avatar: The Way of Water", "Adventure", 190, "James Cameron", 2022);

            // Update kursi
            updateKursi(1, "B10", "tidak tersedia");

            // Hapus film
            deleteFilm(1);

            // Hapus kursi
            deleteKursi(1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ====== CRUD METHODS FOR FILM ======

        static void tampilFilm() throws SQLException {
           try {
               conn = DatabaseConnection.getDBConnection();
               System.out.println("Koneksi berhasil.");

               String query = "SELECT * FROM film";
               System.out.println("Menjalankan query: " + query);
               stmt = conn.prepareStatement(query);
               ResultSet resultSet = stmt.executeQuery();

               System.out.println("=========================================================");
               System.out.println("DAFTAR FILM");
               System.out.println("=========================================================");

               if (!resultSet.isBeforeFirst()) {
                   System.out.println("Tidak ada data film yang tersedia.");
               }

               while (resultSet.next()) {
                   System.out.println("ID          : " + resultSet.getInt("id"));
                   System.out.println("Judul Film  : " + resultSet.getString("judul"));
                   System.out.println("Genre       : " + resultSet.getString("genre"));
                   System.out.println("Durasi      : " + resultSet.getInt("durasi") + " menit");
                   System.out.println("Sutradara   : " + resultSet.getString("sutradara"));
                   System.out.println("=========================================================");
               }
           } finally {
               closeResources();
           }
       }


    static void tambahkanFilm(String judulFilm, String genre, int durasi, String sutradara, int tahunRilis) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "INSERT INTO film (judul, genre, durasi, sutradara, tahun_rilis) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, judulFilm);
            stmt.setString(2, genre);
            stmt.setInt(3, durasi);
            stmt.setString(4, sutradara);
            stmt.setInt(5, tahunRilis); // Tambahkan tahun rilis
            stmt.executeUpdate();
            System.out.println("Film berhasil ditambahkan.");
        } finally {
            closeResources();
        }
    }

    static void updateFilm(int id, String judulFilm, String genre, int durasi, String sutradara, int tahunRilis) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "UPDATE film SET judul = ?, genre = ?, durasi = ?, sutradara = ?, tahun_rilis = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, judulFilm);
            stmt.setString(2, genre);
            stmt.setInt(3, durasi);
            stmt.setString(4, sutradara);
            stmt.setInt(5, tahunRilis);
            stmt.setInt(6, id);
            stmt.executeUpdate();
            System.out.println("Film berhasil diperbarui.");
        } finally {
            closeResources();
        }
    }

    static void deleteFilm(int id) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "DELETE FROM film WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Film berhasil dihapus.");
        } finally {
            closeResources();
        }
    }

    // ====== CRUD METHODS FOR KURSI ======

    static void tampilKursi() throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "SELECT * FROM kursi";
            stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            System.out.println("=========================================================");
            System.out.println("DAFTAR KURSI");
            System.out.println("=========================================================");
            while (resultSet.next()) {
                System.out.println("ID         : " + resultSet.getInt("id"));
                System.out.println("Nomor Kursi: " + resultSet.getString("nomor_kursi"));
                System.out.println("Status     : " + resultSet.getString("status"));
                System.out.println("=========================================================");
            }
        } finally {
            closeResources();
        }
    }

    static void tambahkanKursi(String nomorKursi, String status) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "INSERT INTO kursi (nomor_kursi, status) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nomorKursi);
            stmt.setString(2, status);
            stmt.executeUpdate();
            System.out.println("Kursi berhasil ditambahkan.");
        } finally {
            closeResources();
        }
    }

    static void updateKursi(int id, String nomorKursi, String status) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "UPDATE kursi SET nomor_kursi = ?, status = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nomorKursi);
            stmt.setString(2, status);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Kursi berhasil diperbarui.");
        } finally {
            closeResources();
        }
    }

    static void deleteKursi(int id) throws SQLException {
        try {
            conn = DatabaseConnection.getDBConnection();
            String query = "DELETE FROM kursi WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Kursi berhasil dihapus.");
        } finally {
            closeResources();
        }
    }

    // ====== UTILITY ======
    static void closeResources() {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
