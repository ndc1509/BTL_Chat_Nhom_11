-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3308
-- Thời gian đã tạo: Th10 06, 2021 lúc 11:19 AM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `chat`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--
DROP DATABASE IF EXISTS `chat`;
CREATE DATABASE chat;
USE chat;
CREATE TABLE `account` (
  `username` varchar(255) CHARACTER SET latin1 NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `isOnline` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`username`, `password`, `isOnline`) VALUES
('admin', '1', 0),
('cuong', '1', 0),
('duc', '1', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `client`
--

CREATE TABLE `client` (
  `Id` int(11) NOT NULL,
  `nick_name` varchar(50) NOT NULL,
  `user_name` varchar(255) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `client`
--

INSERT INTO `client` (`Id`, `nick_name`, `user_name`) VALUES
(1, 'Nickname_admin', 'admin'),
(2, 'Nickname_duc', 'duc'),
(3, 'Nickname_cuong', 'cuong');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `friendship1`
--

CREATE TABLE `friendship1` (
  `account1` varchar(255) CHARACTER SET latin1 NOT NULL,
  `account2` varchar(255) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `friendship1`
--

INSERT INTO `friendship1` (`account1`, `account2`) VALUES
('admin', 'cuong'),
('admin', 'duc'),
('duc', 'admin'),
('cuong', 'duc'),
('duc', 'cuong');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`username`);

--
-- Chỉ mục cho bảng `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `client_pass` (`user_name`);

--
-- Chỉ mục cho bảng `friendship1`
--
ALTER TABLE `friendship1`
  ADD KEY `account1` (`account1`),
  ADD KEY `account2` (`account2`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `client`
--
ALTER TABLE `client`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_pass` FOREIGN KEY (`user_name`) REFERENCES `account` (`username`);

--
-- Các ràng buộc cho bảng `friendship1`
--
ALTER TABLE `friendship1`
  ADD CONSTRAINT `friendship1_ibfk_2` FOREIGN KEY (`account2`) REFERENCES `account` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
