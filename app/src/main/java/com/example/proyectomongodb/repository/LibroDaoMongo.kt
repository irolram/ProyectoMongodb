package com.example.proyectomongodb.repository

import ApiBibliotecaService
import com.example.proyectomongodb.model.Libro
import retrofit2.Response

class LibroDaoMongo(private val apiService: ApiBibliotecaService): ILibroDaoMongo {

    override suspend fun getall(): List<Libro> {
        return try {
            apiService.getLibros()
        } catch (e: Exception) {
            println("Error al obtener libros: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getById(id: Int): Libro? {
        val response = apiService.getLibrosById(id)
        return response.body()
    }


    override suspend fun insert(libro: Libro): Boolean {
        return try {
            // 1. Llamamos a la API y guardamos la respuesta completa
            val response = apiService.insertarLibro(libro)

            // 2. Comprobamos si el código HTTP es de éxito (200-299)
            if (response.isSuccessful) {
                println(" Servidor: Libro guardado correctamente")
                true
            } else {
                // Aquí verás si Node.js rechazó el libro (ej. por ID duplicado)
                println("Servidor rechazó el libro: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            // Aquí verás si ni siquiera llegó al PC (ej. IP 10.0.2.2 mal)
            println(" Error de conexión: ${e.message}")
            false
        }
    }

    override suspend fun update(id: Int, libro: Libro): Boolean {
        return try {
            apiService.updateLibro(id, libro)
            true
        } catch (e: Exception) {
            println("Error al actualizar: ${e.message}")
            false
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return try {
            apiService.deleteLibro(id)
            true
        } catch (e: Exception) {
            println("Error al borrar: ${e.message}")
            false
        }
    }
}