package com.example.proyectomongodb.service

import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroDaoMongo

class ServiceLibro(private val libroDao: LibroDaoMongo): IServiceLibro {

    override suspend fun getall(): List<Libro> {

        return libroDao.getall()
    }

    override suspend fun getById(id: Int): Libro {

        require(id >= 0) { "El id no puede ser negativo" }
        return libroDao.getById(id)

    }

    override suspend fun insert(libro: Libro): Boolean {
        require(libro.id >= 0) { "El id no puede ser negativo" }
        require(libro.titulo.isNotEmpty()) { "El titulo no puede estar vacío" }
        require(libro.autor.isNotEmpty()) { "El autor no puede estar vacío" }
        require(libro.genero.isNotEmpty()) { "El genero no puede estar vacío" }
        require(libro.anioPublicacion >= 0) { "El año de publicación no puede ser negativo" }
        require(libro.editorial.isNotEmpty()) { "La editorial no puede estar vacía" }
        require(libro.paginas >= 0) { "El número de páginas no puede ser negativo" }

        return libroDao.insert(libro)

    }

    override suspend fun update(libro: Libro): Boolean {

        require(libro.id >= 0) { "El id no puede ser negativo" }
        require(libro.titulo.isNotEmpty()) { "El titulo no puede estar vacío" }
        require(libro.autor.isNotEmpty()) { "El autor no puede estar vacío" }
        require(libro.genero.isNotEmpty()) { "El genero no puede estar vacío" }
        require(libro.anioPublicacion >= 0) { "El año de publicación no puede ser negativo" }
        require(libro.editorial.isNotEmpty()) { "La editorial no puede estar vacía" }
        require(libro.paginas >= 0) { "El número de páginas no puede ser negativo" }
        return libroDao.update(libro)

    }

    override suspend fun delete(id: Int): Boolean {

        require(id >= 0) { "El id no puede ser negativo" }
        return libroDao.delete(id)

    }

}