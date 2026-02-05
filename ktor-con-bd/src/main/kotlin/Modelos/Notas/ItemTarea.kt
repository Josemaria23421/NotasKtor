package com.example.Modelos.Notas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemTarea (
    val id: Int? = null,
    val notaId: Int, //id respectivo a la nota a la que pertenece
    val nombre: String,
    val estaFinalizado: Boolean = false
    //Esta variable de estado la utilizale para poder usar el checkbox
)