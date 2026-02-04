package com.example.micolornote.Api.auth

import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.Models.Auth.PersonaLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("auth/login")
    suspend fun login(@Body credenciales: PersonaLogin): Response<Persona>

    @POST("usuarios/registrar")
    suspend fun registrar(@Body persona: Persona ): Response<Unit>
}
