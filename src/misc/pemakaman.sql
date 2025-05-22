-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 16, 2025 at 06:43 PM
-- Server version: 8.0.30
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pemakaman`
--

-- --------------------------------------------------------
DROP DATABASE pemakaman;

-- 1. Buat Database Baru
CREATE DATABASE pemakaman;
USE pemakaman;

-- 2. Tabel Users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    no_hp VARCHAR(100) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- 3. Tabel Lokasi Makam
CREATE TABLE lokasi_makam (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_lokasi VARCHAR(100) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    kota VARCHAR(100) NOT NULL
);

-- 4. Tabel Blok Makam
CREATE TABLE blok_makam (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lokasi_id INT NOT NULL,
    kode_blok VARCHAR(100) NOT NULL,
    harga BIGINT NOT NULL,
    keterangan VARCHAR(255),
    FOREIGN KEY (lokasi_id) REFERENCES lokasi_makam(id)
);

-- 5. Tabel Petak Makam
CREATE TABLE petak_makam (
    id INT AUTO_INCREMENT PRIMARY KEY,
    blok_id INT NOT NULL,
    nomor_petak VARCHAR(100) NOT NULL,
    status VARCHAR(100) NOT NULL,
    deskripsi VARCHAR(255),
    FOREIGN KEY (blok_id) REFERENCES blok_makam(id)
);

-- 6. Tabel Jenazah
CREATE TABLE jenazah (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nama_jenazah VARCHAR(100) NOT NULL,
    tanggal_wafat DATE NOT NULL,
    tempat_wafat VARCHAR(100) NOT NULL,
    jenis_kelamin VARCHAR(100) NOT NULL,
    umur INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 7. Tabel Transaksi
CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jenazah_id INT NOT NULL,
    tanggal_pemesanan DATE NOT NULL,
    petak_id INT NOT NULL,
    jumlah_bayar INT NOT NULL,
    no_kartu VARCHAR(100) NOT NULL,
    vcc_kartu VARCHAR(100) NOT NULL,
    nama_kartu VARCHAR(100) NOT NULL,
    catatan VARCHAR(255),
    user_input INT NOT NULL,
    FOREIGN KEY (jenazah_id) REFERENCES jenazah(id),
    FOREIGN KEY (petak_id) REFERENCES petak_makam(id),
    FOREIGN KEY (user_input) REFERENCES users(id)
);

-- 8. Tabel Reservasi
CREATE TABLE reservasi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    tanggal_reservasi DATE NOT NULL,
    petak_id INT NOT NULL,
    catatan VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (petak_id) REFERENCES petak_makam(id)
);

-- 9. Tabel Ambulan
CREATE TABLE ambulan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomor_polisi VARCHAR(100) NOT NULL,
    sopir VARCHAR(100) NOT NULL,
    kapasitas INT NOT NULL,
    status VARCHAR(100) NOT NULL,
    keterangan VARCHAR(255)
);

-- 10. Tabel Penyewaan Ambulan
CREATE TABLE penyewaan_ambulan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ambulan_id INT NOT NULL,
    lokasi_jemput VARCHAR(100) NOT NULL,
    lokasi_tujuan VARCHAR(100) NOT NULL,
    waktu_jemput DATETIME NOT NULL,
    status VARCHAR(100) NOT NULL,
    deskripsi VARCHAR(255),
    FOREIGN KEY (ambulan_id) REFERENCES ambulan(id)
);

INSERT INTO `users` (`id`, `nama_lengkap`, `username`, `email`, `no_hp`, `alamat`, `role`, `password`) VALUES
(1, 'Admin', 'admin', 'admin@gmail.com', '085446753221', 'Jalan Jendral Sudirman, No. 12', 'Admin', 'admin'),
(2, 'User', 'user', 'userbaru@gmail.com2', '0868837737732', 'ALAMATTTTTT2', 'Guest', 'user');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
