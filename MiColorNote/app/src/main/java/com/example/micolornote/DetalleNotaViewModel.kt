package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.notas.notasNet // Asegúrate de que esta ruta sea correcta
import com.example.micolornote.Models.Nota.ActualizarNotaRequest
import kotlinx.coroutines.launch

class DetalleNotaViewModel : ViewModel() {
    private val api = notasNet.retrofit
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun actualizarNotaTexto(id: Int, nuevoContenido: String) {
        viewModelScope.launch {
            val request = ActualizarNotaRequest(nuevoContenido)

            val response = api.actualizarContenidoNota(id, request)
            if (response.isSuccessful) {
                _status.value = "Nota actualizada correctamente"
            } else {
                _status.value = "Error al actualizar nota"
            }
        }
    }

    fun eliminarNota(id: Int) {
        viewModelScope.launch {
            val response = notasNet.retrofit.eliminarNota(id)
            if (response.isSuccessful) {
                _status.value = "Nota eliminada"
            } else {
                _status.value = "Error al eliminar nota"
            }
        }
    }
}