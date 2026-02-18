package com.example.proyectomongodb.model

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val genero: String,
    val anioPublicacion: Int,
    val editorial: String,
    val paginas : Int,
    val disponible : Boolean
)
