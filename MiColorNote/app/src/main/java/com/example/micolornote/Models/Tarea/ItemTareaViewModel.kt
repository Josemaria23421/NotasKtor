package com.example.micolornote.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micolornote.Api.tareas.tareasNet
import com.example.micolornote.Models.Nota.ItemTarea
import kotlinx.coroutines.launch

class ItemTareaViewModel : ViewModel() {

    private val api = tareasNet.retrofit

    private val _items = MutableLiveData<List<ItemTarea>>()
    val items: LiveData<List<ItemTarea>> get() = _items

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // 1. Cargar todos los ItemTarea de una Nota específica
    fun cargarItems(notaId: Int) {
        viewModelScope.launch {
            try {
                val response = api.obtenerTareasDeNota(notaId)
                if (response.isSuccessful) {
                    _items.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al obtener tareas"
                }
            } catch (e: Exception) {
                _error.value = "Fallo de red: ${e.message}"
            }
        }
    }

    // 2. Crear un nuevo ItemTarea (asignado a la nota actual)
    fun agregarItem(notaId: Int, nombre: String) {
        viewModelScope.launch {
            try {
                val nuevoItem = ItemTarea(notaId = notaId, nombre = nombre, estaFinalizado = false)
                val response = api.crearTarea(nuevoItem)
                if (response.isSuccessful) {
                    cargarItems(notaId) // Refrescamos la lista tras insertar
                }
            } catch (e: Exception) {
                _error.value = "No se pudo añadir la tarea"
            }
        }
    }

    // 3. Cambiar el estado (alCheckear)
    fun cambiarEstado(item: ItemTarea) {
        viewModelScope.launch {
            try {
                val nuevoEstado = !item.estaFinalizado
                val response = api.cambiarEstadoTarea(item.id!!, nuevoEstado)

                if (response.isSuccessful) {
                    cargarItems(item.notaId) // Recargamos para confirmar el cambio
                }
            } catch (e: Exception) {
                _error.value = "Error al marcar/desmarcar"
            }
        }
    }

    // 4. Borrar el ItemTarea (alBorrar)
    fun borrarItem(item: ItemTarea) {
        viewModelScope.launch {
            try {
                val response = api.eliminarTarea(item.id!!)
                if (response.isSuccessful) {
                    cargarItems(item.notaId) //Recargamos para revisar el borrado
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar la tarea"
            }
        }
    }
}