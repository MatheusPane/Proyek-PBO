package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertFilm {

    /**
     * Method untuk menambahkan film ke database.
     *
     * @param judul Judul film
     * @param genre Genre film
     * @param tahunRilis Tahun rilis film
     * @param durasi Durasi film (dalam menit)
     * @param sutradara Nama sutradara
     * @param jadwalTayang Jadwal tayang film (dalam format 'YYYY-MM-DD HH:MM')
     * @param hargaTiket Harga tiket film
     * @throws SQLException Jika terjadi kesalahan saat berinteraksi dengan database
     */
    static void insertFilm(String judul, String genre, int tahunRilis, int durasi, String sutradara, String jadwalTayang, int hargaTiket) throws SQLException {
        // Mendapatkan koneksi ke database
        Connection conn = DatabaseConnection.getDBConnection();

        if (conn == null) {
            throw new SQLException("Koneksi ke database gagal.");
        }

        // Query untuk memasukkan data film ke tabel
        String sql = "INSERT INTO film (judul, genre, tahun_rilis, durasi, sutradara, jadwal_tayang, harga_tiket) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Mengisi nilai parameter pada query
            stmt.setString(1, judul);
            stmt.setString(2, genre);
            stmt.setInt(3, tahunRilis);
            stmt.setInt(4, durasi);
            stmt.setString(5, sutradara);
            stmt.setString(6, jadwalTayang);  // Jadwal tayang
            stmt.setInt(7, hargaTiket);      // Harga tiket

            // Menjalankan query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected <= 0) {
                throw new SQLException("Gagal menambahkan data film ke database.");
            }
        } catch (SQLException e) {
            throw new SQLException("Kesalahan saat menambahkan film: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close(); // Menutup koneksi database
            }
        }
    }
}
