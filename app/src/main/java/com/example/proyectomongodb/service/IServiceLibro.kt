package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro
import org.bson.types.ObjectId

interface IServiceLibro {
    suspend fun getall(): List<Libro>
    suspend fun getById(id: ObjectId): Libro
    suspend fun insert(libro: Libro): Boolean
    suspend fun update(libro: Libro): Boolean
    suspend fun delete(id: ObjectId): Boolean
}