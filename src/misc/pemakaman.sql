-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2025 at 05:26 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
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
  `id` int(11) NOT NULL,
  `nomor_polisi` varchar(100) NOT NULL,
  `sopir` varchar(100) NOT NULL,
  `kapasitas` int(11) NOT NULL,
  `status` varchar(100) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ambulan`
--

INSERT INTO `ambulan` (`id`, `nomor_polisi`, `sopir`, `kapasitas`, `status`, `keterangan`) VALUES
(2, 'B 2222 KKK', 'Sarip', 4, 'Tersedia', 'Ambulan kecamatan'),
(3, 'B 3333 KLM', 'Ulil', 5, 'Tersedia', 'sip');

-- --------------------------------------------------------

--
-- Table structure for table `blok_makam`
--

CREATE TABLE `blok_makam` (
  `id` int(11) NOT NULL,
  `lokasi_id` int(11) NOT NULL,
  `kode_blok` varchar(100) NOT NULL,
  `harga` bigint(20) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blok_makam`
--

INSERT INTO `blok_makam` (`id`, `lokasi_id`, `kode_blok`, `harga`, `keterangan`) VALUES
(3, 2, 'B', 3000000, 'Ada layanan kebersihan '),
(4, 2, 'C', 4000000, 'Free kembang + air');

-- --------------------------------------------------------

--
-- Table structure for table `jenazah`
--

CREATE TABLE `jenazah` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `nama_jenazah` varchar(100) NOT NULL,
  `tanggal_wafat` date NOT NULL,
  `tempat_wafat` varchar(100) NOT NULL,
  `jenis_kelamin` varchar(100) NOT NULL,
  `umur` int(11) NOT NULL,
  `status_pemakaman` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jenazah`
--

INSERT INTO `jenazah` (`id`, `user_id`, `nama_jenazah`, `tanggal_wafat`, `tempat_wafat`, `jenis_kelamin`, `umur`, `status_pemakaman`) VALUES
(1, 1, 'abdul', '2025-05-23', 'Bekasi', 'Laki-Laki', 30, 0),
(2, 1, 'Joko bin Pulan', '2025-05-24', 'Jakarta', 'Laki-Laki', 24, 1),
(3, 2, 'Ulil', '2025-05-24', 'Bekasi', 'Laki-Laki', 30, 1);

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_makam`
--

