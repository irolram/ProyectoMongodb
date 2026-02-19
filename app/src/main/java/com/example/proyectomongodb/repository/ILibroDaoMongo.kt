package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro

interface ILibroDaoMongo {
    suspend fun getall(): List<Libro>
    suspend fun getById(id: Int): Libro
    suspend fun insert(libro: Libro): Boolean
    suspend fun update(libro: Libro): Boolean
    suspend fun delete(id: Int): Boolean
}

