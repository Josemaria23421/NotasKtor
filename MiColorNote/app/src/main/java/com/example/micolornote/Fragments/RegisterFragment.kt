package com.example.micolornote.Fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.R
import com.example.micolornote.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    // 1. Definimos el binding
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // 2. Definimos el ViewModel (como en tu ejemplo)
    private val registerViewModel: RegisterViewModel by viewModels()

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Aquí es donde le dices qué XML usar
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Boteones
        binding.btnRegistrar.setOnClickListener {
            val dni = binding.txtRegDni.text.toString().trim()
            val pass = binding.txtRegPassword.text.toString().trim()
            val nombre = binding.txtRegNombre.text.toString().trim()
            if(nombre.isEmpty() || dni.isEmpty() || pass.isEmpty()){
                Toast.makeText(requireContext(), "Faltan datos", Toast.LENGTH_SHORT).show()
            }else{
                registerViewModel.registro(Persona(
                    dni = dni,
                    nombre = nombre,
                    password = pass,
                    fotoPerfil = null,
                    esUsuario = true,
                    esAdmin = false
                ))
            }
        }
        binding.btnIrALogin.setOnClickListener {
            // Navegación usando NavController
            findNavController().navigate(R.id.registro_al_login)
        }

        // -- OBSERVERS --
        registerViewModel.personaRegistrada.observe(viewLifecycleOwner){
            persona ->
            if (persona != null) {
                Toast.makeText(requireContext(), "Hola ${persona.nombre}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.registro_al_login)
            }
        }
        registerViewModel.mensajeError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}