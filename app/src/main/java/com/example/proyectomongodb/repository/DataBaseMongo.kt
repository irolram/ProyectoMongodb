package com.example.proyectomongodb.repository


import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import org.bson.Document
import kotlinx.coroutines.runBlocking


object MongoClientConnectionExample {

    fun main() {
        // Replace the placeholders with your credentials and hostname
        val connectionString = "mongodb+srv://ivanv2:Wma6jHHOGCFqBSca@cluster0.q2o8nms.mongodb.net/?appName=Cluster0"

        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        // Create a new client and connect to the server
        MongoClients.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
        }
    }

}
