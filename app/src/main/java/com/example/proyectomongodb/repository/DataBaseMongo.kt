package com.example.proyectomongodb.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients

object MongoDBConnection {
    private const val CONNECTION_STRING = "<YOUR_CONNECTION_STRING>"

    fun getMongoClient(): MongoClient {
        return MongoClients.create(CONNECTION_STRING)
    }
}