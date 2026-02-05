package com.example.DAO.Notas
import com.example.Modelos.Notas.Notas
interface NotasDAO {
    fun listarPorUsuario(usuarioId: Int): List<Notas>
    fun obtenerNota(id: Int): Notas?
    fun crearNota(nota: Notas): Int
    fun actualizarNotaTexto(id: Int, contenido: String): Boolean
    fun borrarNota(id: Int): Boolean
}