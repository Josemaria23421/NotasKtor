package com.example.DAO.Usuario

import com.example.Modelos.USuario.Persona
import com.example.Modelos.Auth.PersonaLogin

interface PersonaDAO {
    //FuncionesGenericas
    fun obtenerPorDni(dni: String): Persona?
    fun actualizarContrasena(dni: String, nuevaClave: String): Boolean
    fun actualizarFoto(dni: String, urlFoto: String): Boolean
    //FuncionesUsadasPorAadmins
    fun crearUsuario(persona: Persona): Boolean
    fun borrarUsuario(dni: String): Boolean
    fun actualizarRoles(dni: String, esUsuario: Boolean, esAdmin: Boolean): Boolean
    fun listarTodosLosUsuarios(): List<Persona>
}