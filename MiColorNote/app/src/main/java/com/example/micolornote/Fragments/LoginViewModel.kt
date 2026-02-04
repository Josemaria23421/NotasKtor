package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.auth.authNet
import com.example.micolornote.Models.Auth.PersonaLogin
import com.example.micolornote.Models.Persona.Persona
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //observar el resultado del login
    private val _personaLogueada = MutableLiveData<Persona?>()
    val personaLogueada: LiveData<Persona?> get() = _personaLogueada

    //observar errores
    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> get() = _mensajeError

    fun login(dni: String, clave: String) {
        viewModelScope.launch {
            try {
                val credenciales = PersonaLogin(dni, clave)
                val response = authNet.retrofit.login(credenciales)

                if (response.isSuccessful && response.body() != null) {
                    _personaLogueada.value = response.body()
                    _mensajeError.value = null
                } else {
                    _personaLogueada.value = null
                    _mensajeError.value = "DNI o clave incorrectos"
                }
            } catch (e: Exception) {
                _personaLogueada.value = null
                _mensajeError.value = "Error: No se pudo conectar con el servidor: ${e.message}"
            }
        }
    }
}