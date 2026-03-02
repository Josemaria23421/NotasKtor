package com.example.micolornote.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Models.Nota.Notas
import com.example.micolornote.databinding.ItemCardNotaBinding

class NotasAdapter(
    private val onNotaClick: (Notas) -> Unit,
    private val onNotaLongClick: (Notas) -> Unit
) : ListAdapter<Notas, NotasAdapter.NotaViewHolder>(DiffCallback()) {

    inner class NotaViewHolder(
        private val binding: ItemCardNotaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nota: Notas) {
            binding.txtTitulo.text = nota.titulo

            binding.txtFecha.text = nota.fecha

            binding.txtTipoBadge.text = nota.tipo.uppercase()

            if (nota.tipo.equals("TAREAS", ignoreCase = true)) {
                binding.txtTipoBadge.setBackgroundColor(Color.parseColor("#BBDEFB"))
                binding.txtTipoBadge.setTextColor(Color.parseColor("#1976D2"))
            } else {
                binding.txtTipoBadge.setBackgroundColor(Color.parseColor("#F5F5F5"))
                binding.txtTipoBadge.setTextColor(Color.parseColor("#616161"))
            }

            binding.root.setOnClickListener {
                onNotaClick(nota)
            }
            binding.root.setOnLongClickListener {
                onNotaLongClick(nota)
                true // Retornamos true para indicar que hemos gestionado el click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemCardNotaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Notas>() {
        override fun areItemsTheSame(oldItem: Notas, newItem: Notas): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notas, newItem: Notas): Boolean {
            return oldItem == newItem
        }
    }
}