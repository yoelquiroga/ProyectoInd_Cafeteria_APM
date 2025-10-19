package com.example.proyectocafeteria.ui

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.adapter.ProductoAdapter
import com.example.proyectocafeteria.data.AppDatabaseHelper
import com.example.proyectocafeteria.entity.Producto
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvTituloCategoria: TextView
    private lateinit var rvProductos: RecyclerView
    private var categoriaActual: String = "Todas"
    private lateinit var bottomNav: BottomNavigationView

    companion object {
        var listaCompleta: List<Producto> = emptyList()
        var adaptadorHome: ProductoAdapter? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvTituloCategoria = findViewById(R.id.tvTituloCategoria)
        rvProductos = findViewById(R.id.rvProductos)
        bottomNav = findViewById(R.id.bottomNav)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombreusuario = intent.getStringExtra("nombreusuario") ?: "Usuario"
        tvNombreUsuario.text = "Bienvenido, $nombreusuario"

        // Cargar productos desde la base de datos
        cargarProductosDesdeBD()

        rvProductos.layoutManager = GridLayoutManager(this, 2)
        val adaptador = ProductoAdapter(listaCompleta.toMutableList())
        rvProductos.adapter = adaptador
        adaptadorHome = adaptador

        findViewById<Button>(R.id.btnEspresso).setOnClickListener { filtrarPorCategoria("Espresso") }
        findViewById<Button>(R.id.btnCappuccino).setOnClickListener { filtrarPorCategoria("Cappuccino") }
        findViewById<Button>(R.id.btnLatte).setOnClickListener { filtrarPorCategoria("Latte") }
        findViewById<Button>(R.id.btnAmericano).setOnClickListener { filtrarPorCategoria("Americano") }

        findViewById<TextView>(R.id.tvVerTodo).setOnClickListener {
            filtrarPorCategoria("Todas")
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_carrito -> {
                    val intent = Intent(this, CarritoActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }
                R.id.nav_favoritos -> {
                    val intent = Intent(this, FavoritosActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }
                R.id.nav_perfil -> {
                    val intent = Intent(this, PerfilActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }
                else -> {
                    bottomNav.menu.findItem(R.id.nav_home).isChecked = true
                    false
                }
            }
        }
    }

    private fun cargarProductosDesdeBD() {
        val dbHelper = AppDatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM producto", null)

        val productos = mutableListOf<Producto>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_producto"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
                val imagenNombre = cursor.getString(cursor.getColumnIndexOrThrow("imagen_nombre"))

                productos.add(Producto(id, nombre, descripcion, precio, imagenNombre, categoria))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        listaCompleta = productos

        // Si la BD está vacía, insertar datos de ejemplo (solo una vez)
        if (listaCompleta.isEmpty()) {
            insertarProductosDeEjemplo()
            cargarProductosDesdeBD() // Recargar
        }
    }

    private fun insertarProductosDeEjemplo() {
        val dbHelper = AppDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val productosEjemplo = listOf(
            Producto(0, "Cappuccino", "Cappuccino, Milk", 12.5, "capu1", "Cappuccino"),
            Producto(0, "Espresso", "Espresso", 9.5, "expre1", "Espresso"),
            Producto(0, "Latte", "Latte, Milk", 12.0, "latte1", "Latte"),
            Producto(0, "Americano", "Americano, Water", 9.0, "ame1", "Americano"),
            Producto(0, "Mocha", "Cappuccino, Chocolate, Milk", 13.0, "capu5", "Cappuccino"),
            Producto(0, "Flat White", "Cappuccino, Steamed Milk", 12.5, "capu3", "Cappuccino"),
            Producto(0, "Cold Brew", "CappuccinoCold Brew Coffee", 12.0, "capu4", "Cappuccino"),
            Producto(0, "Macchiato", "Latte, Foam", 9.5, "latte2", "Latte"),
            Producto(0, "Caramel Macchiato", "Cappuccino, Milk, Caramel", 13.0, "capu2", "Cappuccino"),
            Producto(0, "Vanilla Latte", "Latte, Milk, Vanilla", 12.5, "latte3", "Latte"),
            Producto(0, "Hazelnut Latte", "Latte, Milk, Hazelnut", 12.5, "latte4", "Latte"),
            Producto(0, "Doble Espresso", "Dos shots de Espresso", 10.0, "expre2", "Espresso"),
            Producto(0, "Iced Americano", "Americano, Cold Water", 9.5, "ame2", "Americano")
        )

        for (p in productosEjemplo) {
            val valores = android.content.ContentValues().apply {
                put("nombre", p.nombre)
                put("descripcion", p.descripcion)
                put("precio", p.precio)
                put("categoria", p.categoria)
                put("imagen_nombre", p.imagenNombre)
            }
            db.insert("producto", null, valores)
        }
        db.close()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.menu.findItem(R.id.nav_home).isChecked = true
    }

    private fun filtrarPorCategoria(categoria: String) {
        categoriaActual = categoria

        val listaFiltrada = if (categoria == "Todas") {
            listaCompleta
        } else {
            listaCompleta.filter { it.categoria == categoria }
        }

        // Actualizar el título
        tvTituloCategoria.text = if (categoria == "Todas") {
            "Café Populares"
        } else {
            "Nuestros cafés $categoria"
        }

        // Actualizar el adaptador existente
        adaptadorHome?.actualizarLista(listaFiltrada.toMutableList())
    }
}