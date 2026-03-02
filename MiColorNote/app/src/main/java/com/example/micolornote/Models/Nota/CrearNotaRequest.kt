package com.example.Modelos.Notas

import com.example.micolornote.Models.Nota.ItemTareaRequest

data class CrearNotaRequest(
    val titulo: String,
    val descripcion_completa: String?,
    val tipo: String,
    val fecha: String,
    val dni: String,
    val items: List<ItemTareaRequest>? = null
)