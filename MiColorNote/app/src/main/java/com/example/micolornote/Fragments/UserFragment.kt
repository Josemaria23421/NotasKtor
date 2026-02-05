package com.example.micolornote.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.micolornote.R

class UserFragment : Fragment(R.layout.fragment_user) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ADMIN", "Persona: ${loginViewModel.personaLogueada.value}")

        val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        val txtDni = view.findViewById<TextView>(R.id.txtDni)
        val txtRol = view.findViewById<TextView>(R.id.txtRol)

        loginViewModel.personaLogueada.observe(viewLifecycleOwner) { persona ->
            persona?.let {
                txtNombre.text = it.nombre
                txtDni.text = "DNI: ${it.dni}"
                txtRol.text = "Rol: Usuario"
            }
        }
    }
}