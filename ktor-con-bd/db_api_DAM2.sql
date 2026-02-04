-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 16-10-2025 a las 19:16:55
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_api_DAM2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `dni` varchar(10) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `clave` varchar(20) DEFAULT NULL,
  `tfno` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`dni`, `nombre`, `clave`, `tfno`) VALUES
('1001A', 'DAM2', '1234', '555 2125'),
('100A', 'DAM2', '1234', '555 2125'),
('10J', 'Asier', '1234', '5558592'),
('11K', 'José María', '1234', '2723832'),
('12L', 'Juán Ramón', '1234', '555425423'),
('13M', 'Ángel', '1234', '555 29223'),
('14N', 'Ismael', '1234', '555 4589645'),
('16A', 'Sergio', '1234', '555 28383'),
('17B', 'José', '1234', '555 29392'),
('19T', 'Mar', '1234', '555 23939'),
('1A', 'Óscar', '123', '5554547'),
('20G', 'Juán María', '123', '555 291039'),
('2B', 'Celia', '123', '5559752'),
('33C', 'Oliver', '1234', '555 34543'),
('388C', 'Paula', '1234', '55354354534'),
('3C', 'Hamza', '1234', '555 34543'),
('4c', 'Chewaca', '1234', '555 454554'),
('4D', 'Adolfo', '1324', '2382932'),
('55d', 'Elizabeth', '654', '5558592'),
('5c', 'Daniel', '1234', '555 454554'),
('5E', 'Yoda', '1234', '2389392'),
('6F', 'R2D2', '12', '555564894'),
('7G', 'Gandalf', '1234', '55512447'),
('8k', 'Bilbo', '1212', '818818818');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Usuario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolesasignados`
--

CREATE TABLE `rolesasignados` (
  `idra` int(11) NOT NULL,
  `dniRol` varchar(10) DEFAULT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rolesasignados`
--

INSERT INTO `rolesasignados` (`idra`, `dniRol`, `idRol`) VALUES
(1, '10J', 1),
(2, '10J', 2),
(3, '11K', 2),
(4, '2B', 1),
(5, '1A', 1),
(6, '1A', 2),
(7, '3C', 2),
(8, '4D', 2),
(9, '5E', 2),
(10, '6F', 2),
(11, '6F', 1),
(12, '7G', 2),
(13, '8H', 2),
(14, '9I', 2),
(15, '12L', 2),
(16, '13M', 2),
(17, '14N', 2),
(18, '16A', 2),
(19, '17B', 2),
(20, '19T', 2),
(21, '20G', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rolesasignados`
--
ALTER TABLE `rolesasignados`
  ADD PRIMARY KEY (`idra`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `rolesasignados`
--
ALTER TABLE `rolesasignados`
  MODIFY `idra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
