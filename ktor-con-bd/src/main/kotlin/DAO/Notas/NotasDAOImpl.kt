package com.example.DAO.Notas

import Helpers.Database
import com.example.Modelos.Notas.Notas

class NotasDAOImpl : NotasDAO {
    override fun listarPorUsuario(usuarioId: Int): List<Notas> {
        val listaDeNotas = mutableListOf<Notas>()
        val usuario = "SELECT * FROM NOTAS WHERE USUARIO_ID = ?"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(usuario).use { statement ->
                statement?.setInt(1, usuarioId)
                val rs = statement?.executeQuery()

                while (rs?.next() == true) {
                    listaDeNotas.add(
                        Notas(
                            id = rs.getInt("id"),
                            titulo = rs.getString("titulo"),
                            descripcionDeLaNota = rs.getString("descripcion_completa"),
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
                        descripcionDeLaNota = rs.getString("descripcion_completa"),
                        tipo = rs.getString("tipo"),
                        fecha = rs.getString("fecha"),
                        usuarioId = rs.getInt("usuario_id")
                    )
                }
            }
        }
        return null
    }

    override fun crearNota(nota: Notas): Int {
        val insertarNota = "INSERT INTO notas (titulo, descripcion_completa, tipo, fecha, usuario_id) VALUES (?, ?, ?, ?, ?)"
        return try {
            Database.getConnection().use { connection ->
                connection?.prepareStatement(insertarNota).use { statement ->
                    statement?.setString(1, nota.titulo)
                    statement?.setString(2, nota.descripcionDeLaNota)
                    statement?.setString(3, nota.tipo)
                    statement?.setString(4, nota.fecha)
                    statement?.setInt(5, nota.usuarioId)
                    val filasAfectadas = statement?.executeUpdate() ?: 0
                    if (filasAfectadas > 0) 1 else -1
                }
            }
        } catch (e: Exception) {
            println("ERROR SQL AL CREAR NOTA: ${e.message}")
            e.printStackTrace()
            -1
        }
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
}