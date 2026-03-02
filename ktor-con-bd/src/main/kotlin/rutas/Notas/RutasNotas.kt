package com.example.rutas.Notas

import com.example.DAO.Notas.NotasDAOImpl
import com.example.Modelos.Notas.ActualizarNotaRequest
import com.example.Modelos.Notas.CrearNotaRequest
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

        post {

            val request = call.receive<CrearNotaRequest>()

            val nota = Notas(
                id = 0,
                titulo = request.titulo,
                descripcion_completa = request.descripcion_completa,
                tipo = request.tipo,
                fecha = request.fecha,
                usuarioId = 0
            )

            val notaId = notasDAO.crearNota(nota, request.dni)

            if (notaId <= 0) {
                call.respond(HttpStatusCode.BadRequest, "No se pudo crear la nota")
                return@post
            }

            if (request.tipo == "TAREAS" && request.items != null) {
                request.items.forEach { item ->
                    notasDAO.insertarItemTarea(
                        notaId = notaId,
                        nombre_item = item.nombre,
                        estaFinalizado = item.estaFinalizado
                    )
                }
            }

            call.respond(HttpStatusCode.Created, notaId)
        }

        get("/usuario/{dni}") {

            val dni = call.parameters["dni"]

            if (dni == null) {
                call.respond(HttpStatusCode.BadRequest, "DNI inválido")
                return@get
            }

            val notas = notasDAO.listarPorUsuario(dni)
            call.respond(notas)
        }

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

        put("/{id}") {

            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val contenido = call.receive<ActualizarNotaRequest>()

            val ok = notasDAO.actualizarNotaTexto(id, contenido.contenido)

            if (ok) {
                call.respond(HttpStatusCode.OK, "Nota actualizada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Nota no encontrada")
            }
        }

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