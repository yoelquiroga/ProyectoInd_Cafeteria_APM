package com.example.proyectocafeteria.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

    companion object {
        val carritoGlobal = mutableListOf<CarritoItem>()
    }

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idProducto = intent.getStringExtra("idProducto") ?: return

        // Buscar el producto en la lista global
        val producto = HomeActivity.listaCompleta.find { it.id == idProducto } ?: return

        // Mostrar datos
        ivImagen.setImageResource(producto.imageUrl)
        tvNombre.text = producto.nombre
        tvDescripcion.text = producto.descripcion
        tvPrecio.text = "S/ ${producto.precio}"

        // Mostrar estado de favorito
        ivFavorito.setImageResource(
            if (producto.esFavorito) R.drawable.ic_corazon_relleno
            else R.drawable.ic_corazon
        )

        // Cambiar de imagen al hacer click al ic_corazon
        ivFavorito.setOnClickListener {
            producto.esFavorito = !producto.esFavorito
            ivFavorito.setImageResource(
                if (producto.esFavorito) R.drawable.ic_corazon_relleno
                else R.drawable.ic_corazon
            )

            HomeActivity.adaptadorHome?.notifyDataSetChanged()
            Toast.makeText(this,
                if (producto.esFavorito) "Agregado a favoritos"
                else "Eliminado de favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }

        // agregarr al carrito
        btnAgregarCarrito.setOnClickListener {
            val item = CarritoItem(producto.id, producto.nombre, producto.precio, producto.imageUrl)
            carritoGlobal.add(item)
            Toast.makeText(this, "Agregado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
        }


        ivBack.setOnClickListener { finish() }
    }

}