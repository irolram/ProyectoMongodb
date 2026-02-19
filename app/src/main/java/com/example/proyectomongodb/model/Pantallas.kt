package com.example.proyectomongodb.model

sealed class Pantalla(val route: String) {
    object Main : Pantalla("main")
    object GetAll : Pantalla("get_all")
    object GetById : Pantalla("get_by_id")
    object Update : Pantalla("update")
    object Delete : Pantalla("delete")
    object Insert : Pantalla("insert")
}