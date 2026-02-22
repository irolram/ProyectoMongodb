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
// Usa esta versión para evitar el crash en Android
    private val connectionString = "mongodb://ivanv2:ola@ac-fr4zkyq-shard-00-00.q2o8nms.mongodb.net:27017,ac-fr4zkyq-shard-00-01.q2o8nms.mongodb.net:27017,ac-fr4zkyq-shard-00-02.q2o8nms.mongodb.net:27017/?ssl=true&replicaSet=atlas-fr4zkyq-shard-0&authSource=admin&retryWrites=true&w=majority"
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
            println("Error en get all: ${e.message}")
        }
        listaLibros
    }

    override suspend fun getById(id: ObjectId): Libro = withContext(Dispatchers.IO) {
        val filtro = Document("_id", id)
        val doc = collection.find(filtro).firstOrNull()
        doc?.let { documentToLibro(it) } ?: throw Exception("El libro no existe")
    }
    override suspend fun insert(libro: Libro): Boolean = withContext(Dispatchers.IO) {
        println("DEBUG 1: Intentando conectar con ivanv2...")
        try {
            // Forzamos un ping para ver si el servidor responde antes de insertar
            val ping = database.runCommand(Document("ping", 1))
            println("DEBUG 2: ¡Conexión establecida! Respuesta: $ping")

            val doc = Document("_id", libro._id)
                .append("titulo", libro.titulo)
                .append("autor", libro.autor)
                .append("genero", libro.genero)
                .append("anioPublicacion", libro.anioPublicacion)
                .append("editorial", libro.editorial)
                .append("paginas", libro.paginas)
                .append("disponible", libro.disponible)

            println("DEBUG 3: Enviando documento...")
            val result = collection.insertOne(doc)
            println("DEBUG 4: ¡Insertado con éxito!")
            result.wasAcknowledged()
        } catch (e: Exception) {
            println("DEBUG ERROR FINAL: ${e.message}")
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