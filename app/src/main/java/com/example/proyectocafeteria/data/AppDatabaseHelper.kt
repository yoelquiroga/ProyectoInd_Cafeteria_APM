package com.example.proyectocafeteria.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "cafeteria.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla USUARIO
        db.execSQL("""
            CREATE TABLE usuario (
                id_usuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                dni TEXT,
                nombres TEXT,
                celular TEXT,
                sexo TEXT,
                correo TEXT UNIQUE,
                clave TEXT
            )
        """.trimIndent())

        // Tabla PRODUCTO
        db.execSQL("""
            CREATE TABLE producto (
                id_producto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombre TEXT,
                descripcion TEXT,
                precio REAL,
                categoria TEXT,
                imagen_nombre TEXT
            )
        """.trimIndent())

        // Tabla PEDIDO
        db.execSQL("""
            CREATE TABLE pedido (
                id_pedido INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                fecha TEXT,
                id_usuario INTEGER,
                FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario)
            )
        """.trimIndent())

        // Tabla DETALLE_PEDIDO
        db.execSQL("""
            CREATE TABLE detalle_pedido (
                id_detalle INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                id_pedido INTEGER,
                id_producto INTEGER,
                cantidad INTEGER,
                precio_unitario REAL,
                precio_total REAL,
                FOREIGN KEY(id_pedido) REFERENCES pedido(id_pedido),
                FOREIGN KEY(id_producto) REFERENCES producto(id_producto)
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS detalle_pedido")
        db.execSQL("DROP TABLE IF EXISTS pedido")
        db.execSQL("DROP TABLE IF EXISTS producto")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        onCreate(db)
    }
}