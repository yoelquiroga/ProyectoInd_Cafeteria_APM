package com.example.proyectocafeteria

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario : TextView
    private lateinit var tvCerraSesion : TextView

    private lateinit var ivGenero : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvCerraSesion = findViewById(R.id.tvCerrarSesion)
        ivGenero = findViewById(R.id.ivGenero)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombreusuario =  intent.getStringExtra("nombreusuario") ?: "Usuario"
        val generoid = intent.getStringExtra("generoid") ?:"no se encontro img"


        when(generoid){
            "Masculino" -> ivGenero.setImageResource(R.drawable.ic_hombre)
            "Femenino" -> ivGenero.setImageResource(R.drawable.ic_mujer)
            else -> ivGenero.setImageResource(R.drawable.ic_otros)
        }
        tvNombreUsuario.text = "$nombreusuario"


        tvCerraSesion.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
           startActivity(intent)
        }


        // Configurar RecyclerView
        val rvProductos = findViewById<RecyclerView>(R.id.rvProductos)
        rvProductos.layoutManager = GridLayoutManager(this, 2) // 2 columnas
        rvProductos.adapter = ProductAdapter(getMockProducts())


//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
//        bottomNav.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_explorador -> {
//                    Toast.makeText(this, "Explorar", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_carrito -> {
//                    Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_favoritos -> {
//                    Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_perfil -> {
//                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
//                    true
//                }
//               else -> false
//            }
//        }
    }

    // Modelo de producto
    data class Product(
        val id: String,
        val nombre: String,
        val descripcion: String,
        val precio: Double,
        val imageUrl: Int
    )

    // Adaptador para RecyclerView
    inner class ProductAdapter(private val producto: List<Product>) :
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ivImagen: ImageView = itemView.findViewById(R.id.ivImagen)
            val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
            val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
            val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
            val btnAdd: ImageView = itemView.findViewById(R.id.btnAdd)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_productos, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val productos = producto[position]
            holder.ivImagen.setImageResource(productos.imageUrl)
            holder.tvNombre.text = productos.nombre
            holder.tvDescripcion.text = productos.descripcion
            holder.tvPrecio.text = "$${productos.precio}"
            holder.btnAdd.setOnClickListener {
                // Simular agregar al carrito
                Toast.makeText(holder.itemView.context, " Agregado: ${productos.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount() = producto.size
    }

    // Datos mock temporales para después cargarlos cuando nos enseñe SqlLite
    private fun getMockProducts(): List<Product> {
        return listOf(
            Product("1", "Cappuccino", "Espresso, Milk", 4.5, R.drawable.capu1),
            Product("2", "Espresso", "Espresso", 3.5, R.drawable.expre1),
            Product("3", "Latte", "Espresso, Milk", 4.0, R.drawable.latt1),
            Product("4", "Americano", "Espresso, Water", 3.0, R.drawable.ame1),
            Product("5", "Mocha", "Espresso, Chocolate, Milk", 5.0, R.drawable.ame3),
            Product("6", "Flat White", "Espresso, Steamed Milk", 4.5, R.drawable.capu5),
            Product("7", "Cold Brew", "Cold Brew Coffee", 4.0, R.drawable.capu6),
            Product("8", "Macchiato", "Espresso, Foam", 3.5, R.drawable.latte2)
        )
    }
}