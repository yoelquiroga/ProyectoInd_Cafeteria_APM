package com.example.proyectocafeteria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.entity.CarritoItem
import com.example.proyectocafeteria.ui.CarritoActivity

class CarritoAdapter(
    private val carrito: List<CarritoItem>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagen: ImageView = itemView.findViewById(R.id.ivImagen)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val btnMenos: TextView = itemView.findViewById(R.id.btnMenos)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val btnMas: TextView = itemView.findViewById(R.id.btnMas)
        val tvPrecioUnitario: TextView = itemView.findViewById(R.id.tvPrecioUnitario)
        val tvPrecioTotal: TextView = itemView.findViewById(R.id.tvPrecioTotal)
        val ivEliminar: ImageView = itemView.findViewById(R.id.ivEliminar)
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
        holder.tvDescripcion.text = item.descripcion
        holder.tvPrecioUnitario.text = "S/ ${String.format("%.2f", item.precio)}"

        // Cantidad
        holder.tvCantidad.text = item.cantidad.toString()

        // Precio total del ítem
        val precioTotal = item.precio * item.cantidad
        holder.tvPrecioTotal.text = "S/ ${String.format("%.2f", precioTotal)}"

        // Botón Menos
        holder.btnMenos.setOnClickListener {
            if (item.cantidad > 1) {
                item.cantidad--
                holder.tvCantidad.text = item.cantidad.toString()
                holder.tvPrecioTotal.text = "S/ ${String.format("%.2f", item.precio * item.cantidad)}"
                // Notificar al CarritoActivity para actualizar el total general
                val activity = holder.itemView.context as? CarritoActivity
                activity?.actualizarTotal()
            }
        }

        // Botón Más
        holder.btnMas.setOnClickListener {
            item.cantidad++
            holder.tvCantidad.text = item.cantidad.toString()
            holder.tvPrecioTotal.text = "S/ ${String.format("%.2f", item.precio * item.cantidad)}"
            // Notificar al CarritoActivity para actualizar el total general
            val activity = holder.itemView.context as? CarritoActivity
            activity?.actualizarTotal()
        }

        // Botón Eliminar
        holder.ivEliminar.setOnClickListener {
            onEliminar(position)
            // Mostrar mensaje de eliminación
            Toast.makeText(
                holder.itemView.context,
                "Producto eliminado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return carrito.size
    }
}