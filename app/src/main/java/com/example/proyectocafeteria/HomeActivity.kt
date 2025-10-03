package com.example.proyectocafeteria

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario : TextView
    private lateinit var ivGenero : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
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


    }
}