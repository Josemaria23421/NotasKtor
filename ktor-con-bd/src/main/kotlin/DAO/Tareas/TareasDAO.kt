package com.example.DAO.Tareas

import com.example.Modelos.Notas.ItemTarea

interface TareasDAO {
    fun listarTarea(notaId: Int): List<ItemTarea>
    fun insertarTarea(item: ItemTarea): Boolean
    fun cambiarEstadoDeLaTarea(itemId: Int, estadoDeLaTarea: Boolean): Boolean
    fun borrarTarea(itemId: Int): Boolean
    fun listarTareaEspecifica(tareaId: Int): ItemTarea?
}