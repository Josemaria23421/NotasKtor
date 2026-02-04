package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.auth.authNet
import com.example.micolornote.Models.Persona.Persona
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    //Observamos el resultado del register
    private val _personaRegistrada = MutableLiveData<Persona?>()
    val personaRegistrada : LiveData<Persona?> get() = _personaRegistrada
    //observamos errores tambien
    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> get() = _mensajeError
    //Funcion de registro
    fun registro(persona: Persona)
    {
        viewModelScope.launch {
            try{
                val response = authNet.retrofit.registrar(persona)
                if (response.isSuccessful) {
                    _personaRegistrada.value = persona
                } else {
                    _personaRegistrada.value = null
                    if (response.code() == 409) {
                        _mensajeError.value = "El DNI ya está registrado"
                    } else {
                        _mensajeError.value = "Error en el servidor: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                _personaRegistrada.value = null
                _mensajeError.value = "Error: No se pudo conectar con el servidor: ${e.message}"
            }
        }
    }
}