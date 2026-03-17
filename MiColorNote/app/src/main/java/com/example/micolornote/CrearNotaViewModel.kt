package com.example.micolornote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Modelos.Notas.CrearNotaRequest
import com.example.micolornote.Api.notas.notasNet
import com.example.micolornote.Models.Nota.ItemTarea
import com.example.micolornote.Models.Nota.ItemTareaRequest
import com.example.micolornote.Models.Nota.Notas
import com.example.micolornote.Models.Nota.notasENUM
import kotlinx.coroutines.launch

class CrearNotaViewModel : ViewModel() {
    private val _notaCreada = MutableLiveData<Boolean>()
    val notaCreada: LiveData<Boolean> get() = _notaCreada

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error


    fun crearNota(
        titulo: String,
        descripcion: String,
        tipo: String, // Recibimos "nota" o "tareas"
        dni: String,
        items: List<ItemTareaRequest>? = null
    ) {
        val crearNotaRequest = CrearNotaRequest(
            titulo = titulo,
            descripcion_completa = descripcion,
            tipo = tipo,
            fecha = obtenerFechaActual(),
            dni = dni,
            items = items // Si es una nota simple, esto será null
        )

        viewModelScope.launch {
            try {
                val response = notasNet.retrofit.crearNota(crearNotaRequest)
                if (response.isSuccessful) {
                    _notaCreada.postValue(true)
                } else {
                    _error.postValue("Error al crear ${tipo}: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de conexión: ${e.localizedMessage}")
            }
        }
    }

    fun eliminarNota(idNota: Int) {
        viewModelScope.launch {
            try {
                val response = notasNet.retrofit.eliminarNota(idNota)
                if (response.isSuccessful && response.body() != null) {
                    _error.value = null
                } else {
                    _error.value = "Error al cargar notas: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"

            }
        }
    }

    //el siguiente metodo esta sacado de chatgpt -> es un metodo que guarda la fecha del dia de hoy
    //funciona como un sysdate (EN SQL)
    fun obtenerFechaActual(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}