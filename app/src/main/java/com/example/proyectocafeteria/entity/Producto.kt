package com.example.proyectocafeteria.entity

import com.example.proyectocafeteria.R

data class Producto(
        val id: Int = 0, // Autoincremental en BD
        val nombre: String,
        val descripcion: String,
        val precio: Double,
        val imagenNombre: String,
        val categoria: String,
        var esFavorito: Boolean = false
) {
val imageUrl: Int
        get() = when (imagenNombre) {
                "ame1" -> R.drawable.ame1
                "ame2" -> R.drawable.ame2
                "capu1" -> R.drawable.capu1
                "capu2" -> R.drawable.capu2
                "capu3" -> R.drawable.capu3
                "capu4" -> R.drawable.capu4
                "capu5" -> R.drawable.capu5
                "expre1" -> R.drawable.expre1
                "expre2" -> R.drawable.expre2
                "latte1" -> R.drawable.latte1
                "latte2" -> R.drawable.latte2
                "latte3" -> R.drawable.latte3
                "latte4" -> R.drawable.latte4
                else -> R.drawable.capu1 // default
        }
}