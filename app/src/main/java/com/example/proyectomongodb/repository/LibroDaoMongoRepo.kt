package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro

interface LibroDaoMongoRepo {
    fun getall()
    fun getById(id: Int)
    fun insert(libro: Libro)
    fun update(libro: Libro)
    fun delete(id: Int)
}

