package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.entity.Usuario


class PerfilActivity : AppCompatActivity() {

    private lateinit var ivFotoPerfil: ImageView
    private lateinit var tvNombrePerfil: TextView
    private lateinit var tvCorreoPerfil: TextView
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        ivFotoPerfil = findViewById(R.id.ivFotoPerfil)
        tvNombrePerfil = findViewById(R.id.tvNombrePerfil)
        tvCorreoPerfil = findViewById(R.id.tvCorreoPerfil)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Leer datos del usuario desde SharedPreferences
        val prefs = getSharedPreferences("RegistroUsu", MODE_PRIVATE)
        val usuario = Usuario(
            correo = prefs.getString("correoregis", "") ?: "",
            nombre = prefs.getString("nombreusuario", "") ?: "",
            genero = prefs.getString("generoid", "") ?: "",
            contraseña = prefs.getString("passregis", "") ?: ""
        )

        // Mostrar datos
        tvNombrePerfil.text = usuario.nombre
        tvCorreoPerfil.text = usuario.correo

        // Establecer imagen según género
        when (usuario.genero) {
            "Masculino" -> ivFotoPerfil.setImageResource(R.drawable.ic_perfil_user)
            "Femenino" -> ivFotoPerfil.setImageResource(R.drawable.ic_perfil_user)
            else -> ivFotoPerfil.setImageResource(R.drawable.ic_perfil_user)
        }

        // Botón Cerrar Sesión
        btnCerrarSesion.setOnClickListener {
            // Limpiar SharedPreferences
            with(prefs.edit()) {
                clear()
                apply()
            }
            // Ir a AccesoActivity
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
            finish() // Cierra PerfilActivity
        }
    }
}