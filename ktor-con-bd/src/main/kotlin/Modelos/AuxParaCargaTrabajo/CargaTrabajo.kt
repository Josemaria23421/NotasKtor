package com.example.Modelos.AuxParaCargaTrabajo

import kotlinx.serialization.Serializable

@Serializable
data class CargaTrabajo(
    val usuarioId: Int,
    val nombreUsuario: String,
    val cantidadTareasPendientes: Int
)