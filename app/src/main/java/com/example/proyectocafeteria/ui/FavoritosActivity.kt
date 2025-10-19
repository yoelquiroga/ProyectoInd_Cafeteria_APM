package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.adapter.ProductoAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritosActivity : AppCompatActivity() {
    private lateinit var rvFavoritos: RecyclerView
    private lateinit var adaptadorFavoritos: ProductoAdapter
    private lateinit var ivRegresar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)

        ivRegresar = findViewById(R.id.ivRegresar)
        rvFavoritos = findViewById(R.id.rvFavoritos)
        rvFavoritos.layoutManager = GridLayoutManager(this, 2)

        // Inicializar con una lista vacía mutable
        adaptadorFavoritos = ProductoAdapter(mutableListOf())
        rvFavoritos.adapter = adaptadorFavoritos
        actualizarFavoritos() // Llenar la lista

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivRegresar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@FavoritosActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    fun actualizarFavoritos() {
        val favoritos = HomeActivity.listaCompleta.filter { it.esFavorito }
        adaptadorFavoritos.actualizarLista(favoritos.toMutableList())
    }

    override fun onResume() {
        super.onResume()
        actualizarFavoritos()
    }
}