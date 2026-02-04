package com.example.micolornote.Models.Persona

data class Persona(
    val dni: String,
    val nombre: String,
    val password: String,
    val fotoPerfil: String?,
    val esUsuario: Boolean,
    val esAdmin: Boolean
)
