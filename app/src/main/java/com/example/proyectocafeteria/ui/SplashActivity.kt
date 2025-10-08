package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocafeteria.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Instalar el splash screen nativo
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Animación del logo
        val logo = findViewById<ImageView>(R.id.icSplash)
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.deslizar_in)
        logo.startAnimation(slideIn)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BienvenidaActivity::class.java))
            finish()
        }, 2000)
    }
}