package com.example.micolornote.Models.Nota

data class ItemTareaRequest(
    val nombre: String,
    val estaFinalizado: Boolean = false
)