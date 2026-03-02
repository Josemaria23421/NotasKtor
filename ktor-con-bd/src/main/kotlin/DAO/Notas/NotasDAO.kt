package com.example.DAO.Notas
import com.example.Modelos.Notas.Notas
interface NotasDAO {
    fun listarPorUsuario(usuarioDNi: String): List<Notas>
    fun obtenerNota(id: Int): Notas?
    fun crearNota(nota: Notas, dni:String): Int
    fun actualizarNotaTexto(id: Int, contenido: String): Boolean
    fun borrarNota(id: Int): Boolean
    fun insertarItemTarea(notaId: Int,nombre: String,estaFinalizado: Boolean): Boolean}