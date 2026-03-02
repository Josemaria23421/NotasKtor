package com.example.micolornote.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Adapters.ItemsTareaAdapter
import com.example.micolornote.Holder.NotaHolder
import com.example.micolornote.Models.Nota.ItemTarea
import com.example.micolornote.R

class DetalleNotaFragment : Fragment(R.layout.fragment_detalle_nota) {

    private val viewModel: DetalleNotaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nota = NotaHolder.nota ?: return

        val etTitulo = view.findViewById<EditText>(R.id.etTituloDetalle)
        val etTexto = view.findViewById<EditText>(R.id.etContenidoTexto)
        val layoutTareas = view.findViewById<LinearLayout>(R.id.layoutTareasDetalle)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarCambios)

        // Seteamos los datos iniciales
        etTitulo.setText(nota.titulo)
        etTitulo.isEnabled = false // Título no editable por ahora

        if (nota.tipo.equals("TEXTO", ignoreCase = true)) {
            etTexto.visibility = View.VISIBLE
            layoutTareas.visibility = View.GONE
            etTexto.setText(nota.descripcion_completa)
        } else {
            etTexto.visibility = View.GONE
            layoutTareas.visibility = View.VISIBLE
        }
        val rvTareas = view.findViewById<RecyclerView>(R.id.rvItemsTareaDetalle)
        if (nota.tipo.equals("TAREAS", ignoreCase = true)) {
            layoutTareas.visibility = View.VISIBLE

            // 1. Configurar el adaptador
            val adapter = ItemsTareaAdapter(
                onToggle = { item -> },
                onDelete = { item -> }
            )

            rvTareas.layoutManager = LinearLayoutManager(requireContext())
            rvTareas.adapter = adapter

            // 2. Por ahora, para probar que se VEAN, añade datos de prueba:
            val listaPrueba = listOf(
                ItemTarea(1, nota.id, "Tarea de ejemplo 1", false),
                ItemTarea(2, nota.id, "Tarea de ejemplo 2", true)
            )
            adapter.submitList(listaPrueba)
        }

        // Botón Guardar
        btnGuardar.setOnClickListener {
            val contenido = etTexto.text.toString()
            if (contenido.isNotEmpty()) {
                viewModel.actualizarNotaTexto(nota.id, contenido)
            }
        }

        // Observador de status (Igual que en tu perfil)
        viewModel.status.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            if (mensaje.contains("correctamente") || mensaje.contains("eliminada")) {
                parentFragmentManager.popBackStack()
            }
        }
    }
}