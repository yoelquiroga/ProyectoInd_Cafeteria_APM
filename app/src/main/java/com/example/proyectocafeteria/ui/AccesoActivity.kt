package com.example.proyectocafeteria.ui

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocafeteria.ui.HomeActivity
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.data.AppDatabaseHelper
import com.example.proyectocafeteria.ui.RegistrarseActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.proyectocafeteria.entity.Usuario

class AccesoActivity : AppCompatActivity() {
    private lateinit var tietCorreologin : TextInputEditText
    private lateinit var tietContraseñalogin : TextInputEditText
    private lateinit var tvRegistrarse : TextView
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acceso)

        tietCorreologin = findViewById(R.id.tietCorreologin)
        tietContraseñalogin = findViewById(R.id.tietContraseñalogin)
        tvRegistrarse = findViewById(R.id.tvRegistrarse)
        btnLogin = findViewById(R.id.btnLogin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btnLogin.setOnClickListener {
            val correo = tietCorreologin.text.toString().trim()
            val contraseña = tietContraseñalogin.text.toString().trim()

            // Validar campos vacíos
            if (correo.isEmpty()) {
                Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contraseña.isEmpty()) {
                Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Conectar a la base de datos
            val dbHelper = AppDatabaseHelper(this)
            val db = dbHelper.readableDatabase

            // Consultar usuario
            val cursor: Cursor = db.rawQuery(
                "SELECT nombres, sexo FROM usuario WHERE correo = ? AND clave = ?",
                arrayOf(correo, contraseña)
            )

            // Verificar si se encontró un resultado
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombres"))
                val genero = cursor.getString(cursor.getColumnIndexOrThrow("sexo"))

                cursor.close()
                db.close()

                // Ir a HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("nombreusuario", nombre)
                intent.putExtra("generoid", genero)
                startActivity(intent)
                finish() // Evita volver al login con el botón "back"
            } else {
                // Credenciales incorrectas
                cursor.close()
                db.close()

                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                tietCorreologin.setText("")
                tietContraseñalogin.setText("")
            }
        }

        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }
    }
}