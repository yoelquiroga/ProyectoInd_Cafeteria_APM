package com.example.proyectocafeteria.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.entity.CarritoItem
import com.example.proyectocafeteria.entity.Producto

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var ivImagen: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var btnAgregarCarrito: Button
    private lateinit var ivBack: ImageView
    private lateinit var ivFavorito: ImageView
    private lateinit var btnMenos: TextView
    private lateinit var btnMas: TextView
    private lateinit var tvCantidad: TextView

    companion object {
        val carritoGlobal = mutableListOf<CarritoItem>()
    }

    private var cantidad = 1
    private var precioUnitario = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        ivImagen = findViewById(R.id.ivImagen)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvPrecio = findViewById(R.id.tvPrecio)
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito)
        ivBack = findViewById(R.id.ivBack)
        ivFavorito = findViewById(R.id.ivFavorito)
        btnMenos = findViewById(R.id.btnMenos)
        btnMas = findViewById(R.id.btnMas)
        tvCantidad = findViewById(R.id.tvCantidad)


        ivBack.setOnClickListener {
            finish()
        }

        // Inicializar el TextView de cantidad
        tvCantidad.text = cantidad.toString()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idProducto = intent.getIntExtra("idProducto", -1)
        if (idProducto == -1) {
            finish()
            return
        }

        // Buscar el producto en la lista global
        val producto = HomeActivity.listaCompleta.find { it.id == idProducto } ?: run {
            finish()
            return
        }

        // Mostrar datos
        ivImagen.setImageResource(producto.imageUrl)
        tvNombre.text = producto.nombre
        tvDescripcion.text = producto.descripcion
        precioUnitario = producto.precio
        tvPrecio.text = "S/ ${String.format("%.2f", producto.precio)}"

        // Mostrar estado de favorito
        ivFavorito.setImageResource(
            if (producto.esFavorito) R.drawable.ic_corazon_relleno_blanco
            else R.drawable.ic_corazon_blanco
        )

        // Cambiar de imagen al hacer click al corazón
        ivFavorito.setOnClickListener {
            producto.esFavorito = !producto.esFavorito
            ivFavorito.setImageResource(
                if (producto.esFavorito) R.drawable.ic_corazon_relleno_blanco
                else R.drawable.ic_corazon_blanco
            )

            HomeActivity.adaptadorHome?.notifyDataSetChanged()
            Toast.makeText(
                this,
                if (producto.esFavorito) "Agregado a favoritos"
                else "Eliminado de favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Botones + y -
        btnMenos.setOnClickListener {
            if (cantidad > 1) {
                cantidad--
                tvCantidad.text = cantidad.toString()
                actualizarPrecioTotal()
            }
        }

        btnMas.setOnClickListener {
            cantidad++
            tvCantidad.text = cantidad.toString()
            actualizarPrecioTotal()
        }

        // Agregar al carrito con la cantidad seleccionada
        btnAgregarCarrito.setOnClickListener {
            // Buscar si el producto ya está en el carrito
            val existingItem = carritoGlobal.find { it.id == producto.id }

            if (existingItem != null) {
                // Si ya existe, actualiza la cantidad
                existingItem.cantidad += cantidad
                Toast.makeText(
                    this,
                    "Actualizado: ${producto.nombre} x ${existingItem.cantidad}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Si no existe, créalo
                val newItem = CarritoItem(
                    producto.id,
                    producto.nombre,
                    producto.descripcion,
                    producto.precio,
                    producto.imageUrl,
                    cantidad
                )
                carritoGlobal.add(newItem)
                Toast.makeText(this, "Agregado: ${producto.nombre} x $cantidad", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun actualizarPrecioTotal() {
        val precioTotal = precioUnitario * cantidad
        tvPrecio.text = "S/ ${String.format("%.2f", precioTotal)}"
    }
}