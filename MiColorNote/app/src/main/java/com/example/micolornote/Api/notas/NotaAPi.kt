package com.example.micolornote.Api.notas
import com.example.Modelos.Notas.CrearNotaRequest
import com.example.micolornote.Models.Nota.ActualizarNotaRequest
import com.example.micolornote.Models.Nota.Notas
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotasApi   {

    // Crear una nota
    @POST("notas")
    suspend fun crearNota(
        @Body nota: CrearNotaRequest
    ): Response<ResponseBody>

    // Obtener todas las notas de un usuario
    @GET("notas/usuario/{dni}")
    suspend fun obtenerNotasPorUsuario(
        @Path("dni") usuarioDni: String?
    ): Response<List<Notas>>

    // Obtener una nota concreta
    @GET("notas/{id}")
    suspend fun obtenerNota(
        @Path("id") id: Int
    ): Response<Notas>

    // Actualizar el contenido (texto) de una nota
    @PUT("notas/{id}")
    suspend fun actualizarContenidoNota(
        @Path("id") id: Int,
        @Body contenido: ActualizarNotaRequest
    ): Response<ResponseBody>

    // Eliminar una nota
    @DELETE("notas/{id}")
    suspend fun eliminarNota(
        @Path("id") id: Int
    ): Response<ResponseBody>
}
