package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.notas.notasNet
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.Models.Nota.Notas
import com.example.micolornote.Models.Persona.Persona
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _usuario = MutableLiveData<Persona?>()
    val usuario: LiveData<Persona?> get() = _usuario

    private val _notas = MutableLiveData<List<Notas>>()
    val notas: LiveData<List<Notas>> get() = _notas

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // 1. CARGAR NOTAS
    fun cargarNotas(dni: String) {
        viewModelScope.launch {
            try {
                val response = notasNet.retrofit.obtenerNotasPorUsuario(dni)

                if (response.isSuccessful && response.body() != null) {
                    _notas.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "Error al cargar notas: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun borrarNota(id: Int) {
        viewModelScope.launch {
            try {
                val response = notasNet.retrofit.eliminarNota(id)

                if (response.isSuccessful) {
                    UsuarioHolder.dni?.let { dni ->
                        cargarNotas(dni)
                    }
                } else {
                    _error.value = "No se pudo eliminar la nota del servidor"
                }
            } catch (e: Exception) {
                _error.value = "Error al conectar para borrar: ${e.message}"
            }
        }
    }
}