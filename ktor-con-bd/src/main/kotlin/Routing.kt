package com.example

import com.example.rutas.Admin.adminRoutes
import com.example.rutas.Auth.rutasAuth
import com.example.rutas.Notas.rutasNotas
import com.example.rutas.Tareas.rutasTareas
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rutas.userRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
        // Rutas de usuarios
        userRouting()

        // Rutas de notas
        rutasNotas()

        // Rutas de autenticación
        rutasAuth()

        // Rutas de admin
        adminRoutes()
        //Rutas de las tareas
        rutasTareas()
    }
}
