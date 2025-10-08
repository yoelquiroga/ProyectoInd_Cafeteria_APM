package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
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
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.adapter.ProductoAdapter
import com.example.proyectocafeteria.entity.Producto
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var tvNombreUsuario: TextView

    private lateinit var ivGenero: ImageView
    private lateinit var rvProductos: RecyclerView
    private var categoriaActual: String = "Todas"
    private lateinit var bottomNav: BottomNavigationView

    companion object {
        lateinit var listaCompleta: List<Producto>
        var adaptadorHome: ProductoAdapter? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        ivGenero = findViewById(R.id.ivGenero)
        rvProductos = findViewById(R.id.rvProductos)
        bottomNav = findViewById(R.id.bottomNav)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreusuario = intent.getStringExtra("nombreusuario") ?: "Usuario"
        val generoid = intent.getStringExtra("generoid") ?: "no se encontro img"

        when (generoid) {
            "Masculino" -> ivGenero.setImageResource(R.drawable.ic_hombre)
            "Femenino" -> ivGenero.setImageResource(R.drawable.ic_mujer)
            else -> ivGenero.setImageResource(R.drawable.ic_otros)
        }
        tvNombreUsuario.text = "$nombreusuario"





        //Lista productos

        listaCompleta = getMockProducts()

        rvProductos.layoutManager = GridLayoutManager(this, 2)
        val adaptador = ProductoAdapter(listaCompleta)
        rvProductos.adapter = adaptador
        adaptadorHome = adaptador

        findViewById<Button>(R.id.btnEspresso).setOnClickListener { filtrarPorCategoria("Espresso") }
        findViewById<Button>(R.id.btnCappuccino).setOnClickListener { filtrarPorCategoria("Cappuccino") }
        findViewById<Button>(R.id.btnLatte).setOnClickListener { filtrarPorCategoria("Latte") }
        findViewById<Button>(R.id.btnAmericano).setOnClickListener { filtrarPorCategoria("Americano") }

        findViewById<TextView>(R.id.tvVerTodo).setOnClickListener {
            filtrarPorCategoria("Todas")
        }

        //Navega entre iconos del navegation,del home carrito y eso
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
        rvProductos.adapter = ProductoAdapter(listaFiltrada)
    }

    private fun getMockProducts(): List<Producto> {
        return listOf(
            Producto("1", "Cappuccino", "Espresso, Milk", 12.5, R.drawable.capu1, "Cappuccino"),
            Producto("2", "Espresso", "Espresso", 9.5, R.drawable.expre1, "Espresso"),
            Producto("3", "Latte", "Espresso, Milk", 12.0, R.drawable.latt1, "Latte"),
            Producto("4", "Americano", "Espresso, Water", 9.0, R.drawable.ame1, "Americano"),
            Producto("5", "Mocha", "Espresso, Chocolate, Milk", 15.0, R.drawable.ame3, "Cappuccino"),
            Producto("6", "Flat White", "Espresso, Steamed Milk", 12.5, R.drawable.capu5, "Espresso"),
            Producto("7", "Cold Brew", "Cold Brew Coffee", 12.0, R.drawable.capu6, "Americano"),
            Producto("8", "Macchiato", "Espresso, Foam", 9.5, R.drawable.latte2, "Espresso")
        )
    }
}