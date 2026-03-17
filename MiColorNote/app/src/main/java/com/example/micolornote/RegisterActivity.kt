package com.example.micolornote.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.micolornote.Fragments.RegisterViewModel
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val dni = binding.txtRegDni.text.toString().trim()
            val pass = binding.txtRegPassword.text.toString().trim()
            val nombre = binding.txtRegNombre.text.toString().trim()

            if (nombre.isEmpty() || dni.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevaPersona = Persona(
                    dni = dni,
                    nombre = nombre,
                    password = pass,
                    fotoPerfil = null,
                    es_usuario = true,
                    es_admin = false
                )
                registerViewModel.registro(nuevaPersona)
            }
        }

        binding.btnIrALogin.setOnClickListener {
            // Volver al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Observadores
        registerViewModel.personaRegistrada.observe(this) { persona ->
            persona?.let {
                Toast.makeText(this, "Hola ${it.nombre}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        registerViewModel.mensajeError.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
