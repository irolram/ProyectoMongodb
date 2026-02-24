package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro

interface IServiceLibro {
    suspend fun getall(): List<Libro>

    suspend fun getById(id: Int): Libro?

    suspend fun insert(libro: Libro): Boolean

    suspend fun update(id: Int, libro: Libro): Boolean

    suspend fun delete(id: Int): Boolean
}