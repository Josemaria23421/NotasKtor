package com.example.micolornote

import android.os.Bundle
import android.view.View
import android.widget.* // esto importa  RadioGroup, RadioButton, LinearLayout, etc.
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.Models.Nota.ItemTareaRequest

class CrearNotaFragment : Fragment(R.layout.fragment_crear_nota) {

    private val viewModel: CrearNotaViewModel by viewModels()
    private val editTextsTareas = mutableListOf<EditText>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitulo = view.findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)
        val rgTipo = view.findViewById<RadioGroup>(R.id.rgTipo)
        val layoutContenedorTareas = view.findViewById<LinearLayout>(R.id.layoutContenedorTareas)
        val containerItems = view.findViewById<LinearLayout>(R.id.containerItems)
        val btnAgregarItem = view.findViewById<Button>(R.id.btnAgregarItem)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarNota)

        rgTipo.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbTarea) {
                layoutContenedorTareas.visibility = View.VISIBLE
            } else {
                layoutContenedorTareas.visibility = View.GONE
                containerItems.removeAllViews()
                editTextsTareas.clear()
            }
        }

        // 2. Botón para añadir un nuevo campo de tarea
        btnAgregarItem.setOnClickListener {
            val nuevoItemET = EditText(requireContext()).apply {
                hint = "Tarea #${editTextsTareas.size + 1}"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
            }
            containerItems.addView(nuevoItemET)
            editTextsTareas.add(nuevoItemET)
            nuevoItemET.requestFocus()
        }

        // 3. Botón Guardar
        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()
            val usuarioDni = UsuarioHolder.dni

            if (titulo.isBlank()) {
                Toast.makeText(requireContext(), "El título es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuarioDni == null) {
                Toast.makeText(requireContext(), "Error: Usuario no identificado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val esTarea = rgTipo.checkedRadioButtonId == R.id.rbTarea

            val itemsCapturados = if (esTarea) {
                editTextsTareas
                    .map { it.text.toString() }
                    .filter { it.isNotBlank() }
                    .map { ItemTareaRequest(nombre = it, estaFinalizado = false) }
            } else null

            viewModel.crearNota(
                titulo = titulo,
                descripcion = descripcion,
                tipo = if (esTarea) "TAREAS" else "TEXTO",
                dni = usuarioDni,
                items = itemsCapturados
            )
        }

        // --- Observadores ---
        viewModel.notaCreada.observe(viewLifecycleOwner) { creada ->
            if (creada) {
                Toast.makeText(requireContext(), "¡Guardado con éxito!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}