CREATE TABLE `lokasi_makam` (
  `id` int(11) NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `kota` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lokasi_makam`
--

INSERT INTO `lokasi_makam` (`id`, `nama_lokasi`, `alamat`, `kota`) VALUES
(2, 'TPU Rawa Aren', 'jalan bekasi aja', 'Bekasi');

-- --------------------------------------------------------

--
-- Table structure for table `penyewaan_ambulan`
--

CREATE TABLE `penyewaan_ambulan` (
  `id` int(11) NOT NULL,
  `ambulan_id` int(11) NOT NULL,
  `lokasi_jemput` varchar(100) NOT NULL,
  `lokasi_tujuan` varchar(100) NOT NULL,
  `waktu_jemput` datetime NOT NULL,
  `status` varchar(100) NOT NULL,
  `deskripsi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyewaan_ambulan`
--

INSERT INTO `penyewaan_ambulan` (`id`, `ambulan_id`, `lokasi_jemput`, `lokasi_tujuan`, `waktu_jemput`, `status`, `deskripsi`) VALUES
(2, 3, 'Bekasi', 'Bandung', '2025-05-24 22:07:02', 'Selesai', 'cepetan');

-- --------------------------------------------------------

--
-- Table structure for table `petak_makam`
--

CREATE TABLE `petak_makam` (
  `id` int(11) NOT NULL,
  `blok_id` int(11) NOT NULL,
  `nomor_petak` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `deskripsi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petak_makam`
--

INSERT INTO `petak_makam` (`id`, `blok_id`, `nomor_petak`, `status`, `deskripsi`) VALUES
(2, 3, 'B1', 'Terisi', 'Paling pojok '),
(3, 4, 'C1', 'Terisi', 'Deket tukang kembang'),
(4, 3, 'B2', 'Kosong', 'Deket tukang gorengan');

-- --------------------------------------------------------

--
-- Table structure for table `reservasi`
--

CREATE TABLE `reservasi` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `tanggal_reservasi` date NOT NULL,
  `petak_id` int(11) NOT NULL,
  `catatan` varchar(255) DEFAULT NULL,
  `jumlah_bayar` bigint(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL,
  `jenazah_id` int(11) NOT NULL,
  `tanggal_pemesanan` date NOT NULL,
  `petak_id` int(11) NOT NULL,
  `jumlah_bayar` int(11) NOT NULL,
  `no_kartu` varchar(100) NOT NULL,
  `vcc_kartu` varchar(100) NOT NULL,
  `nama_kartu` varchar(100) NOT NULL,
  `catatan` varchar(255) DEFAULT NULL,
  `user_input` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `jenazah_id`, `tanggal_pemesanan`, `petak_id`, `jumlah_bayar`, `no_kartu`, `vcc_kartu`, `nama_kartu`, `catatan`, `user_input`) VALUES
(1, 2, '2025-05-23', 2, 3000000, '123456', '333', 'Ali', 'Sip', 1),
(3, 3, '2025-05-23', 3, 4000000, '1111111111', '111', 'aa', 'ok	', 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `no_hp` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `role` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama_lengkap`, `username`, `email`, `no_hp`, `alamat`, `role`, `password`) VALUES
(1, 'Admin', 'admin', 'admin@gmail.com', '085446753221', 'Jalan Jendral Sudirman, No. 12', 'Admin', 'admin'),
(2, 'User', 'user', 'userbaru@gmail.com2', '0868837737732', 'ALAMATTTTTT2', 'Guest', 'user'),
(3, 'Fira', 'fira', 'fira@gmail.com', '021', '', 'Guest', 'fira'),
(4, 'Lily', 'lily', 'lily@gmail.com', '021', '', 'Guest', 'lily');

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `lokasi_id` (`lokasi_id`);

--
-- Indexes for table `jenazah`
--
ALTER TABLE `jenazah`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `lokasi_makam`
--
ALTER TABLE `lokasi_makam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `penyewaan_ambulan`
--
ALTER TABLE `penyewaan_ambulan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ambulan_id` (`ambulan_id`);

--
-- Indexes for table `petak_makam`
--
ALTER TABLE `petak_makam`
  ADD PRIMARY KEY (`id`),
  ADD KEY `blok_id` (`blok_id`);

--
-- Indexes for table `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `petak_id` (`petak_id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jenazah_id` (`jenazah_id`),
  ADD KEY `petak_id` (`petak_id`),
  ADD KEY `user_input` (`user_input`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `blok_makam`
--
ALTER TABLE `blok_makam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jenazah`
--
ALTER TABLE `jenazah`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `lokasi_makam`
--
ALTER TABLE `lokasi_makam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `penyewaan_ambulan`
--
ALTER TABLE `penyewaan_ambulan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `petak_makam`
--
ALTER TABLE `petak_makam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blok_makam`
--
ALTER TABLE `blok_makam`
  ADD CONSTRAINT `blok_makam_ibfk_1` FOREIGN KEY (`lokasi_id`) REFERENCES `lokasi_makam` (`id`);

--
-- Constraints for table `jenazah`
--
ALTER TABLE `jenazah`
  ADD CONSTRAINT `jenazah_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `penyewaan_ambulan`
--
ALTER TABLE `penyewaan_ambulan`
  ADD CONSTRAINT `penyewaan_ambulan_ibfk_1` FOREIGN KEY (`ambulan_id`) REFERENCES `ambulan` (`id`);

--
-- Constraints for table `petak_makam`
--
ALTER TABLE `petak_makam`
  ADD CONSTRAINT `petak_makam_ibfk_1` FOREIGN KEY (`blok_id`) REFERENCES `blok_makam` (`id`);

--
-- Constraints for table `reservasi`
--
ALTER TABLE `reservasi`
  ADD CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`petak_id`) REFERENCES `petak_makam` (`id`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`jenazah_id`) REFERENCES `jenazah` (`id`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`petak_id`) REFERENCES `petak_makam` (`id`),
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`user_input`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
