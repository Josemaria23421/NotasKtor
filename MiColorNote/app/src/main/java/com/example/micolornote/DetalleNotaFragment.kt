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
import com.example.micolornote.R

class DetalleNotaFragment : Fragment(R.layout.fragment_detalle_nota) {

    // ViewModel para la nota (Texto)
    private val viewModel: DetalleNotaViewModel by viewModels()
    // ViewModel para los ítems (Tareas)
    private val itemViewModel: ItemTareaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nota = NotaHolder.nota ?: return

        val etTitulo = view.findViewById<EditText>(R.id.etTituloDetalle)
        val etTexto = view.findViewById<EditText>(R.id.etContenidoTexto)
        val layoutTareas = view.findViewById<LinearLayout>(R.id.layoutTareasDetalle)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarCambios)
        val etNuevaTarea = view.findViewById<EditText>(R.id.etNuevaTareaDetalle)
        val btnAddTarea = view.findViewById<Button>(R.id.btnAgregarTareaDetalle)

        etTitulo.setText(nota.titulo)
        etTitulo.isEnabled = false

        // Lógica de visibilidad según el tipo
        if (nota.tipo.equals("TEXTO", ignoreCase = true)) {
            etTexto.visibility = View.VISIBLE
            layoutTareas.visibility = View.GONE
            etTexto.setText(nota.descripcion_completa)
        } else {
            etTexto.visibility = View.GONE
            layoutTareas.visibility = View.VISIBLE

            // CONFIGURACIÓN DE TAREAS
            val rvTareas = view.findViewById<RecyclerView>(R.id.rvItemsTareaDetalle)
            rvTareas.layoutManager = LinearLayoutManager(requireContext())

            val adapter = ItemsTareaAdapter(
                modificado = { item ->
                    itemViewModel.cambiarEstado(item)
                },
                alBorrar = { item ->
                    itemViewModel.borrarItem(item)
                }
            )
            rvTareas.adapter = adapter

            // Observamos los ítems reales de la DB
            itemViewModel.items.observe(viewLifecycleOwner) { lista ->
                adapter.submitList(lista)
            }

            // Cargamos los ítems de esta nota
            itemViewModel.cargarItems(nota.id)

            // Botón para añadir tarea nueva
            btnAddTarea.setOnClickListener {
                val nombre = etNuevaTarea.text.toString()
                if (nombre.isNotBlank()) {
                    itemViewModel.agregarItem(nota.id, nombre)
                    etNuevaTarea.text.clear()
                }
            }
        }

        // Botón Guardar (Solo para notas de TEXTO)
        btnGuardar.setOnClickListener {
            if (nota.tipo.equals("TEXTO", ignoreCase = true)) {
                val contenido = etTexto.text.toString()
                viewModel.actualizarNotaTexto(nota.id, contenido)
            } else {
                parentFragmentManager.popBackStack()
            }
        }

        // Observador de status para cerrar al guardar texto
        viewModel.status.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            if (mensaje.contains("correctamente")) {
                parentFragmentManager.popBackStack()
            }
        }

        // Observador de errores de tareas
        itemViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show() }
            }
        }
}