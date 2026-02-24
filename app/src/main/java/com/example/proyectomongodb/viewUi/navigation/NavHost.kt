package com.example.proyectomongodb.viewUi.navigation

import ObtenerLibroPorIdScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectomongodb.model.Pantalla
import com.example.proyectomongodb.viewUi.pantallas.ActualizarLibroScreen
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
        // 1. Pantalla Principal (Menú)
        composable(Pantalla.Main.route) {
            PantallaPrincipal(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Pantalla.GetAll.route) {
            ObtenerTodosLosLibrosScreen(
                viewModel = libroViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigate = { ruta -> navController.navigate(ruta) } )
        }

        // 3. Insertar nuevo libro
        composable(Pantalla.Insert.route) {
            InsertarLibroScreen(
                viewModel = libroViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Pantalla.Update.route) {
            val libro = libroViewModel.libroSeleccionado
            if (libro != null) {
                ActualizarLibroScreen(
                    libro = libro,
                    viewModel = libroViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }

        composable(Pantalla.GetById.route) {
            ObtenerLibroPorIdScreen(
                viewModel = libroViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Pantalla.Delete.route) { /* Pendiente */ }
    }
}