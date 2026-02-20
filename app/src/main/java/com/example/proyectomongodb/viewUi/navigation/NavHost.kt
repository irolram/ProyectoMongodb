package com.example.proyectomongodb.viewUi.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectomongodb.model.Pantalla
import com.example.proyectomongodb.viewUi.pantallas.InsertarLibroScreen
import com.example.proyectomongodb.viewUi.pantallas.ObtenerTodosLosLibrosScreen
import com.example.proyectomongodb.viewUi.pantallas.PantallaPrincipal
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val libroViewModel: LibroViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Pantalla.Main.route
    ) {
        composable(Pantalla.Main.route) {
            PantallaPrincipal(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Pantalla.GetAll.route) {
            ObtenerTodosLosLibrosScreen(viewModel = libroViewModel)
        }

        composable(Pantalla.Insert.route) {
            InsertarLibroScreen(
                viewModel = libroViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Pantalla.GetById.route) { /* Pendiente */ }
        composable(Pantalla.Update.route) { /* Pendiente */ }
        composable(Pantalla.Delete.route) { /* Pendiente */ }
    }
}