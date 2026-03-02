package com.example.Modelos.Notas
@kotlinx.serialization.Serializable

data class ItemTareaRequest(
    val nombre: String,
    val estaFinalizado: Boolean = false
)