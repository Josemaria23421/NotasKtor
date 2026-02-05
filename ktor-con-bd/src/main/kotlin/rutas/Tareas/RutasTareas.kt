package com.example.rutas.Tareas

import com.example.DAO.Tareas.TareasDAOImpl
import com.example.Modelos.Notas.ItemTarea
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.rutasTareas() {
    val tareaDAO = TareasDAOImpl()
    route("/tarea")
    {
        post {
            val tarea = call.receive<ItemTarea>()
            val idGenerado = tareaDAO.insertarTarea(tarea)
            if (idGenerado) {
                call.respond(HttpStatusCode.Created, idGenerado)
            } else {
                call.respond(HttpStatusCode.BadRequest, "No se pudo crear la nota")
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val tarea = tareaDAO.listarTareaEspecifica(id)

            if (tarea != null) {
                call.respond(tarea)
            } else {
                call.respond(HttpStatusCode.NotFound, "Tarea no encontrada")
            }
        }
        get("/nota/{notaId}") {
            val notaId = call.parameters["notaId"]?.toIntOrNull()

            if (notaId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID de nota inválido")
                return@get
            }

            val tareas = tareaDAO.listarTarea(notaId)
            call.respond(tareas)
        }

        put("/{id}/estado") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val estado = call.receive<Boolean>()
            val ok = tareaDAO.cambiarEstadoDeLaTarea(id, estado)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Estado actualizado")
            } else {
                call.respond(HttpStatusCode.NotFound, "Tarea no encontrada")
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }

            val ok = tareaDAO.borrarTarea(id)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Tarea eliminada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Tarea no encontrada")
            }
        }
    }

}