package com.example.proyectomongodb.model

// Sealed class con las pantallas de la aplicación
sealed class Pantalla(val route: String) {
    object Main : Pantalla("main")
    object GetAll : Pantalla("get_all")
    object GetById : Pantalla("get_by_id")
    object Update : Pantalla("update")
    object Delete : Pantalla("delete")
    object Insert : Pantalla("insert")
}