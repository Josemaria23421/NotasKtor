package com.example.micolornote.Models.Nota


data class Notas(
    val id: Int,
    val titulo: String,
    val descripcion_completa: String,
    val tipo: String,      // "tareas" o "nota"
    val fecha: String,
    val usuarioId: Int,
    val items: List<ItemTarea>? = null

)
