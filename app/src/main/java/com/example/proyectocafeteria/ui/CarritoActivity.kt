package com.example.proyectocafeteria.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocafeteria.R
import com.example.proyectocafeteria.adapter.CarritoAdapter
import com.example.proyectocafeteria.entity.CarritoItem
import com.example.proyectocafeteria.ui.DetalleProductoActivity

class CarritoActivity : AppCompatActivity() {

    private lateinit var rvCarrito: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnPagar: Button
    private lateinit var ivRegresar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)

        rvCarrito = findViewById(R.id.rvCarrito)
        tvTotal = findViewById(R.id.tvTotal)
        btnPagar = findViewById(R.id.btnPagar)
        ivRegresar = findViewById(R.id.ivRegresar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCarrito.layoutManager = LinearLayoutManager(this)
        rvCarrito.adapter = CarritoAdapter(DetalleProductoActivity.carritoGlobal) { posicion ->
            DetalleProductoActivity.carritoGlobal.removeAt(posicion)
            rvCarrito.adapter?.notifyDataSetChanged()
            actualizarTotal()
        }

        ivRegresar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        actualizarTotal()

        btnPagar.setOnClickListener {
            if (DetalleProductoActivity.carritoGlobal.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Pago procesado!", Toast.LENGTH_SHORT).show()
                DetalleProductoActivity.carritoGlobal.clear()
                rvCarrito.adapter?.notifyDataSetChanged()
                actualizarTotal()
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@CarritoActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun actualizarTotal() {
        val total = DetalleProductoActivity.carritoGlobal.sumOf { it.precio }
        tvTotal.text = "Total: S/ ${String.format("%.2f", total)}"
    }
}