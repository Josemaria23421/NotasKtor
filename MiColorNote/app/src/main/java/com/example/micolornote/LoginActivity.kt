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
        loginViewModel.personaLogueada.observe(this) { persona ->
            persona?.let { navegarSegunRol(it) }
        }

        loginViewModel.mensajeError.observe(this) { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun navegarSegunRol(persona: Persona) {
        UsuarioHolder.dni = persona.dni
        UsuarioHolder.usuario = persona
        Toast.makeText(this, "Hola ${persona.nombre}", Toast.LENGTH_SHORT).show()

        if (persona.esAdmin){
            val intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
            finish()
        }else if(persona.esUsuario){
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
        }
    }
}
