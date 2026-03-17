package com.example.micolornote.Models.Persona

data class Persona(
    val dni: String,
    val nombre: String,
    val password: String,
    val fotoPerfil: String?,
    var es_usuario: Boolean,
    var es_admin: Boolean
)
