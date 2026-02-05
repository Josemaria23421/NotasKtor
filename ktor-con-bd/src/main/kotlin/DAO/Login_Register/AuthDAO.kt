package com.example.DAO.Login_Register

import com.example.Modelos.Auth.PersonaLogin
import com.example.Modelos.USuario.Persona

interface AuthDAO {
    fun login(credenciales: PersonaLogin): Persona?
}