-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2018 a las 23:43:06
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
-- Base de datos: `cementerio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ajuste_inventario`
--

CREATE TABLE `ajuste_inventario` (
  `cod_ajuste` int(10) NOT NULL,
  `fecha_ajuste` date NOT NULL,
  `cod_producto` int(5) NOT NULL,
  `observacion` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `cantidad` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `anexo_contrato`
--

CREATE TABLE `anexo_contrato` (
  `cod_contrato` int(5) NOT NULL,
  `run_trabajador` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `descripcion` varchar(20) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aranceles`
--

CREATE TABLE `aranceles` (
  `cod_arancel` int(5) NOT NULL,
  `detalle_arancel` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `valor_arancel` varchar(45) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `run_cliente` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `dv_cliente` varchar(1) COLLATE utf8_spanish2_ci NOT NULL,
  `nom_cliente` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_pat_cliente` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_mat_cliente` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `dir_cliente` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fono_cliente` varchar(12) COLLATE utf8_spanish2_ci NOT NULL,
  `correo_cliente` varchar(100) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_sepultacion`
--

CREATE TABLE `detalle_sepultacion` (
  `cod_sepultura` int(5) NOT NULL,
  `run_sepultado` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_sepultacion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `cod_sepultura` int(5) NOT NULL,
  `cod_venta` int(5) NOT NULL,
  `cod_arancel` int(5) NOT NULL,
  `descuento` int(4) NOT NULL,
  `tipo_pago` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `rut_empresa` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `dv_empresa` varchar(1) COLLATE utf8_spanish2_ci NOT NULL,
  `nombre_empresa` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `giro_empresa` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `comuna_empresa` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ciudad_empresa` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fono_empresa` varchar(12) COLLATE utf8_spanish2_ci NOT NULL,
  `correo_empresa` varchar(100) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `nro_factura` int(10) NOT NULL,
  `rut_empresa` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_emision` date NOT NULL,
  `forma_pago` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `valor_iva` int(6) NOT NULL,
  `estado` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `valor_neto_factura` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gastos`
--

CREATE TABLE `gastos` (
  `cod_gasto` int(5) NOT NULL,
  `fecha_gasto` date NOT NULL,
  `tipo_venta` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `valor_total` int(7) NOT NULL,
  `nro_factura` int(10) NOT NULL,
  `descripcion` varchar(45) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_detalle`
--

CREATE TABLE `linea_detalle` (
  `nro_factura` int(10) NOT NULL,
  `cod_producto` int(5) NOT NULL,
  `cantidad` int(10) NOT NULL,
  `precio_unitario` int(6) NOT NULL,
  `descuento` int(5) NOT NULL,
  `valor_total_linea` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos_y_abonos`
--

CREATE TABLE `pagos_y_abonos` (
  `cod_pago` int(5) NOT NULL,
  `fecha_pago` date NOT NULL,
  `tipo_transaccion` varchar(5) COLLATE utf8_spanish2_ci NOT NULL,
  `monto_pago` int(6) NOT NULL,
  `cod_venta` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `cod_producto` int(5) NOT NULL,
  `nom_producto` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `descripcion` varchar(30) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sepultados`
--

CREATE TABLE `sepultados` (
  `run_sepultado` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `dv_sepultado` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `nom_sepultado` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_pat_sepultado` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_mat_sepultado` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_nac_sepultado` date NOT NULL,
  `fecha_defun_sepultado` date NOT NULL,
  `fecha_sepultacion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `sepultados`
--

INSERT INTO `sepultados` (`run_sepultado`, `dv_sepultado`, `nom_sepultado`, `ape_pat_sepultado`, `ape_mat_sepultado`, `fecha_nac_sepultado`, `fecha_defun_sepultado`, `fecha_sepultacion`) VALUES
('18161779', '9', 'DFGDFGDF', 'SDGSDGSD', 'DFGDFGDFG', '1992-05-01', '2000-09-20', '2000-09-21'),
('18161888', '4', 'DASDASD', 'ASDASD', 'ASDAS', '2018-11-09', '0000-00-00', '0000-00-00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sepultura`
--

CREATE TABLE `sepultura` (
  `cod_sepultura` int(5) NOT NULL,
  `tipo_sepultura` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `estado_sepultura` varchar(8) COLLATE utf8_spanish2_ci NOT NULL,
  `ubicacion_sepultura` varchar(45) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajadores`
--

CREATE TABLE `trabajadores` (
  `run_trabajador` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `dv_trabajador` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `nombre_trabajador` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_pat_trabajador` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `ape_mat_trabajador` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `cargo_trabajador` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha_nac_trabajador` date NOT NULL,
  `direccion` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `fono` varchar(12) COLLATE utf8_spanish2_ci NOT NULL,
  `correo_trabajador` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `cod_contrato` int(5) NOT NULL,
  `tipo_contrato` char(2) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `trabajadores`
--

INSERT INTO `trabajadores` (`run_trabajador`, `dv_trabajador`, `nombre_trabajador`, `ape_pat_trabajador`, `ape_mat_trabajador`, `cargo_trabajador`, `fecha_nac_trabajador`, `direccion`, `fono`, `correo_trabajador`, `cod_contrato`, `tipo_contrato`) VALUES
('1111', '1', 'SDFSDF', 'DSFSDF', 'SDFSDF', 'SDFSF', '2000-00-00', 'sdasasd', 'sdfsdfsdf', 'dsfsdf', 1, 'PI');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `nombre_usuario` varchar(15) COLLATE utf8_spanish2_ci NOT NULL,
  `tipo_usuario` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `pass_usuario` varchar(45) COLLATE utf8_spanish2_ci NOT NULL,
  `run_trabajador` varchar(10) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `cod_venta` int(5) NOT NULL,
  `fecha_venta` date NOT NULL,
  `monto_total` int(6) NOT NULL,
  `run_cliente` varchar(10) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ajuste_inventario`
--
ALTER TABLE `ajuste_inventario`
  ADD PRIMARY KEY (`cod_ajuste`),
  ADD KEY `cod_producto` (`cod_producto`);

--
-- Indices de la tabla `anexo_contrato`
--
ALTER TABLE `anexo_contrato`
  ADD PRIMARY KEY (`cod_contrato`,`run_trabajador`),
  ADD KEY `run_trabajador` (`run_trabajador`);

--
-- Indices de la tabla `aranceles`
--
ALTER TABLE `aranceles`
  ADD PRIMARY KEY (`cod_arancel`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`run_cliente`);

--
-- Indices de la tabla `detalle_sepultacion`
--
ALTER TABLE `detalle_sepultacion`
  ADD PRIMARY KEY (`cod_sepultura`,`run_sepultado`),
  ADD KEY `run_sepultado` (`run_sepultado`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`cod_sepultura`,`cod_venta`,`cod_arancel`),
  ADD KEY `cod_arancel` (`cod_arancel`),
  ADD KEY `cod_venta` (`cod_venta`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`rut_empresa`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`nro_factura`),
  ADD KEY `rut_empresa` (`rut_empresa`);

--
-- Indices de la tabla `gastos`
--
ALTER TABLE `gastos`
  ADD PRIMARY KEY (`cod_gasto`),
  ADD KEY `nro_factura` (`nro_factura`);

--
-- Indices de la tabla `linea_detalle`
--
ALTER TABLE `linea_detalle`
  ADD PRIMARY KEY (`nro_factura`,`cod_producto`),
  ADD KEY `cod_producto` (`cod_producto`);

--
-- Indices de la tabla `pagos_y_abonos`
--
ALTER TABLE `pagos_y_abonos`
  ADD PRIMARY KEY (`cod_pago`),
  ADD KEY `cod_venta` (`cod_venta`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cod_producto`);

--
-- Indices de la tabla `sepultados`
--
ALTER TABLE `sepultados`
  ADD PRIMARY KEY (`run_sepultado`);

--
-- Indices de la tabla `sepultura`
--
ALTER TABLE `sepultura`
  ADD PRIMARY KEY (`cod_sepultura`);

--
-- Indices de la tabla `trabajadores`
--
ALTER TABLE `trabajadores`
  ADD PRIMARY KEY (`run_trabajador`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`nombre_usuario`),
  ADD KEY `run_trabajador` (`run_trabajador`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`cod_venta`),
  ADD KEY `run_cliente` (`run_cliente`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ajuste_inventario`
--
ALTER TABLE `ajuste_inventario`
  ADD CONSTRAINT `ajuste_inventario_ibfk_1` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`);

--
-- Filtros para la tabla `anexo_contrato`
--
ALTER TABLE `anexo_contrato`
  ADD CONSTRAINT `anexo_contrato_ibfk_1` FOREIGN KEY (`run_trabajador`) REFERENCES `trabajadores` (`run_trabajador`);

--
-- Filtros para la tabla `detalle_sepultacion`
--
ALTER TABLE `detalle_sepultacion`
  ADD CONSTRAINT `detalle_sepultacion_ibfk_1` FOREIGN KEY (`run_sepultado`) REFERENCES `sepultados` (`run_sepultado`),
  ADD CONSTRAINT `detalle_sepultacion_ibfk_2` FOREIGN KEY (`cod_sepultura`) REFERENCES `sepultura` (`cod_sepultura`);

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`cod_sepultura`) REFERENCES `sepultura` (`cod_sepultura`),
  ADD CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`cod_arancel`) REFERENCES `aranceles` (`cod_arancel`),
  ADD CONSTRAINT `detalle_venta_ibfk_3` FOREIGN KEY (`cod_venta`) REFERENCES `venta` (`cod_venta`);

--
-- Filtros para la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`rut_empresa`) REFERENCES `empresa` (`rut_empresa`);

--
-- Filtros para la tabla `gastos`
--
ALTER TABLE `gastos`
  ADD CONSTRAINT `gastos_ibfk_1` FOREIGN KEY (`nro_factura`) REFERENCES `facturas` (`nro_factura`);

--
-- Filtros para la tabla `linea_detalle`
--
ALTER TABLE `linea_detalle`
  ADD CONSTRAINT `linea_detalle_ibfk_1` FOREIGN KEY (`nro_factura`) REFERENCES `facturas` (`nro_factura`),
  ADD CONSTRAINT `linea_detalle_ibfk_2` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`);

--
-- Filtros para la tabla `pagos_y_abonos`
--
ALTER TABLE `pagos_y_abonos`
  ADD CONSTRAINT `pagos_y_abonos_ibfk_1` FOREIGN KEY (`cod_venta`) REFERENCES `venta` (`cod_venta`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`run_trabajador`) REFERENCES `trabajadores` (`run_trabajador`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`run_cliente`) REFERENCES `cliente` (`run_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
