-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-10-2018 a las 22:37:10
-- Versión del servidor: 10.1.24-MariaDB
-- Versión de PHP: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cementerio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `run_cliente` varchar(10) COLLATE utf16_spanish2_ci NOT NULL,
  `dv_cliente` varchar(1) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `nom_cliente` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `ape_pat_cliente` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `ape_mat_cliente` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `dir_cliente` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `fono_cliente` varchar(12) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `correo_cliente` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish2_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`run_cliente`, `dv_cliente`, `nom_cliente`, `ape_pat_cliente`, `ape_mat_cliente`, `dir_cliente`, `fono_cliente`, `correo_cliente`) VALUES
('', '', '', '', '', '', '', ''),
('19764121', '\0', '\0J\0u\0a\0n', '\0P\0e\0r\0e\0z', '\0D\0i\0a\0z', '\0A\0r\0t\0u\0r\0o\0 \0p\0r\0a\0t\0 \02\01\03', '\09\08\08\03\03\04', '\0j\0u\0a\0n\0.\0p\0e\0r\0e\0z\0@\0g\0m\0a\0i\0l\0.\0c\0o\0m'),
('rut', '1', 'name', 'apat', 'amat', 'dir', 'tel', 'corr');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`run_cliente`),
  ADD UNIQUE KEY `run_cliente` (`run_cliente`),
  ADD KEY `run_cliente_2` (`run_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
