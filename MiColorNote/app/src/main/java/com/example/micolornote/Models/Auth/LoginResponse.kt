package com.example.micolornote.Models.Auth

data class LoginResponse(
    val dni: String,
    val nombre: String,
    val password: String,
    val fotoPerfil: String?,
    val esUsuario: Boolean,
    val esAdmin: Boolean
)