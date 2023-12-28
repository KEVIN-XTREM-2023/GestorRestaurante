-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-02-2022 a las 00:59:52
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `restaurante`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `CED_PER` varchar(10) NOT NULL,
  `NOM_PER` varchar(30) NOT NULL,
  `APE_PER` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`CED_PER`, `NOM_PER`, `APE_PER`) VALUES
('111', 'sas', 'asas'),
('1801', 'KEVIN', 'SAQUINGA'),
('1802', 'JESSICA', 'TITUAÑA'),
('1803', 'ESTAFANIA', 'PEREZ'),
('1803350097', 'Juan', 'Zapata'),
('1804', 'MARCELO', 'PEREZ'),
('1805', 'MARIA', 'SANCHEZ'),
('1806', 'EDU', 'FLORES'),
('1807', 'RENE', 'TERAN'),
('1808', 'RENE', 'PAREDES'),
('1809', 'ANGEL', 'CAIZAA'),
('1810', 'KEVIN', 'FLORES'),
('1811', 'MATEO', 'GARCIA'),
('1812', 'JORGE', 'MORENO'),
('1850747013', 'KEVIN', 'SAQUINGA'),
('222', 'asa', 'asas');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`CED_PER`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
