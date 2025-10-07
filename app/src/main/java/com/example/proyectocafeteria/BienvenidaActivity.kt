package com.example.proyectocafeteria

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import com.example.proyectocafeteria.ui.AccesoActivity


class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

            // Referencia al botón
            val btnEmpezar = findViewById<Button>(R.id.btnEmpezar)

            // Acción: abrir AccesoActivity
            btnEmpezar.setOnClickListener {
                val intent = Intent(this, AccesoActivity::class.java)
                startActivity(intent)
                finish() // opcional: cierra la BienvenidaActivity para que no regrese al splash

        }
    }
}