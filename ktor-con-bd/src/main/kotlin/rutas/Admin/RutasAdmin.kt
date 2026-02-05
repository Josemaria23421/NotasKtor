package com.example.rutas.Admin

import com.example.DAO.Admin.AdminDAOImpl
import com.example.DAO.Usuario.PersonaDAOImpl
import com.example.Modelos.USuario.Persona
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.adminRoutes() {
    val adminDAO = AdminDAOImpl()
    val usuarioDao = PersonaDAOImpl();

    route("/admin") {

        put("/asignar/manual/{notaId}/{usuarioId}") {

            val notaId = call.parameters["notaId"]?.toIntOrNull()
            val usuarioId = call.parameters["usuarioId"]?.toIntOrNull()

            if (notaId == null || usuarioId == null) {
                call.respond(HttpStatusCode.BadRequest, "Parámetros inválidos")
                return@put
            }

            val ok = adminDAO.asignarTareaManual(notaId, usuarioId)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Tarea asignada correctamente")
            } else {
                call.respond(HttpStatusCode.BadRequest, "No se pudo asignar la tarea")
            }
        }
        val adminDAO = AdminDAOImpl()

        route("/admin") {

            put("/asignar/manual/{notaId}/{usuarioId}") {

                val notaId = call.parameters["notaId"]?.toIntOrNull()
                val usuarioId = call.parameters["usuarioId"]?.toIntOrNull()

                if (notaId == null || usuarioId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Parámetros inválidos")
                    return@put
                }

                val ok = adminDAO.asignarTareaManual(notaId, usuarioId)

                if (ok) {
                    call.respond(HttpStatusCode.OK, "Tarea asignada correctamente")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "No se pudo asignar la tarea")
                }
            }
            post("/registrar") {
                try {
                    val persona = call.receive<Persona>()
                    val exito = usuarioDao.crearUsuario(persona)

                    if (exito) {
                        call.respond(HttpStatusCode.Created, "Usuario creado correctamente")
                    } else {
                        //Intento de duplicado de DNi
                        call.respond(HttpStatusCode.Conflict, "El usuario con ese DNI ya existe")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos enviados: ${e.message}")
                }
            }
        }
        get("/carga") {
            val carga = adminDAO.obtenerCargaTrabajo()
            call.respond(carga)
        }
    }
}