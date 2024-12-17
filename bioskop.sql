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
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomor_kursi VARCHAR(10),
    status VARCHAR(20)
);
