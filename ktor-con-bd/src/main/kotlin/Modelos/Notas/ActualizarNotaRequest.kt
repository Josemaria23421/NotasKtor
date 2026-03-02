package com.example.Modelos.Notas

@kotlinx.serialization.Serializable
data class ActualizarNotaRequest(
    val contenido: String
)