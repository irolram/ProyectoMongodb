package com.example.proyectomongodb.repository

import ApiBibliotecaService
import com.example.proyectomongodb.model.Libro

class LibroRepositoryMongo(private val apiService: ApiBibliotecaService): ILibroRepositoryMongo {

    //Función para obtener todos los libros, devuelve una lista de tipo Libro
    override suspend fun getall(): List<Libro> {
        return try {
            //Llama a la funcion getLibros del apiService y devuelve la lista
            apiService.getLibros()
        } catch (e: Exception) {
            println("Error al obtener libros: ${e.message}")
            emptyList()
        }
    }

    //Función para obtener el libro con un ID especifico, devuelve un Libro o nulo
    override suspend fun getById(id: Int): Libro? {
        //Llama a la funcion getLibrosById del apiService y devuelve el libro
        return apiService.getLibrosById(id).body()
    }


    //Función para insertar un libro, devuelve un booleano
    override suspend fun insert(libro: Libro): Boolean {
        return try {
            //Variable para saber si se ha insertado correctamente el libro
            val response = apiService.insertarLibro(libro)

            // Si es exitoso devuelve true, sino false
            if (response.isSuccessful) {
                println(" Servidor: Libro guardado correctamente")
                true
            } else {
                println("Servidor rechazó el libro: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            println(" Error de conexión: ${e.message}")
            false
        }
    }

    //Función para actualizar un libro, devuelve un booleano
    override suspend fun update(id: Int, libro: Libro): Boolean {
        return try {
            //Llama a la función updateLibro del apiService y devuelve true si sale bien
            apiService.updateLibro(id, libro)
            true
        } catch (e: Exception) {
            println("Error al actualizar: ${e.message}")
            false
        }
    }

    // Función para borrar un libro, devuelve un booleano
    override suspend fun delete(id: Int): Boolean {
        return try {
            //Llama a la funcion deleteLibro del apiService y devuelve true si sale bien
            apiService.deleteLibro(id)
            true
        } catch (e: Exception) {
            println("Error al borrar: ${e.message}")
            false
        }
    }
}