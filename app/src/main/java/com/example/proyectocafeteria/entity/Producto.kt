package com.example.proyectocafeteria.entity

data class Producto (

        val id: String,
        val nombre: String,
        val descripcion: String,
        val precio: Double,
        val imageUrl: Int,
        val categoria: String,
        var esFavorito: Boolean = false
)