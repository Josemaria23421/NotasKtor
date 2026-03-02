package com.example.micolornote.Models.Nota


data class ItemTarea(
    val id: Int? = null,
    val notaId: Int,
    val nombre: String,
    val estaFinalizado: Boolean = false
)
