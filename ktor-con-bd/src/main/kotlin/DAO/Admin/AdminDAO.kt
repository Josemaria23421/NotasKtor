package com.example.DAO.Admin

import com.example.Modelos.AuxParaCargaTrabajo.CargaTrabajo

interface AdminDAO {
    fun asignarTareaManual(notaId: Int, usuarioId: Int): Boolean

    // algoritmoAunPorDefinir
    fun asignarTareaAutomatica(notaId: Int): Int?

    fun obtenerCargaTrabajo(): List<CargaTrabajo>
}