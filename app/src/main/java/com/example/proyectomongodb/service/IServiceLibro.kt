package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro

interface IServiceLibro {
    suspend fun getall(): List<Libro>

    // Quitamos el "?" para cumplir con tu requisito de no devolver nulos
    suspend fun getById(id: Int): Libro?

    suspend fun insert(libro: Libro): Boolean

    // Añadimos el ID al update para que el Service sepa a quién actualizar
    suspend fun update(id: Int, libro: Libro): Boolean

    suspend fun delete(id: Int): Boolean
}