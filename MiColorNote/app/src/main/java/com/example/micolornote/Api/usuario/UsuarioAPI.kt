import com.example.micolornote.Models.Persona.Persona
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioAPI {
    @GET("usuarios")
    suspend fun listarUsuarios(): Response<List<Persona>>

    @GET("usuarios/{dni}")
    suspend fun obtenerUsuario(@Path("dni") dni: String): Response<Persona>
}
