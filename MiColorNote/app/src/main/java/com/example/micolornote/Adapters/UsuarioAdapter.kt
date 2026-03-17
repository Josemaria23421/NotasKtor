package com.example.micolornote.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Holder.UsuarioHolder
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.R
import com.example.micolornote.databinding.ItemCardUsuarioBinding

class UsuarioAdapter(
    private val onDetalleClick: (Persona) -> Unit
) : ListAdapter<Persona, UsuarioAdapter.UsuarioViewHolder>(DiffCallback()) {

    inner class UsuarioViewHolder(private val binding: ItemCardUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(usuario: Persona) {
            binding.tvNombre.text = usuario.nombre
            binding.tvDni.text = "DNI: ${usuario.dni}"

            // Función solicitada para gestionar la navegación
            fun irADetalleDelUsuario() {
                UsuarioHolder.usuario = usuario
                // Usamos el ID de la acción que definimos en el nav_admin.xml
                binding.root.findNavController().navigate(R.id.action_adminFragment_to_usuarioDetalleFragment)
            }

            // Click en el botón de detalle
            binding.btDetalle.setOnClickListener {
                irADetalleDelUsuario()
            }

            // Click en toda la tarjeta
            binding.root.setOnClickListener {
                binding.root.setOnClickListener {

                    val detalle = "Nombre: ${usuario.nombre}\nDNI: ${usuario.dni}"

                    Toast.makeText(binding.root.context, detalle, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemCardUsuarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Persona>() {
        override fun areItemsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return oldItem.dni == newItem.dni
        }

        override fun areContentsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return oldItem == newItem
        }
    }
}