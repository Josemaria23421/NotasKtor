package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.usuario.usuarioNet
import com.example.micolornote.Models.Persona.Persona
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Persona>>()
    val usuarios: LiveData<List<Persona>> get() = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val response = usuarioNet.retrofit.listarUsuarios()
                if (response.isSuccessful && response.body() != null) {
                    _usuarios.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "Error al cargar usuarios: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            }
        }
    }
}