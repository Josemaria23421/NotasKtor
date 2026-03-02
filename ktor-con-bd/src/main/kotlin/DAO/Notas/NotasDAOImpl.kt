package com.example.DAO.Notas

import Helpers.Database
import com.example.Modelos.Notas.Notas

class NotasDAOImpl : NotasDAO {
    override fun listarPorUsuario(usuarioDNI: String): List<Notas> {
        val listaDeNotas = mutableListOf<Notas>()
        val usuario = "SELECT * FROM NOTAS WHERE USUARIO_ID = (SELECT ID FROM USUARIOS WHERE DNI = ?)"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(usuario).use { statement ->
                statement?.setString(1, usuarioDNI)
                val rs = statement?.executeQuery()

                while (rs?.next() == true) {
                    listaDeNotas.add(
                        Notas(
                            id = rs.getInt("id"),
                            titulo = rs.getString("titulo"),
                            descripcion_completa = rs.getString("descripcion_completa"),
                            tipo = rs.getString("tipo"),
                            fecha = rs.getString("fecha"),
                            usuarioId = rs.getInt("usuario_id")
                        )
                    )
                }
            }
        }
        return listaDeNotas
    }

    override fun obtenerNota(id: Int): Notas? {
        val notaEspecifica = "SELECT * FROM NOTAS WHERE ID = ?"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(notaEspecifica).use { statement ->
                statement?.setInt(1, id)
                val rs = statement?.executeQuery()

                if (rs?.next() == true) {
                    return Notas(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        descripcion_completa = rs.getString("descripcion_completa"),
                        tipo = rs.getString("tipo"),
                        fecha = rs.getString("fecha"),
                        usuarioId = rs.getInt("usuario_id")
                    )
                }
            }
        }
        return null
    }

    override fun crearNota(nota: Notas, dni: String): Int {
        val obtenerUsuarioId = "SELECT id FROM usuarios WHERE dni = ?"
        val insertarNota = "INSERT INTO notas (titulo, descripcion_completa, tipo, fecha, usuario_id) VALUES (?, ?, ?, ?, ?)"
        val obtenerUltimaNota = "SELECT MAX(id) as ultimo_id FROM notas WHERE usuario_id = ?"

        try {
            Database.getConnection().use { connection ->

                connection ?: return -1

                val usuarioId = connection.prepareStatement(obtenerUsuarioId).use { ps ->
                    ps.setString(1, dni)
                    val rs = ps.executeQuery()
                    if (rs.next()) rs.getInt("id") else null
                }

                if (usuarioId == null) {
                    println("Usuario no encontrado")
                    return -1
                }

                // Insertar nota
                connection.prepareStatement(insertarNota).use { statement ->
                    statement.setString(1, nota.titulo)
                    statement.setString(2, nota.descripcion_completa)
                    statement.setString(3, nota.tipo)
                    statement.setString(4, nota.fecha)
                    statement.setInt(5, usuarioId)
                    statement.executeUpdate()
                }

                //Obtener el ID máximo de ese usuario
                connection.prepareStatement(obtenerUltimaNota).use { ps ->
                    ps.setInt(1, usuarioId)
                    val rs = ps.executeQuery()
                    if (rs.next()) {
                        return rs.getInt("ultimo_id")
                    }
                }
            }

        } catch (e: Exception) {
            println("ERROR SQL AL CREAR NOTA: ${e.message}")
            e.printStackTrace()
        }

        return -1
    }


    override fun actualizarNotaTexto(id: Int, contenido: String): Boolean {
        val actualizarNotas = "UPDATE notas SET descripcion_completa = ? WHERE id = ?"

        Database.getConnection().use { connection ->
            connection?.prepareStatement(actualizarNotas).use { statement ->
                statement?.setString(1, contenido)
                statement?.setInt(2, id)
                val filas = statement?.executeUpdate() ?: 0
                return filas > 0
            }
        }
    }

    override fun borrarNota(id: Int): Boolean {
        val borrarNota = "DELETE FROM notas WHERE id = ?"

        Database.getConnection().use { connection ->
            connection?.prepareStatement(borrarNota).use { statement ->
                statement?.setInt(1, id)
                val filas = statement?.executeUpdate() ?: 0
                return filas > 0
            }
        }
    }


    //Tareas (notas que contienen una lista)
    override fun insertarItemTarea(notaId: Int, nombre_item: String, estaFinalizado: Boolean): Boolean {
        val sql = "INSERT INTO items_tarea (nota_id, nombre_item, esta_finalizado) VALUES (?, ?, ?)"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(sql).use { statement ->
                statement?.setInt(1, notaId)
                statement?.setString(2, nombre_item)
                statement?.setBoolean(3, estaFinalizado)
                val filas = statement?.executeUpdate() ?: 0
                return filas > 0
            }
        }
    }
}