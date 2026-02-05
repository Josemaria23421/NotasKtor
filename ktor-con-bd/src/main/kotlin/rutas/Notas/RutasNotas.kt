package com.example.rutas.Notas

import com.example.DAO.Notas.NotasDAOImpl
import com.example.Modelos.Notas.Notas
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.rutasNotas() {

    val notasDAO = NotasDAOImpl()

    route("/notas") {
        //crear una nota
        post {
            val nota = call.receive<Notas>()
            val idGenerado = notasDAO.crearNota(nota)

            if (idGenerado > 0) {
                call.respond(HttpStatusCode.Created, idGenerado)
            } else {
                call.respond(HttpStatusCode.BadRequest, "No se pudo crear la nota")
            }
        }

        //conseguir las notas ed un usuario concreto
        get("/usuario/{id}") {
            val usuarioId = call.parameters["id"]?.toIntOrNull()

            if (usuarioId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val notas = notasDAO.listarPorUsuario(usuarioId)
            call.respond(notas)
        }
        //conseguir una nota en especifico
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val nota = notasDAO.obtenerNota(id)

            if (nota != null) {
                call.respond(nota)
            } else {
                call.respond(HttpStatusCode.NotFound, "Nota no encontrada")
            }
        }
        //modificar contenido de una nota
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val contenido = call.receive<String>()

            val ok = notasDAO.actualizarNotaTexto(id, contenido)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Nota actualizada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Nota no encontrada")
            }
        }
        //borrar una nota
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }

            val ok = notasDAO.borrarNota(id)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Nota eliminada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Nota no encontrada")
            }
        }
    }

}