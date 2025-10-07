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


    // Lista global para el carrito
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

        // Recibir solo el id del prodcuto
        val idProducto = intent.getStringExtra("idProducto") ?: return

        // Buscar el producto en la lista (igual que en HomeActivity)
        val producto = getMockProducts().find { it.id == idProducto } ?: return

        // Mostrar datoss
        ivImagen.setImageResource(producto.imageUrl)
        tvNombre.text = producto.nombre
        tvDescripcion.text = producto.descripcion
        tvPrecio.text = "$${producto.precio}"

        // Botón agregar al carrito
        btnAgregarCarrito.setOnClickListener {
            val item = CarritoItem(producto.id, producto.nombre, producto.precio, producto.imageUrl)
            DetalleProductoActivity.carritoGlobal.add(item)
            Toast.makeText(this, "Agregado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
        }

        // Botón Volver
        ivBack.setOnClickListener {
            finish()
        }

        // Botón Favorito
        ivFavorito.setOnClickListener {
            Toast.makeText(this, "Marcado como favorito", Toast.LENGTH_SHORT).show()
        }

    }
    private fun getMockProducts(): List<Producto> {
        return listOf(
            Producto("1", "Cappuccino", "Espresso, Milk", 12.5, R.drawable.capu1),
            Producto("2", "Espresso", "Espresso", 9.5, R.drawable.expre1),
            Producto("3", "Latte", "Espresso, Milk", 12.0, R.drawable.latt1),
            Producto("4", "Americano", "Espresso, Water", 9.0, R.drawable.ame1),
            Producto("5", "Mocha", "Espresso, Chocolate, Milk", 15.0, R.drawable.ame3),
            Producto("6", "Flat White", "Espresso, Steamed Milk", 12.5, R.drawable.capu5),
            Producto("7", "Cold Brew", "Cold Brew Coffee", 12.0, R.drawable.capu6),
            Producto("8", "Macchiato", "Espresso, Foam", 9.5, R.drawable.latte2)
        )
    }

}

