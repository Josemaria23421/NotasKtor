package com.example.micolornote.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.Modelos.Notas.CrearNotaRequest
import com.example.micolornote.CrearNotaViewModel
import com.example.micolornote.Models.Nota.ItemTareaRequest
import com.example.micolornote.R
import com.example.micolornote.databinding.FragmentAsignarTareaBinding
import com.example.micolornote.databinding.FragmentRegistroUsuarioAdminBinding

import java.text.SimpleDateFormat
import java.util.*

class AsignarTareasFragment : Fragment(R.layout.fragment_asignar_tarea) {

    private val adminViewModel: AdminViewModel by viewModels()
    private val crearNotaViewModel : CrearNotaViewModel by viewModels()
    private lateinit var binding: FragmentAsignarTareaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAsignarTareaBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val editTextsTareas = mutableListOf<EditText>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1 Cargar usuarios en el Spinner
        adminViewModel.usuarios.observe(viewLifecycleOwner) { lista ->
            if (!lista.isNullOrEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, lista.map { it.nombre })
                binding.spinnerUsuarios.adapter = adapter
            }
        }
        adminViewModel.cargarUsuarios()

        // 2 Lógica para añadir filas de tareas dinámicamente
        binding.btnAgregarItem.setOnClickListener {
            val nuevoItemET = EditText(requireContext()).apply {
                hint = "Tarea #${editTextsTareas.size + 1}"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
            }
            //revisar
            binding.containerItems.addView(nuevoItemET)
            editTextsTareas.add(nuevoItemET)

            nuevoItemET.requestFocus()
        }
        // 3 Logica Pra guardar Una Tarea
        binding.btnCrearYAsignar.setOnClickListener {
            val titulo = binding.etTituloTarea.text.toString()

            if (titulo.isEmpty()) {
                binding.etTituloTarea.error = "Falta el título"
                return@setOnClickListener
            }

            val dniParaEnviar = if (binding.rgAsignacion.checkedRadioButtonId == R.id.rbManual) {
                val listaUsuarios = adminViewModel.usuarios.value
                val pos = binding.spinnerUsuarios.selectedItemPosition

                // Primero comprobamos que la lista no este vacia
                // Segundo comprobamos que la posición sea válida para evitar que la app explote
                if (listaUsuarios != null && pos >= 0 && pos < listaUsuarios.size) {
                    val usuarioSeleccionado = listaUsuarios[pos]
                    usuarioSeleccionado.dni // Devolvemos el DNI real
                } else {
                    "" // Si algo falla (lista vacía o error), devolvemos texto vacío
                }
            } else {
                "AUTO"
            }

            val subTareas = editTextsTareas.mapNotNull { et ->
                val texto = et.text.toString()
                if (texto.isNotBlank()) ItemTareaRequest(texto, false) else null
            }

            val request = CrearNotaRequest(
                titulo = titulo,
                descripcion_completa = binding.etDescTarea.text.toString(),
                tipo = "TAREAS",
                fecha = crearNotaViewModel.obtenerFechaActual(),
                dni = dniParaEnviar,
                items = subTareas.ifEmpty { null }
            )

            adminViewModel.crearYAsignarTarea(request)
        }

        // 4 Observar el resultado de la operación
        adminViewModel.operacionExitosa.observe(viewLifecycleOwner) { exito ->
            if (exito) {
                Toast.makeText(context, "Guardado y asignado correctamente", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }
    override fun onPause() {
        super.onPause()
        binding.etTituloTarea.text.clear()
        binding.etDescTarea.text.clear()
        binding.containerItems.removeAllViews()
        editTextsTareas.clear()
        binding.spinnerUsuarios.setSelection(0)
        binding.rgAsignacion.check(R.id.rbManual) // O el que tengas por defecto
    }
}