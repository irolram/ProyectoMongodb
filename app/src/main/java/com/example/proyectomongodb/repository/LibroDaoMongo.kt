package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro
import com.mongodb.client.MongoClients
import org.bson.Document
import org.bson.types.ObjectId

class LibroDaoMongo : ILibroDaoMongo {

    private val connectionString = "mongodb+srv://ivanv2:Wma6jHHOGCFqBSca@cluster0.q2o8nms.mongodb.net/?appName=Cluster0"
    private val client = MongoClients.create(connectionString)
    private val database = client.getDatabase("biblioteca_db")
    private val collection = database.getCollection("libros")

    override suspend fun getall(): List<Libro> {
        val listaLibros = mutableListOf<Libro>()
        collection.find().forEach { document ->
            listaLibros.add(documentToLibro(document))
        }
        return listaLibros
    }

    // Ahora recibe un ObjectId por parámetro
    override suspend fun getById(id: ObjectId): Libro {
        // En Mongo la clave primaria SIEMPRE se llama "_id"
        val filtro = Document("_id", id)
        val doc = collection.find(filtro).firstOrNull()

        if (doc != null) {
            println("Libro encontrado en la base de datos")
            return documentToLibro(doc)
        } else {
            throw Exception("El libro no existe en la base de datos")
        }
    }

    override suspend fun insert(libro: Libro): Boolean {
        try {
            // Ya no hay que buscar el ID máximo. ¡El código es directo!
            val doc = Document("_id", libro.id)
                .append("titulo", libro.titulo)
                .append("autor", libro.autor)
                .append("genero", libro.genero)
                .append("anioPublicacion", libro.anioPublicacion)
                .append("editorial", libro.editorial)
                .append("paginas", libro.paginas)
                .append("disponible", libro.disponible)

            return collection.insertOne(doc).wasAcknowledged()
        } catch (e: Exception) {
            println("Error al insertar: ${e.message}")
            return false
        }
    }

    override suspend fun update(libro: Libro): Boolean {
        val filtro = Document("_id", libro.id)

        val nuevoDocumento = Document("_id", libro.id)
            .append("titulo", libro.titulo)
            .append("autor", libro.autor)
            .append("genero", libro.genero)
            .append("anioPublicacion", libro.anioPublicacion)
            .append("editorial", libro.editorial)
            .append("paginas", libro.paginas)
            .append("disponible", libro.disponible)

        val result = collection.replaceOne(filtro, nuevoDocumento)

        return if (result.modifiedCount > 0) {
            println("Libro actualizado correctamente")
            true
        } else {
            println("No se ha podido actualizar el libro")
            false
        }
    }

    // Ahora recibe un ObjectId
    override suspend fun delete(id: ObjectId): Boolean {
        val libroBorrado = collection.deleteOne(Document("_id", id)).deletedCount

        return if (libroBorrado > 0) {
            println("Libro borrado correctamente")
            true
        } else {
            println("No se ha podido borrar el libro")
            false
        }
    }

    private fun documentToLibro(doc: Document): Libro {
        return Libro(
            // Usamos la función nativa getObjectId y leemos "_id"
            id = doc.getObjectId("_id") ?: ObjectId(),
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