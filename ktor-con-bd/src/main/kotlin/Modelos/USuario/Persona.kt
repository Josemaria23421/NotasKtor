package com.example.Modelos.USuario

import kotlinx.serialization.Serializable

@Serializable
data class Persona(
    val dni: String,
    val nombre: String,
    val password: String,
    val fotoPerfil: String? = null, // Para la foto
    //Estas variables reflejan el rol con el que nos encontramos, pudiendo ser usuarioNormal y adminitrador
    val esUsuario: Boolean,
    val esAdmin: Boolean

)