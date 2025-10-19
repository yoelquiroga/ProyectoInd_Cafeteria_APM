package com.example.proyectocafeteria.entity

data class CarritoItem(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imageUrl: Int,
    var cantidad: Int = 1
)