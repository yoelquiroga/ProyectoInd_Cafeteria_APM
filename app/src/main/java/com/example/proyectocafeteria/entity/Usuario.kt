package com.example.proyectocafeteria.entity

data class Usuario(
    val id: Int = 0,
    val correo: String,
    val nombre: String,
    val genero: String,
    val contraseña: String
)