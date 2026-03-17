package com.example.micolornote.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.R

class UsuarioDetalleFragment : Fragment() {

    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_usuario_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Traemos el usuario directamente del Holder
        val usuario = UsuarioHolder.usuario!!

        val tvNombre = view.findViewById<TextView>(R.id.tvNombreDetalle)
        val tvDni = view.findViewById<TextView>(R.id.tvDniDetalle)
        val cbEsUsuario = view.findViewById<CheckBox>(R.id.cbEsUsuarioDetalle)
        val cbEsAdmin = view.findViewById<CheckBox>(R.id.cbEsAdminDetalle)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarRoles)
        val btnBorrar = view.findViewById<Button>(R.id.btnBorrarUsuario)

        // Seteamos los datos en la UI
        tvNombre.text = usuario.nombre
        tvDni.text = usuario.dni
        cbEsUsuario.isChecked = usuario.es_usuario
        cbEsAdmin.isChecked = usuario.es_admin

        // Acción Guardar
        btnGuardar.setOnClickListener {
            usuario.es_usuario = cbEsUsuario.isChecked
            usuario.es_admin = cbEsAdmin.isChecked

            adminViewModel.actualizarRoles(usuario.dni, usuario)
        }
       // Acción Borrar
        btnBorrar.setOnClickListener {
            adminViewModel.borrarUsuario(usuario.dni)
        }
        // Observamos el éxito para navegar hacia atrás
        adminViewModel.operacionExitosa.observe(viewLifecycleOwner) { exito ->
            if (exito) {
                Toast.makeText(context, "Cambio Realizado COn exito", Toast.LENGTH_SHORT).show()
                adminViewModel.resetStatus()
                findNavController().popBackStack()
            }
        }

        // Observamos posibles errores
        adminViewModel.error.observe(viewLifecycleOwner) { msg ->
            if (msg != null) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}