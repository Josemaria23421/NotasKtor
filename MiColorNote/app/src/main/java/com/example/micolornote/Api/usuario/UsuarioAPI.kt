import com.example.micolornote.Models.Persona.Persona
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioAPI {
    //Ruta para todos los usuarios
    //el response body nos esta evitando que tengamos que usar Maps de Strings
    //osea, nos hace que el json que es enviado por el back (Ktor)
    //nos venga en crudo, siendo el mensaje en concreto

    // Obtener todos los usuarios
    @GET("usuarios")
    suspend fun listarUsuarios(): Response<List<Persona>>

    // Obtener usuario por DNI
    @GET("usuarios/{dni}")
    suspend fun obtenerUsuario(
        @Path("dni") dni: String
    ): Response<Persona>

    // Cambiar foto de perfil
    @PUT("usuarios/{dni}/foto")
    suspend fun cambiarFotoDelUsuario(
        @Path("dni") dni: String,
        @Body body: Map<String, String>   // { "foto": "url" }
    ): Response<ResponseBody>


    // Cambiar contraseña
    @PUT("usuarios/{dni}/password")
    suspend fun cambiarPassword(
        @Path("dni") dni: String,
        @Body body: Map<String, String>   // { "password": "nuevaClave" }
    ): Response<ResponseBody>

    // Registrar usuario
    @POST("usuarios/registrar")
    suspend fun registrarUsuario(
        @Body persona: Persona
    ): Response<Map<String, String>>

}
