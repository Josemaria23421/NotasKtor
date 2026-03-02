package com.example.micolornote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.usuario.usuarioNet
import kotlinx.coroutines.launch

class UserPerfilViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun updatePassword(dni: String, nuevoPass: String) {
        viewModelScope.launch {
            val body = mapOf("password" to nuevoPass)
            val response = usuarioNet.retrofit.cambiarPassword(dni, body)
            if (response.isSuccessful) {
                _status.value = "Contraseña actualizada"
            } else {
                _status.value = "Error al cambiar contraseña"
            }
        }
    }

    fun updateFoto(dni: String, urlFoto: String) {
        viewModelScope.launch {
            val body = mapOf("foto" to urlFoto)
            val response = usuarioNet.retrofit.cambiarFotoDelUsuario(dni, body)
            if (response.isSuccessful) {
                _status.value = "Foto actualizada"
            }
        }
    }
}