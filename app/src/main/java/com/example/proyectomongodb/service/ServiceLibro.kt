package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroDaoMongo
import retrofit2.Response

class ServiceLibro(private val libroDao: LibroDaoMongo) : IServiceLibro {

    override suspend fun getall(): List<Libro> {
        return libroDao.getall()
    }

    override suspend fun getById(id: Int): Libro? {
        return libroDao.getById(id)
    }


    override suspend fun insert(libro: Libro): Boolean {
        return try {
            if (libro.titulo.isEmpty() || libro.autor.isEmpty()) {
                println("DEBUG: Fallo de validación en Service (campos vacíos)")
                return false
            }
            libroDao.insert(libro)
        } catch (e: Exception) {
            println("DEBUG ERROR en Service: ${e.message}")
            false
        }
    }

    override suspend fun update(id: Int, libro: Libro): Boolean {
        return try {
            require(libro.titulo.isNotEmpty()) { "El titulo no puede estar vacío" }
            require(libro.autor.isNotEmpty()) { "El autor no puede estar vacío" }
            require(libro.anioPublicacion >= 0) { "El año no puede ser negativo" }

            libroDao.update(id, libro)
        } catch (e: Exception) {
            println("DEBUG ERROR en Service Update: ${e.message}")
            false
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return libroDao.delete(id)
    }
}