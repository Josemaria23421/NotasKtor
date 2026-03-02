-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-02-2026 a las 14:38:21
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `misnotas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `items_tarea`
--

CREATE TABLE `items_tarea` (
  `id` int(11) NOT NULL,
  `nota_id` int(11) NOT NULL,
  `nombre_item` varchar(255) NOT NULL,
  `esta_finalizado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `items_tarea`
--

INSERT INTO `items_tarea` (`id`, `nota_id`, `nombre_item`, `esta_finalizado`) VALUES
(2, 2, 'Jabón', 1),
(3, 2, 'Pollo', 0),
(4, 1, 'Hacer la compra', 0),
(5, 1, 'Hacer la compra', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notas`
--

CREATE TABLE `notas` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descripcion_completa` text DEFAULT NULL,
  `tipo` enum('TEXTO','TAREAS') NOT NULL,
  `fecha` date NOT NULL,
  `usuario_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `notas`
--

INSERT INTO `notas` (`id`, `titulo`, `descripcion_completa`, `tipo`, `fecha`, `usuario_id`) VALUES
(1, 'Comprar regalo', 'Ir a la tienda de la esquina y comprar un detalle para el cumple', 'TEXTO', '2026-01-23', 1),
(2, 'Lista de la compra', NULL, 'TAREAS', '2026-01-23', 1),
(3, 'Nota de prueba', 'Esta es la descripción larga', 'TEXTO', '2026-01-27', 1),
(4, 'Nota de prueba', 'Esta es la descripción larga', 'TEXTO', '2026-01-27', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `es_usuario` tinyint(1) DEFAULT 1,
  `es_admin` tinyint(1) DEFAULT 0,
  `dni` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `password`, `foto_perfil`, `es_usuario`, `es_admin`, `dni`) VALUES
(1, 'adminPrueba', 'mi_nueva_clave_2026', '', 1, 1, '111111111'),
(2, 'Jose Manuel', 'mypassword123', 'https://tinyurl.com/foto-prueba.jpg', 1, 0, '12345678Z');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `items_tarea`
--
ALTER TABLE `items_tarea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nota` (`nota_id`);

--
-- Indices de la tabla `notas`
--
ALTER TABLE `notas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuario` (`usuario_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `items_tarea`
--
ALTER TABLE `items_tarea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `notas`
--
ALTER TABLE `notas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `items_tarea`
--
ALTER TABLE `items_tarea`
  ADD CONSTRAINT `fk_nota` FOREIGN KEY (`nota_id`) REFERENCES `notas` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `notas`
--
ALTER TABLE `notas`
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
