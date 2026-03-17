package com.example.micolornote.Models.Auth

data class LoginResponse(
    val dni: String,
    val nombre: String,
    val password: String,
    val fotoPerfil: String?,
    val es_usuario: Boolean,
    val es_admin: Boolean
)