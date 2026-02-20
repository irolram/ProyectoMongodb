package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro
// CORRECCIÓN: Usamos el ObjectId nativo de Java/BSON en lugar del kbson
import org.bson.types.ObjectId

interface ILibroDaoMongo {
    suspend fun getall(): List<Libro>
    suspend fun getById(id: ObjectId): Libro
    suspend fun insert(libro: Libro): Boolean
    suspend fun update(libro: Libro): Boolean
    suspend fun delete(id: ObjectId): Boolean
}