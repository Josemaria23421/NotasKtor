package com.example.Modelos.Auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonaLogin(
    val dni: String,
    val password: String)