package com.example.proyectocafeteria.entity

data class DetallePedido(
    val id: Int = 0,
    val id_pedido: Int,
    val id_producto: Int,
    val cantidad: Int,
    val precio_unitario: Double,
    val precio_total: Double
)