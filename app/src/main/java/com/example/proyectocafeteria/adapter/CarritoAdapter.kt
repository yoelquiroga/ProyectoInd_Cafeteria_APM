package com.example.proyectocafeteria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.entity.CarritoItem

class CarritoAdapter (
    private val carrito: List<CarritoItem>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagen: ImageView = itemView.findViewById(R.id.ivImagen)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = carrito[position]
        holder.ivImagen.setImageResource(item.imageUrl)
        holder.tvNombre.text = item.nombre
        holder.tvPrecio.text = "S/ ${String.format("%.2f", item.precio)}"

        holder.btnEliminar.setOnClickListener {
            onEliminar(position)
        }
    }
    override fun getItemCount(): Int {
        return carrito.size
    }
}