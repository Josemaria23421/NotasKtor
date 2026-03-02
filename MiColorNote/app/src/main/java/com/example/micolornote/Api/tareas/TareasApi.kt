package com.example.micolornote.Api.tareas

import com.example.micolornote.Models.Nota.ItemTarea
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TareasApi {

    // Crear una tarea
    @POST("tarea")
    suspend fun crearTarea(
        @Body tarea: ItemTarea
    ): Response<Boolean>

    // Obtener una tarea concreta por ID
    @GET("tarea/{id}")
    suspend fun obtenerTarea(
        @Path("id") id: Int
    ): Response<ItemTarea>

    // Obtener todas las tareas de una nota
    @GET("tarea/nota/{notaId}")
    suspend fun obtenerTareasDeNota(
        @Path("notaId") notaId: Int
    ): Response<List<ItemTarea>>

    // Cambiar el estado de una tarea
    @PUT("tarea/{id}/estado")
    suspend fun cambiarEstadoTarea(
        @Path("id") id: Int, @Body estado: Boolean
    ): Response<String>

    // Eliminar una tarea
    @DELETE("tarea/{id}")
    suspend fun eliminarTarea(
        @Path("id") id: Int
    ): Response<String>
}