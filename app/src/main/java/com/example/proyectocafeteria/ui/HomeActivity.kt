package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var tvCerraSesion: TextView
    private lateinit var ivGenero: ImageView

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

        //Traer el nombre del usuario al HOME
        val nombreusuario = intent.getStringExtra("nombreusuario") ?: "Usuario"
        val generoid = intent.getStringExtra("generoid") ?: "no se encontro img"

        when (generoid) {
            "Masculino" -> ivGenero.setImageResource(R.drawable.ic_hombre)
            "Femenino" -> ivGenero.setImageResource(R.drawable.ic_mujer)
            else -> ivGenero.setImageResource(R.drawable.ic_otros)
        }
        tvNombreUsuario.text = "$nombreusuario"

        //Boton de cerrar sesión
        tvCerraSesion.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
        }

        // Configurar el RecyclerView
        val rvProductos = findViewById<RecyclerView>(R.id.rvProductos)
        rvProductos.layoutManager = GridLayoutManager(this, 2) // 2 columnas
        rvProductos.adapter = ProductoAdapter(getMockProducts())

        // 👇 Configurar BottomNavigationView para abrir el carrito
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_carrito -> {
                    val intent = Intent(this, CarritoActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> {
                    bottomNav.menu.findItem(R.id.nav_carrito).isChecked = false
                    false
                }
            }
        }

    }
        // Datos temporales de prueba para después cargarlos cuando nos enseñe SqlLite
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

