-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: May 06, 2020 at 03:13 PM
-- Server version: 5.7.28
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sensor_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `sensor_list`
--

DROP TABLE IF EXISTS `sensor_list`;
CREATE TABLE IF NOT EXISTS `sensor_list` (
  `sensorId` int(11) NOT NULL AUTO_INCREMENT,
  `sensorNo` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `floorNo` int(11) NOT NULL,
  `roomNo` int(11) NOT NULL,
  `smokeLevel` float NOT NULL,
  `co2Level` float NOT NULL,
  PRIMARY KEY (`sensorId`),
  UNIQUE KEY `sensorNo` (`sensorNo`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sensor_list`
--

INSERT INTO `sensor_list` (`sensorId`, `sensorNo`, `active`, `floorNo`, `roomNo`, `smokeLevel`, `co2Level`) VALUES
(11, 5, 1, 5, 5, 1, 6),
(6, 1, 1, 2, 5, 0, 0),
(9, 2, 1, 2, 3, 0, 3),
(12, 3, 0, 4, 1, 0, 6),
(14, 4, 1, 5, 2, 1, 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
