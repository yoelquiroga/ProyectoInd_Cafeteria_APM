package com.example.proyectocafeteria.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.data.AppDatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import com.example.proyectocafeteria.entity.Usuario

class RegistrarseActivity : AppCompatActivity() {
    private lateinit var tietCorreoRegis : TextInputEditText
    private lateinit var tietNombreUsu : TextInputEditText
    private lateinit var tietContraseñaRegis : TextInputEditText
    private lateinit var tietConfirmarContraseña : TextInputEditText
    private lateinit var tietTelefono: TextInputEditText
    private lateinit var btnRegistrarse : Button
    private lateinit var tvIniciarSesion : TextView
    private lateinit var  rgSexo : RadioGroup



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)


        tietCorreoRegis = findViewById(R.id.CorreoRegis)
        tietNombreUsu = findViewById(R.id.tietNombreUsu)
        tietContraseñaRegis = findViewById(R.id.tietContraseñaRegis)
        tietConfirmarContraseña = findViewById(R.id.tietConfirmarContraseña)
        tietTelefono = findViewById(R.id.tietTelefono)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        tvIniciarSesion = findViewById(R.id.tvIniciarSesion)
        rgSexo = findViewById(R.id.rgSexo)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                maxOf(systemBars.bottom, imeInsets.bottom)
            )
            insets
        }

        // Registrar Datos para el Login
        btnRegistrarse.setOnClickListener {
            val correo = tietCorreoRegis.text.toString().trim()
            val nombre = tietNombreUsu.text.toString().trim()
            val contraseña = tietContraseñaRegis.text.toString().trim()
            val confirmar = tietConfirmarContraseña.text.toString().trim()
            val telefono = tietTelefono.text.toString().trim() // 👈 Teléfono
            val generoId = rgSexo.checkedRadioButtonId

            // Validaciones
            if (correo.isEmpty()) {
                Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Ingrese un nombre de usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contraseña.isEmpty()) {
                Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmar.isEmpty()) {
                Toast.makeText(this, "Confirme la contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmar != contraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (telefono.length != 9) {
                Toast.makeText(this, "El teléfono debe tener 9 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (generoId == -1) {
                Toast.makeText(this, "Elija un género", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val genero = when(generoId){
                R.id.rbMasculino -> "Masculino"
                R.id.rbFemenino -> "Femenino"
                else -> "Otros"
            }


            // Guardar en la base de datos
            val dbHelper = AppDatabaseHelper(this)
            val db = dbHelper.writableDatabase

            // Verificar si el correo ya existe
            val cursor = db.rawQuery("SELECT correo FROM usuario WHERE correo = ?", arrayOf(correo))
            if (cursor.count > 0) {
                cursor.close()
                db.close()
                Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            cursor.close()

            // Insertar nuevo usuario
            val valores = ContentValues().apply {
                put("nombres", nombre)
                put("celular", telefono)
                put("correo", correo)
                put("clave", contraseña)
            }

            val resultado = db.insert("usuario", null, valores)
            db.close()

            if (resultado != -1L) {
                // Registro exitoso → ir directamente a HomeActivity
                Toast.makeText(this, "Registro exitoso. Bienvenido!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("nombreusuario", nombre)
                intent.putExtra("generoid", genero)
                startActivity(intent)
                finish() // Cierra RegistroActivity para que no se pueda volver con "back"
            } else {
                Toast.makeText(this, "Error al registrar. Intente nuevamente.", Toast.LENGTH_SHORT).show()
            }
        }


        //Ir al Login
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
        }

    }
}