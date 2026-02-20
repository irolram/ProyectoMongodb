package com.example.proyectomongodb.repository

import android.annotation.SuppressLint
import com.example.proyectomongodb.model.Libro
import com.mongodb.client.MongoClients
import org.bson.Document
import org.bson.types.ObjectId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LibroDaoMongo : ILibroDaoMongo {

    @SuppressLint("AuthLeak")
// Cambia esto en LibroDaoMongo.kt
    private val connectionString ="mongodb+srv://ivanv2:t50pH1lYDeH3qfla@cluster0.q2o8nms.mongodb.net/?appName=Cluster0"
    private val client by lazy { MongoClients.create(connectionString) }
    private val database by lazy { client.getDatabase("BibliotecaEjercicio") }
    private val collection by lazy { database.getCollection("Biblioteca") }

    override suspend fun getall(): List<Libro> = withContext(Dispatchers.IO) {
        val listaLibros = mutableListOf<Libro>()
        try {
            collection.find().forEach { document ->
                listaLibros.add(documentToLibro(document))
            }
        } catch (e: Exception) {
            println("Error en getall: ${e.message}")
        }
        listaLibros
    }

    override suspend fun getById(id: ObjectId): Libro = withContext(Dispatchers.IO) {
        val filtro = Document("_id", id)
        val doc = collection.find(filtro).firstOrNull()
        doc?.let { documentToLibro(it) } ?: throw Exception("El libro no existe")
    }

    override suspend fun insert(libro: Libro): Boolean = withContext(Dispatchers.IO) {
        try {
            val doc = Document("_id", libro._id)
                .append("titulo", libro.titulo)
                .append("autor", libro.autor)
                .append("genero", libro.genero)
                .append("anioPublicacion", libro.anioPublicacion)
                .append("editorial", libro.editorial)
                .append("paginas", libro.paginas)
                .append("disponible", libro.disponible)

            val result = collection.insertOne(doc)
            result.wasAcknowledged()
        } catch (e: Exception) {
            println("Error al insertar: ${e.message}")
            false
        }
    }

    override suspend fun update(libro: Libro): Boolean = withContext(Dispatchers.IO) {
        val filtro = Document("_id", libro._id)
        val nuevoDocumento = Document("_id", libro._id)
            .append("titulo", libro.titulo)
            .append("autor", libro.autor)
            .append("genero", libro.genero)
            .append("anioPublicacion", libro.anioPublicacion)
            .append("editorial", libro.editorial)
            .append("paginas", libro.paginas)
            .append("disponible", libro.disponible)

        val result = collection.replaceOne(filtro, nuevoDocumento)
        result.modifiedCount > 0
    }

    override suspend fun delete(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val result = collection.deleteOne(Document("_id", id))
        result.deletedCount > 0
    }

    private fun documentToLibro(doc: Document): Libro {
        return Libro(
            _id = doc.getObjectId("_id") ?: ObjectId(),
            titulo = doc.getString("titulo") ?: "Sin título",
            autor = doc.getString("autor") ?: "Desconocido",
            genero = doc.getString("genero") ?: "",
            anioPublicacion = doc.getInteger("anioPublicacion") ?: 0,
            editorial = doc.getString("editorial") ?: "",
            paginas = doc.getInteger("paginas") ?: 0,
            disponible = doc.getBoolean("disponible") ?: false
        )
    }
}