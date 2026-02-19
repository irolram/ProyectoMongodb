package com.example.proyectomongodb.repository

import com.example.proyectomongodb.model.Libro
import com.mongodb.client.MongoClients
import org.bson.Document


class LibroDaoMongo: ILibroDaoMongo {

    private val connectionString = "mongodb+srv://ivanv2:Wma6jHHOGCFqBSca@cluster0.q2o8nms.mongodb.net/?appName=Cluster0"
    private val client = MongoClients.create(connectionString)
    private val database = client.getDatabase("biblioteca_db")
    private val collection = database.getCollection("libros")

    val listaLibros = mutableListOf<Libro>()

    override suspend fun getall(): List<Libro> {
        collection.find().forEach { document ->
            listaLibros.add(documentToLibro(document))
        }
        return listaLibros

    }

    override suspend fun getById(id: Int): Libro {
        val filtro = Document("Id", id)
        val doc = collection.find(filtro).firstOrNull()

         if (doc != null) {
            println("Libro con Id $id encontrado en la base de datos")
             documentToLibro(doc)
        } else {
            println("Libro con Id $id no existe")
        }
        return listaLibros[0]
    }

    override suspend fun insert(libro: Libro):Boolean {

        val doc = Document("Id", libro.id)
            .append("titulo", libro.titulo)
            .append("autor", libro.autor)
            .append("genero", libro.genero)
            .append("anioPublicacion", libro.anioPublicacion)
            .append("editorial", libro.editorial)
            .append("numeroPaginas", libro.paginas)
            .append("disponible", libro.disponible)

        return collection.insertOne(doc).wasAcknowledged()

    }

    override suspend fun update(libro: Libro) : Boolean{

        val filtro = Document("Id", libro.id)

        val nuevoDocumento = Document("Id", libro.id)
            .append("titulo", libro.titulo)
            .append("autor", libro.autor)
            .append("genero", libro.genero)
            .append("anioPublicacion", libro.anioPublicacion)
            .append("editorial", libro.editorial)
            .append("numeroPaginas", libro.paginas)
            .append("disponible", libro.disponible)

        val result = collection.replaceOne(filtro, nuevoDocumento)

        if (result.modifiedCount > 0 ){
            println("Libro con Id ${libro.id} actualizado correctamente")
            return true
        }else{
            println("No se ha podido actualizar el libro con Id ${libro.id}")
            return false

        }
    }

    override suspend fun delete(id: Int):Boolean {

        val LibroBorrado = collection.deleteOne(Document("Id", id)).deletedCount

        if (LibroBorrado > 0 ){
            println("Libro con Id $id borrado correctamente")
            return true
        }else{
            println("No se ha podido borrar el libro con Id $id")
            return false
        }

    }

    private fun documentToLibro(doc: Document): Libro {
        return Libro(
            id = doc.getInteger("Id"),
            titulo = doc.getString("titulo"),
            autor = doc.getString("autor"),
            genero = doc.getString("genero"),
            anioPublicacion = doc.getInteger("anioPublicacion"),
            editorial = doc.getString("editorial"),
            paginas = doc.getInteger("paginas"),
            disponible = doc.getBoolean("disponible")
        )
    }
}