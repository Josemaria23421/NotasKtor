package com.example.DAO.Admin

import Helpers.Database
import com.example.Modelos.AuxParaCargaTrabajo.CargaTrabajo

class AdminDAOImpl : AdminDAO {
    override fun asignarTareaManual(notaId: Int, usuarioId: Int): Boolean {
        val asignarTareasaUnUsuario = "UPDATE notas SET usuario_id = ? WHERE id = ?"

        return try {
            Database.getConnection().use { connection ->
                connection?.prepareStatement(asignarTareasaUnUsuario).use { statement ->
                    statement?.setInt(1, usuarioId)
                    statement?.setInt(2, notaId)

                    val filas = statement?.executeUpdate() ?: 0
                    filas > 0
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun asignarTareaAutomatica(notaId: Int): Int? {
        val listaCarga = obtenerCargaTrabajo()

        if (listaCarga.isEmpty()) return null

        val usuarioConMenosCargaDeTrabajo = listaCarga.first().usuarioId

        val sqlUpdate = "UPDATE notas SET usuario_id = ? WHERE id = ?"

        Database.getConnection().use { connection ->
            connection?.prepareStatement(sqlUpdate).use { statement ->
                statement?.setInt(1, usuarioConMenosCargaDeTrabajo)
                statement?.setInt(2, notaId)

                val filas = statement?.executeUpdate() ?: 0
                return if (filas > 0) usuarioConMenosCargaDeTrabajo else null
            }
        }
    }

    override fun obtenerCargaTrabajo(): List<CargaTrabajo> {
        val lista = mutableListOf<CargaTrabajo>()

        // Seleccionamos el usuario y contamos sus notas en una subconsulta
        val sql = "SELECT u.id, u.username, " +
                "(SELECT COUNT(*) FROM notas n WHERE n.usuario_id = u.id AND n.tipo = 'TAREAS') AS tareas " +
                "FROM usuarios u " +
                "ORDER BY tareas ASC"

        Database.getConnection().use { connection ->
            connection?.prepareStatement(sql).use { statement ->
                val rs = statement?.executeQuery()

                while (rs?.next() == true) {
                    lista.add(
                        CargaTrabajo(
                            usuarioId = rs.getInt("id"),
                            nombreUsuario = rs.getString("username"),
                            cantidadTareasPendientes = rs.getInt("tareas")
                        )
                    )
                }
            }
        }
        return lista
    }

}