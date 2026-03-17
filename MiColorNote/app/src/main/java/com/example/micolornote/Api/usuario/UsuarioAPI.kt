import com.example.micolornote.Models.Persona.Persona
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioAPI {
    //Ruta para todos los usuarios

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
        @Body body: Map<String, String>
    ): Response<ResponseBody>

    // Cambiar contraseña
    @PUT("usuarios/{dni}/password")
    suspend fun cambiarPassword(
        @Path("dni") dni: String,
        @Body body: Map<String, String>
    ): Response<ResponseBody>

    // Registrar usuario
    @POST("usuarios/registrar")
    suspend fun registrarUsuario(
        @Body persona: Persona
    ): Response<Map<String, String>>

    //Modificar los roles de los usuarios
    @PUT("usuarios/{dni}/roles")
    suspend fun actualizarRoles(
        @Path("dni") dni: String,
        @Body persona: Persona
    ): Response<ResponseBody>

    //borrar un usuario
    @DELETE("usuarios/{dni}")
    suspend fun borrarUsuario(
        @Path("dni") dni: String
    ): Response<ResponseBody>
}
