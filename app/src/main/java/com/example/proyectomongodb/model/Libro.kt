package com.example.proyectomongodb.model

import org.bson.types.ObjectId
data class Libro(
    val id: ObjectId = ObjectId(),
    val titulo: String,
    val autor: String,
    val genero: String,
    val anioPublicacion: Int,
    val editorial: String,
    val paginas: Int,
    val disponible: Boolean
)
