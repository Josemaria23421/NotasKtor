package com.example.rutas.Auth

import com.example.DAO.Login_Register.AuthDAOImpl
import com.example.Modelos.Auth.PersonaLogin
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.rutasAuth(){
    val authDAO = AuthDAOImpl()
    route("/auth")
    {
        post("/login") {
            val credenciales = call.receive<PersonaLogin>()
            val persona = authDAO.login(credenciales)

            if (persona != null) {
                call.respond(persona)
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "DNI o clave incorrectos"
                )
            }
        }
    }
}