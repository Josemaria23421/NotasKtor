package com.example.micolornote.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.micolornote.Fragments.LoginViewModel
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels() // Activity scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val dni = binding.txtDni.text.toString().trim()
            val pass = binding.txtPassword.text.toString().trim()

            if (dni.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(dni, pass)
            }
        }

        binding.btnIrARegistro.setOnClickListener {
            // Aquí puedes lanzar tu activity de registro si existe
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Observamos el LiveData del ViewModel
        loginViewModel.personaLogueada.observe(this) { personaRecibida ->
            if (personaRecibida != null) {
                navegarSegunRol(personaRecibida)
            }
        }

        loginViewModel.mensajeError.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun navegarSegunRol(persona: Persona) {
        UsuarioHolder.dni = persona.dni
        UsuarioHolder.usuario = persona

        // CASO 1: Tiene AMBOS roles -> Preguntamos
        if (persona.es_admin && persona.es_usuario) {
            mostrarDialogoSeleccionRol(persona)
        }
        // CASO 2: Solo es Admin
        else if (persona.es_admin) {
            irAAdmin()
        }
        // CASO 3: Solo es Usuario
        else if (persona.es_usuario) {
            irAUser()
        }
        else {
            Toast.makeText(this, "No tienes roles asignados", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoSeleccionRol(persona: Persona) {
        val opciones = arrayOf("Entrar como Administrador", "Entrar como Usuario")

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Selecciona un perfil")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> irAAdmin()
                    1 -> irAUser()
                }
            }
            .show()
    }

    private fun irAAdmin() {
        startActivity(Intent(this, AdminMainActivity::class.java))
        finish()
    }

    private fun irAUser() {
        startActivity(Intent(this, UserMainActivity::class.java))
        finish()
    }
}
