package com.example.proyectocafeteria.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.entity.CarritoItem
import com.example.proyectocafeteria.entity.Producto
import com.example.proyectocafeteria.ui.DetalleProductoActivity
import com.example.proyectocafeteria.ui.FavoritosActivity
import com.example.proyectocafeteria.ui.HomeActivity

class ProductoAdapter(private val productos: MutableList<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    fun actualizarLista(nuevaLista: List<Producto>) {
        productos.clear()
        productos.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagen: ImageView = itemView.findViewById(R.id.ivImagen)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val ivFavoritos: ImageView = itemView.findViewById(R.id.ivFavoritos)
        val ivAgregar: ImageView = itemView.findViewById(R.id.ivAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.ivImagen.setImageResource(producto.imageUrl)
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "S/ ${producto.precio}"

        // Mostrar corazón vacío o relleno
        holder.ivFavoritos.setImageResource(
            if (producto.esFavorito) R.drawable.ic_corazon_relleno
            else R.drawable.ic_corazon
        )

        // Clic en el corazón
        holder.ivFavoritos.setOnClickListener {
            producto.esFavorito = !producto.esFavorito
            holder.ivFavoritos.setImageResource(
                if (producto.esFavorito) R.drawable.ic_corazon_relleno
                else R.drawable.ic_corazon
            )

            // Notificar a HomeActivity
            HomeActivity.adaptadorHome?.notifyDataSetChanged()

            // Notificar a FavoritosActivity si está abierta
            if (holder.itemView.context is FavoritosActivity) {
                (holder.itemView.context as FavoritosActivity).actualizarFavoritos()
            }

            // Mostrar mensaje de Toast
            Toast.makeText(
                holder.itemView.context,
                if (producto.esFavorito) "Agregado a favoritos"
                else "Eliminado de favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Clic en el ícono de agregar al carrito
        holder.ivAgregar.setOnClickListener {
            val item = CarritoItem(
                id = producto.id,
                nombre = producto.nombre,
                descripcion = producto.descripcion,
                precio = producto.precio,
                imageUrl = producto.imageUrl,
                cantidad = 1
            )
            DetalleProductoActivity.carritoGlobal.add(item)
            Toast.makeText(
                holder.itemView.context,
                "Agregado: ${producto.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleProductoActivity::class.java)
            intent.putExtra("idProducto", producto.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}
