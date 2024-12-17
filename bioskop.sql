
CREATE DATABASE bioskop;
USE bioskop;

CREATE TABLE film (
    id INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(100),
    genre VARCHAR(50),
    durasi INT,
    sutradara VARCHAR(100)
);
CREATE TABLE kursi (
    id SERIAL PRIMARY KEY,
    id_film INT NOT NULL,
    jadwal_tayang DATETIME,
    harga_tiket INT,
    nomor_kursi VARCHAR(5) NOT NULL,
    status BOOLEAN DEFAULT TRUE,  -- TRUE: tersedia, FALSE: sudah dipesan
    FOREIGN KEY (id_film) REFERENCES film(id)
);

CREATE TABLE pengguna (
    id INT AUTO_INCREMENT PRIMARY KEY,         -- Primary key dengan auto increment
    username VARCHAR(50) NOT NULL UNIQUE,      -- Kolom username, harus unik dan tidak boleh null
    password VARCHAR(255) NOT NULL,            -- Kolom password, dienkripsi (misalnya dengan bcrypt)
    role ENUM('admin', 'user') NOT NULL,       -- Peran pengguna: admin atau user
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp untuk mencatat waktu pendaftaran
);
CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    film_id INT,
    nama_pembeli VARCHAR(100) NOT NULL,
    jadwal DATETIME NOT NULL,
    jumlah_tiket INT NOT NULL,
    total_harga INT NOT NULL,
    waktu_pemesanan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nomor_pemesanan VARCHAR(20),
    FOREIGN KEY (film_id) REFERENCES film(id)
);
