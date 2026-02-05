package com.example.micolornote.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.micolornote.R
import com.example.micolornote.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // 1. Definimos el binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // 2. Definimos el ViewModel (como en tu ejemplo)
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflamos el layout
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- CLICS DE BOTONES ---

        binding.btnLogin.setOnClickListener {
            val dni = binding.txtDni.text.toString().trim()
            val pass = binding.txtPassword.text.toString().trim()

            if (dni.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Faltan datos", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(dni, pass)
            }
        }

        binding.btnIrARegistro.setOnClickListener {
            // Navegación usando NavController
            findNavController().navigate(R.id.login_al_registro)
        }

        // --- OBSERVADORES ---

        loginViewModel.personaLogueada.observe(viewLifecycleOwner) { persona ->
            if (persona != null) {
                Toast.makeText(requireContext(), "Hola ${persona.nombre}", Toast.LENGTH_SHORT).show()
                when {
                    persona.esAdmin ->{
                        findNavController().navigate(R.id.login_al_admin)
                    }
                    persona.esUsuario ->{
                        findNavController().navigate(R.id.login_al_usuario)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Rol no válido",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        loginViewModel.mensajeError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }


}