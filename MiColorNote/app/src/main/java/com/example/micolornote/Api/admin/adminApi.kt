package com.example.micolornote.Api.admin

import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.Models.Auth.PersonaLogin
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface adminApi {
    @POST("admin/registrar")
    suspend fun registrar(@Body persona: Persona): Response<ResponseBody>
}