package com.example.micolornote.Models.Auth

import kotlinx.serialization.SerialName

data class PersonaLogin (
    val dni:String,
    val password: String
)