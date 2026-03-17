package com.example.micolornote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.micolornote.Fragments.AdminViewModel
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.databinding.ActivityRegisterBinding
import com.example.micolornote.databinding.FragmentRegistroUsuarioAdminBinding

class RegistroUsuarioFragment : Fragment(R.layout.fragment_registro_usuario__admin) {

    private val adminViewModel: AdminViewModel by activityViewModels()
    private lateinit var binding: FragmentRegistroUsuarioAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroUsuarioAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistarAdmin.setOnClickListener {
            val p = Persona(
                dni = binding.etDniAdminCrear.text.toString(),
                nombre = binding.etNombreAdminCrear.text.toString(),
                password = binding.etPassAdminCrear.text.toString(),
                es_usuario = true,
                es_admin = false,
                fotoPerfil = null
            )
            adminViewModel.registrarUsuario(p)
        }

        adminViewModel.registroExitoso.observe(viewLifecycleOwner) { exito ->
            if(exito) {
                Toast.makeText(context, "¡Usuario creado!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()

            }
        }
    }
    //Arreglo actual -> ayudado por JuanRamon
    //Se ha modificado para que al moverte a traves del nav host se borren los datos (por ejemplo -
    //Si no han habido un "registro" de forma exitosa, que no haga mas, y se borren los datos que hubeira escritos
    override fun onPause() {
        super.onPause()
        binding.etDniAdminCrear.text?.clear()
        binding.etNombreAdminCrear.text?.clear()
        binding.etPassAdminCrear.text?.clear()
    }
}