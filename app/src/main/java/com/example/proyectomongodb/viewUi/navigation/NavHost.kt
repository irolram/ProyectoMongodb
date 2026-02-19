package com.example.proyectomongodb.viewUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectomongodb.model.Pantalla
import com.example.proyectomongodb.ui.ObtenerTodosLosLibrosScreen
import com.example.proyectomongodb.viewUi.PantallaPrincipal

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Pantalla.Main.route
    ) {
        composable(Pantalla.Main.route) {
            PantallaPrincipal(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // Definición de las demás pantallas de destino
        composable(Pantalla.GetAll.route) { ObtenerTodosLosLibrosScreen() }
        composable(Pantalla.GetById.route) { /* Composable de Obtener ID */ }
        composable(Pantalla.Update.route) { /* Composable de Actualizar */ }
        composable(Pantalla.Delete.route) { /* Composable de Eliminar */ }
        composable(Pantalla.Insert.route) { /* Composable de Insertar */ }
    }
}