package com.example.proyectomongodb.viewUi.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.model.Pantalla
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObtenerTodosLosLibrosScreen(
    viewModel: LibroViewModel,
    onBackClick: () -> Unit,
    onNavigate: (String) -> Unit // Añadimos esto para poder saltar a "Update"
) {
    // Usamos el nombre correcto de la variable que definiste arriba
    val listaLibros by viewModel.libros.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (listaLibros.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No hay libros disponibles")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cambiado 'libros' por 'listaLibros'
                items(listaLibros) { libro ->
                    Box(modifier = Modifier.clickable {
                        // 1. Guardamos el libro en el ViewModel
                        viewModel.seleccionarLibro(libro)
                        // 2. Navegamos usando la función que viene por parámetro
                        onNavigate(Pantalla.Update.route)
                    }) {
                        // Usamos tu diseño de tarjeta que ya es muy bonito
                        LibroCard(libro = libro)
                    }
                }
            }
        }
    }
}

@Composable
fun LibroCard(libro: Libro,onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = libro.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (libro.disponible) Icons.Default.CheckCircle else Icons.Default.Clear,
                    contentDescription = if (libro.disponible) "Disponible" else "Agotado",
                    tint = if (libro.disponible) Color(0xFF4CAF50) else Color(0xFFE57373)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Género: ${libro.genero}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetalleTexto("Año", libro.anioPublicacion.toString())
                DetalleTexto("Editorial", libro.editorial)
                DetalleTexto("Págs", libro.paginas.toString())
            }
        }
    }
}

@Composable
fun DetalleTexto(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}