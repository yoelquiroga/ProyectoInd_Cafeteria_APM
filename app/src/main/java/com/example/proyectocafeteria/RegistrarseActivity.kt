package com.example.proyectocafeteria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var tietCorreoRegis : TextInputEditText
    private lateinit var tietNombreUsu : TextInputEditText
    private lateinit var tietContraseñaRegis : TextInputEditText
    private lateinit var tietConfirmarContraseña : TextInputEditText
    private lateinit var btnRegistrarse : Button
    private lateinit var tvIniciarSesion : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)


        tietCorreoRegis = findViewById(R.id.CorreoRegis)
        tietNombreUsu = findViewById(R.id.tietNombreUsu)
        tietContraseñaRegis = findViewById(R.id.tietContraseñaRegis)
        tietConfirmarContraseña = findViewById(R.id.tietConfirmarContraseña)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        tvIniciarSesion = findViewById(R.id.tvIniciarSesion)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Registrar Datos para el Login
        btnRegistrarse.setOnClickListener {

            val correoregis = tietCorreoRegis.text.toString().trim()
            val nombreusu = tietNombreUsu.text.toString().trim()
            val contraseñaregis = tietContraseñaRegis.text.toString().trim()
            val confirmarpass = tietConfirmarContraseña.text.toString().trim()

            if (correoregis.isEmpty()){
                Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nombreusu.isEmpty()){
                Toast.makeText(this, "Ingrese un nombre de usurio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contraseñaregis.isEmpty()){
                Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmarpass.isEmpty()){
                Toast.makeText(this, "Confirme la contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmarpass != contraseñaregis){
                Toast.makeText(this, "Las contraseña no son iguales", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("RegistroUsu", Context.MODE_PRIVATE)
            with(prefs.edit()) {
                putString("passregis", contraseñaregis)
                putString("nombreusuario", nombreusu)
                putString("correoregis", correoregis)
                apply()
            }

                val intent = Intent(this, AccesoActivity::class.java)
                startActivity(intent)

        }



        //Ir al Login
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
        }


    }
}