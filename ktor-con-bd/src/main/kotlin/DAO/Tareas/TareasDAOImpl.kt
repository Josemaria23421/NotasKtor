package com.example.DAO.Tareas

import Helpers.Database
import com.example.Modelos.Notas.ItemTarea
import javax.xml.crypto.Data

class TareasDAOImpl : TareasDAO {
    override fun listarTarea(notaId: Int): List<ItemTarea> {
        val tareas = mutableListOf<ItemTarea>()
        var listadoDeTareas = "SELECT * FROM ITEMS_TAREA WHERE NOTA_ID = ?"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(listadoDeTareas).use { statement ->
                statement?.setInt(1, notaId)
                val rs = statement?.executeQuery()
                while (rs?.next() == true) {
                    tareas.add(
                        ItemTarea(
                            id = rs.getInt("id"),
                            notaId = rs.getInt("nota_id"),
                            nombre = rs.getString("nombre_item"),
                            estaFinalizado = rs.getBoolean("esta_finalizado"),
                        )
                    )
                }
            }
        }
        return tareas
    }

    override fun listarTareaEspecifica(tareaId: Int): ItemTarea? {
        var tarea = "SELECT * FROM ITEMS_TAREA WHERE ID = ?"
        return Database.getConnection().use { connection ->
            connection?.prepareStatement(tarea).use { statement ->
                statement?.setInt(1, tareaId)
                statement?.executeQuery().use { rs ->
                    if (rs?.next() == true) {
                        ItemTarea(
                            id = rs.getInt("id"),
                            notaId = rs.getInt("nota_id"),
                            nombre = rs.getString("nombre_item"),
                            estaFinalizado = rs.getBoolean("esta_finalizado"),
                        )
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun insertarTarea(item: ItemTarea): Boolean {
        val insertarNuevaTarea = "INSERT INTO ITEMS_TAREA (nota_id, nombre_item, esta_finalizado) VALUES (?, ?, ?)"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(insertarNuevaTarea).use { statement ->
                statement?.setInt(1, item.notaId)
                statement?.setString(2, item.nombre)
                statement?.setBoolean(3, item.estaFinalizado)
                return statement?.executeUpdate()!! > 0
            }
        }

    }

    override fun cambiarEstadoDeLaTarea(itemId: Int, estadoDeLaTarea: Boolean): Boolean {
        val modificarEstado = "UPDATE ITEMS_TAREA SET ESTA_FINALIZADO = ? WHERE ID = ?"
        Database.getConnection().use { connection ->
            connection?.prepareStatement(modificarEstado).use { statement ->
                statement?.setBoolean(1, estadoDeLaTarea)
                statement?.setInt(2, itemId)
                return statement?.executeUpdate()!! > 0
            }
        }
    }

    override fun borrarTarea(itemId: Int): Boolean {
        val borrarTarea = "DELETE FROM ITEMS_TAREA WHERE ID = ?"
        Database.getConnection().use { conexcion ->
            conexcion?.prepareStatement(borrarTarea).use { statement ->
                statement?.setInt(1, itemId)
                return statement?.executeUpdate()!! > 0
            }
        }
    }
}