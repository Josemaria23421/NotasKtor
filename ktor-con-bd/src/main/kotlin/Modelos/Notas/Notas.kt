package com.example.Modelos.Notas


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Notas(
    val id : Int,
    val titulo: String,
    @SerialName("descripcion") val descripcionDeLaNota: String,
    val tipo: String, //con esto definimos tareas o notasSimples
    val fecha: String,
    val usuarioId: Int    //id del usuario al que pertenece)
)
