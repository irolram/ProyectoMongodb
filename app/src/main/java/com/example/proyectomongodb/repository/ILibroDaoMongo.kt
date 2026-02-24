package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro

interface ILibroDaoMongo {
    suspend fun getall(): List<Libro>

    suspend fun getById(id: Int): Libro?

    suspend fun insert(libro: Libro): Boolean

    // Cambiamos 'update(libro)' por esto para que el repositorio
    // sepa exactamente qué ID mandar a la ruta de Node.js
    suspend fun update(id: Int, libro: Libro): Boolean

    suspend fun delete(id: Int): Boolean
}