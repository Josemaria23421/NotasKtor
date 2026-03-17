package com.example.micolornote.Fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Modelos.Notas.CrearNotaRequest
import com.example.micolornote.Api.admin.adminNet
import com.example.micolornote.Api.notas.notasNet
import com.example.micolornote.Api.usuario.usuarioNet
import com.example.micolornote.Models.Nota.Notas
import com.example.micolornote.Models.Persona.Persona
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Persona>>()
    val usuarios: LiveData<List<Persona>> get() = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error
    private val _operacionExitosa = MutableLiveData<Boolean>()
    val operacionExitosa: LiveData<Boolean> get() = _operacionExitosa
    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> get() = _registroExitoso

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
    fun actualizarRoles(dni: String, personaActualizada: Persona) {
        viewModelScope.launch {
            try {
                val response = usuarioNet.retrofit.actualizarRoles(dni, personaActualizada)

                if (response.isSuccessful) {
                    _operacionExitosa.value = true
                    _error.value = null
                    cargarUsuarios()
                } else {
                    _error.value = "No se pudieron actualizar los roles"
                }
            } catch (e: Exception) {
                _error.value = "Fallo al conectar con el servidor"
            }
        }
    }
    fun crearYAsignarTarea(request: CrearNotaRequest) {
        viewModelScope.launch {
            try {
                val response = notasNet.retrofit.crearNota(request)

                if (response.isSuccessful) {
                    _operacionExitosa.value = true
                } else {
                    _error.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {

                _error.value = "Error de red: ${e.message}"
                Log.e("Error de la Api", "Fallo al crear tarea", e)
            }
        }
    }
    fun registrarUsuario(nuevaPersona: Persona) {
        viewModelScope.launch {
            try {
                val response = adminNet.retrofit.registrar(nuevaPersona)
                if (response.isSuccessful) {
                    _registroExitoso.value = true
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Fallo de red: ${e.message}"
            }
        }
    }
    fun borrarUsuario(dni: String) {
        viewModelScope.launch {
            try {
                val response = usuarioNet.retrofit.borrarUsuario(dni)
                if (response.isSuccessful) {
                    _operacionExitosa.value = true
                    _error.value = null
                    cargarUsuarios()
                } else {
                    _error.value = "Error al eliminar: el usuario puede tener datos asociados"
                }
            } catch (e: Exception) {
                _error.value = "Fallo de conexión al borrar"
            }
        }
    }

    fun resetStatus() {
        _operacionExitosa.value = false
    }

}