package com.example.micolornote.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Adapters.UsuarioAdapter
import com.example.micolornote.R

class AdminFragment : Fragment(R.layout.fragment_admin) {
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var adapter: UsuarioAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvUsuarios = view.findViewById<RecyclerView>(R.id.rvUsuarios)
        rvUsuarios.layoutManager = LinearLayoutManager(requireContext())

        adapter = UsuarioAdapter { usuario ->
            Toast.makeText(requireContext(), "Detalle: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
        }
        rvUsuarios.adapter = adapter

        viewModel.usuarios.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        // Esto carga los datos reales
        viewModel.cargarUsuarios()
    }
}
