package com.example.micolornote.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.micolornote.Models.Nota.ItemTarea
import com.example.micolornote.R

class ItemsTareaAdapter(
    private val onToggle: (ItemTarea) -> Unit,
    private val onDelete: (ItemTarea) -> Unit
) : ListAdapter<ItemTarea, ItemsTareaAdapter.TareaViewHolder>(DiffCallback()) {

    inner class TareaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val check = view.findViewById<CheckBox>(R.id.cbTarea)
        val tvNombre = view.findViewById<TextView>(R.id.tvNombreTarea)
        val btnDelete = view.findViewById<ImageButton>(R.id.btnBorrarTarea)

        fun bind(item: ItemTarea) {
            tvNombre.text = item.nombre
            check.isChecked = item.estaFinalizado

            check.setOnClickListener { onToggle(item) }
            btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea_detalle, parent, false)
        return TareaViewHolder(v)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ItemTarea>() {
        override fun areItemsTheSame(old: ItemTarea, new: ItemTarea) = old.id == new.id
        override fun areContentsTheSame(old: ItemTarea, new: ItemTarea) = old == new
    }
}