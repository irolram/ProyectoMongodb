package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroRepositoryMongo

class ServiceLibro(private val libroRepository: LibroRepositoryMongo) : IServiceLibro {

    //Funciones de la interfaz IServiceLibro

    //Función para obtener todos los libros, devuelve una lista de tipo Libro
    override suspend fun getall(): List<Libro> {
        return libroRepository.getall()
    }

    //Función para obtener el libro con un ID especifico, devuelve un Libro o nulo
    override suspend fun getById(id: Int): Libro? {

        return libroRepository.getById(id)
    }

    //Función para capturar errores al insertar un libro, devuelve un booleano
    override suspend fun insert(libro: Libro): Boolean {
        return try {
            require(libro.titulo.isNotEmpty()) { "El titulo no puede estar vacío" }
            require(libro.autor.isNotEmpty()){ "El autor no puede estar vacío" }
            require(libro.anioPublicacion <= 2027) { "El año no puede ser negativo" }
            libroRepository.insert(libro)
        } catch (e: Exception) {
            println("DEBUG ERROR en Service: ${e.message}")
            false
        }
    }
    // Función para capturar errores al actualizar un libro, devuelve un booleano
    override suspend fun update(id: Int, libro: Libro): Boolean {
        return try {
            require(libro.titulo.isNotEmpty()) { "El titulo no puede estar vacío" }
            require(libro.autor.isNotEmpty()) { "El autor no puede estar vacío" }
            require(libro.anioPublicacion >= 0) { "El año no puede ser negativo" }

            libroRepository.update(id, libro)
        } catch (e: Exception) {
            println("DEBUG ERROR en Service Update: ${e.message}")
            false
        }
    }

    // Función para capturar errores al borrar un libro, devuelve un booleano
    override suspend fun delete(id: Int): Boolean {
        return try {
            require(id >= 0) { "El ID no puede ser negativo" }
            return libroRepository.delete(id)

        }catch (e: Exception){
            println("DEBUG ERROR en Service Delete: ${e.message}")
            false
        }
    }
}