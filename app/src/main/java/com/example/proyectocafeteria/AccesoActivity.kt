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

        val prefs = getSharedPreferences("RegistroUsu", Context.MODE_PRIVATE)
        val correoregistro = prefs.getString("correoregis", "")
        val contraseñaregistro = prefs.getString("passregis", "")
        val nombreusu = prefs.getString("nombreusuario", "")

        //Iniciar Sesion en Home
        btnLogin.setOnClickListener {

            val correologin = tietCorreologin.text.toString().trim()
            val contraseñalogin = tietContraseñalogin.text.toString().trim()

            if (correologin.isEmpty()){
                Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contraseñalogin.isEmpty()){
                Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (correologin != correoregistro && contraseñalogin == contraseñaregistro){
                Toast.makeText(this, "El correo que ingreso no existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                tietCorreologin.setText("")
            }
            if (correologin == correoregistro && contraseñalogin != contraseñaregistro){
                Toast.makeText(this, "La contraseña que ingreso es incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                tietCorreologin.setText("")
            }
            if (correologin != correoregistro && contraseñalogin != contraseñaregistro){
                Toast.makeText(this, "La cuenta que ingreso no existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                tietCorreologin.setText("")
                tietContraseñalogin.setText("")
            }else{

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("nombreusuario", nombreusu)
                startActivity(intent)
            }

        }




        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }


    }
}