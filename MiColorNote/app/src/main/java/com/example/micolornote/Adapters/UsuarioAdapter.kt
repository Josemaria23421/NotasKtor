package com.example.micolornote.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.micolornote.Models.Persona.Persona
import com.example.micolornote.databinding.ItemCardUsuarioBinding

class UsuarioAdapter(
    private val onDetalleClick: (Persona) -> Unit
) : ListAdapter<Persona, UsuarioAdapter.UsuarioViewHolder>(DiffCallback()) {

    inner class UsuarioViewHolder(private val binding: ItemCardUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(usuario: Persona) {
            binding.tvNombre.text = usuario.nombre
            binding.tvDni.text = "DNI: ${usuario.dni}"
            if (!usuario.fotoPerfil.isNullOrEmpty()) {
                Glide.with(binding.imageViewPerfil.context)
                    .load(usuario.fotoPerfil)
                    .into(binding.imageViewPerfil)
            }
            // Click en botón detalle
            binding.btDetalle.setOnClickListener {
                onDetalleClick(usuario)
            }

            // Click en toda la tarjeta → mostrar detalle
            binding.root.setOnClickListener {
                val detalle = "Nombre: ${usuario.nombre}\nDNI: ${usuario.dni}"
                Toast.makeText(binding.root.context, detalle, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemCardUsuarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
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
