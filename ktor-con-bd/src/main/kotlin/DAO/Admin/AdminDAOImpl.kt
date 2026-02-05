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
        //falta crear la asignacion automatica se hara en base a la carga de
        //tareas que no estan terminadas
        return 1
    }

    override fun obtenerCargaTrabajo(): List<CargaTrabajo> {
        val lista = mutableListOf<CargaTrabajo>()

        val sql = " SELECT u.id, u.nombre, COUNT(n.id) AS tareas\n FROM usuarios u\n" +
                "        LEFT JOIN notas n ON u.id = n.usuario_id\n" +
                "        WHERE u.es_Admin = false\n" +
                "        GROUP BY u.id, u.nombre\n" +
                "        ORDER BY tareas DESC"

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