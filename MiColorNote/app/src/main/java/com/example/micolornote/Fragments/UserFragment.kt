package com.example.micolornote.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Adapters.NotasAdapter
import com.example.micolornote.Holder.NotaHolder
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.Models.Nota.Notas
import com.example.micolornote.R

class UserFragment : Fragment(R.layout.fragment_user) {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapterNotas : NotasAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dni = UsuarioHolder.dni

        if (dni == null) {
            Toast.makeText(requireContext(), "Usuario no logueado", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("LoginDebug", "Usuario DNI: $dni")

        val rvNotas = view.findViewById<RecyclerView>(R.id.rvNotas)
        rvNotas.layoutManager = LinearLayoutManager(requireContext())

        adapterNotas = NotasAdapter(
            onNotaClick = { nota ->
                NotaHolder.nota = nota
                findNavController().navigate(R.id.fragment_detalle_nota)
            },
            onNotaLongClick = { nota ->
                mostrarDialogoConfirmacion(nota)
            }
        )

        rvNotas.adapter = adapterNotas

        // Observar notas del usuario
        userViewModel.notas.observe(viewLifecycleOwner) { notas ->
            adapterNotas.submitList(notas)
        }

        // Observar errores
        userViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("LoginDebug", "Usuario ID: ${userViewModel.usuario.value?.dni}")

        // Cargar notas del usuario logueado
        userViewModel.cargarNotas(dni)
    }

    // El Alert para borrar manteniendo el estilo de la app
    private fun mostrarDialogoConfirmacion(nota: Notas) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Nota")
            .setMessage("¿Estás seguro de que quieres borrar \"${nota.titulo}\"?")
            .setPositiveButton("Sí, eliminar") { _, _ ->
                // Le pasamos el marrón al ViewModel
                userViewModel.borrarNota(nota.id)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}