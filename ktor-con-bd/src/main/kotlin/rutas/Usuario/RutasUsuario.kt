package rutas

import com.example.DAO.Usuario.PersonaDAO
import com.example.DAO.Usuario.PersonaDAOImpl
import com.example.Modelos.USuario.Persona
import com.example.Modelos.Auth.PersonaLogin
import Modelos.Respuesta
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userRouting() {  //Esta ruta se incluirá en el archivo Routing.

    val usuarioDAO = PersonaDAOImpl()

    route("/usuarios") {
        //conseguir una persona por DNI
        get("") {
            val usuarios = usuarioDAO.listarTodosLosUsuarios()
            if (usuarios.isNotEmpty()) {
                call.respond(usuarios)  // Devuelve JSON con todos los usuarios
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("mensaje" to "No hay usuarios registrados"))
            }
        }

        get("/{dni}") {
            val dni = call.parameters["dni"]

            if (dni.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "DNI inválido")
                return@get
            }

            val usuario = usuarioDAO.obtenerPorDni(dni)

            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }
        put("/{dni}/foto") {
            val dni = call.parameters["dni"]


            if (dni.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "DNI inválido")
                return@put
            }
            val body = call.receive<Map<String, String>>()
            val urlFoto = body["foto"]

            if (urlFoto != null) {
                val ok = usuarioDAO.actualizarFoto(dni, urlFoto)
                if (ok) call.respond(HttpStatusCode.OK, "Foto actualizada")
                else call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Falta el campo 'foto' en el JSON")
            }
        }
        put("/{dni}/password")
        {
            val dni = call.parameters["dni"]

            if (dni.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "DNI inválido")
                return@put
            }
            val body = call.receive<Map<String, String>>()
            val nuevaClave = body["password"]

            if (nuevaClave != null) {
                val ok = usuarioDAO.actualizarContrasena(dni, nuevaClave)
                if (ok) {
                    call.respond(HttpStatusCode.OK, "Contraseña actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el usuario con DNI: $dni")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Falta el campo 'password' en el JSON")
            }
        }
        post("/registrar") {
            try {
                val persona = call.receive<Persona>()
                val personaACrear = Persona(
                    dni = persona.dni,
                    nombre = persona.nombre,
                    password = persona.password,
                    fotoPerfil = persona.fotoPerfil,
                    esUsuario = true,
                    esAdmin = false
                )
                val exito = usuarioDAO.crearUsuario(personaACrear)

                if (exito) {
                    // Usamos un Map para que se envíe como JSON
                    call.respond(HttpStatusCode.Created, mapOf("mensaje" to "Usuario creado correctamente"))
                } else {
                    call.respond(HttpStatusCode.Conflict, mapOf("mensaje" to "El usuario con ese DNI ya existe"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos enviados: ${e.message}")
            }
        }
    }
}
