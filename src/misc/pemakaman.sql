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

--
-- Table structure for table `ambulan`
--

CREATE TABLE `ambulan` (
  `id` int NOT NULL,
  `nomor_polisi` varchar(100) NOT NULL,
  `sopir` varchar(100) NOT NULL,
  `kapasitas` int NOT NULL,
  `status` varchar(100) NOT NULL,
  `keterangan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `blok_makam`
--

CREATE TABLE `blok_makam` (
  `id` int NOT NULL,
  `lokasi_id` int NOT NULL,
  `kode_blok` varchar(100) NOT NULL,
  `harga` bigint NOT NULL,
  `keterangan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `jenazah`
--

CREATE TABLE `jenazah` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `nama_jenazah` varchar(100) NOT NULL,
  `tanggal_wafat` date NOT NULL,
  `tempat_wafat` varchar(100) NOT NULL,
  `jenis_kelamin` varchar(100) NOT NULL,
  `umur` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_makam`
--

CREATE TABLE `lokasi_makam` (
  `id` int NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `kota` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `penyewaan_ambulan`
--

CREATE TABLE `penyewaan_ambulan` (
  `id` int NOT NULL,
  `ambulan_id` int NOT NULL,
  `lokasi_jemput` varchar(100) NOT NULL,
  `lokasi_tujuan` varchar(100) NOT NULL,
  `waktu_jemput` datetime NOT NULL,
  `status` varchar(100) NOT NULL,
  `deskripsi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `petak_makam`
--

CREATE TABLE `petak_makam` (
  `id` int NOT NULL,
  `blok_id` int NOT NULL,
  `nomor_petak` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `deskripsi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservasi`
--

CREATE TABLE `reservasi` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `tanggal_reservasi` date NOT NULL,
  `petak_id` int NOT NULL,
  `catatan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int NOT NULL,
  `jenazah_id` int NOT NULL,
  `tanggal_pemesanan` date NOT NULL,
  `petak_id` int NOT NULL,
  `jumlah_bayar` int NOT NULL,
  `no_kartu` varchar(100) NOT NULL,
  `vcc_kartu` varchar(100) NOT NULL,
  `nama_kartu` varchar(100) NOT NULL,
  `catatan` varchar(255) NOT NULL,
  `user_input` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `no_hp` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `role` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama_lengkap`, `username`, `email`, `no_hp`, `alamat`, `role`, `password`) VALUES
(1, 'Admin Ganteng', 'admin', 'admin@gmail.com', '085446753221', 'Jalan Jendral Sudirman, No. 12', 'admin', 'admin'),
(2, 'User baru2', 'userbaru2', 'userbaru@gmail.com2', '0868837737732', 'ALAMATTTTTT2', 'Admin', 'userbaru2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ambulan`
--
ALTER TABLE `ambulan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `blok_makam`
--
ALTER TABLE `blok_makam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jenazah`
--
ALTER TABLE `jenazah`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lokasi_makam`
--
ALTER TABLE `lokasi_makam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `penyewaan_ambulan`
--
ALTER TABLE `penyewaan_ambulan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `petak_makam`
--
ALTER TABLE `petak_makam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ambulan`
--
ALTER TABLE `ambulan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `blok_makam`
--
ALTER TABLE `blok_makam`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `jenazah`
--
ALTER TABLE `jenazah`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lokasi_makam`
--
ALTER TABLE `lokasi_makam`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `penyewaan_ambulan`
--
ALTER TABLE `penyewaan_ambulan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `petak_makam`
--
ALTER TABLE `petak_makam`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
