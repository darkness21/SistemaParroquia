-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-10-2018 a las 18:17:28
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cementerioparroquial`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `run_cliente` varchar(10) COLLATE utf16_spanish2_ci NOT NULL,
  `dv_cliente` varchar(1) COLLATE utf16_spanish2_ci NOT NULL,
  `nom_cliente` varchar(45) COLLATE utf16_spanish2_ci NOT NULL,
  `ape_pat_cliente` varchar(45) COLLATE utf16_spanish2_ci NOT NULL,
  `ape_mat_cliente` varchar(45) COLLATE utf16_spanish2_ci NOT NULL,
  `dir_cliente` varchar(45) COLLATE utf16_spanish2_ci NOT NULL,
  `fono_cliente` varchar(12) COLLATE utf16_spanish2_ci NOT NULL,
  `correo_cliente` varchar(100) COLLATE utf16_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish2_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`run_cliente`, `dv_cliente`, `nom_cliente`, `ape_pat_cliente`, `ape_mat_cliente`, `dir_cliente`, `fono_cliente`, `correo_cliente`) VALUES
('19764121', '6', 'Juan', 'Perez', 'Diaz', 'Arturo prat 213', '988334522', 'juan.perez@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`run_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
