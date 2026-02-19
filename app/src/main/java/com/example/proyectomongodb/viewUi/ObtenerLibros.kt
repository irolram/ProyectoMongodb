package com.example.proyectomongodb.ui

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
// Importa tu modelo aquí
import com.example.proyectomongodb.model.Libro

class LibroViewModel : ViewModel() {
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ObtenerTodosLosLibrosScreen(

        viewModel: LibroViewModel = viewModel(),
        onBackClick: () -> Unit = {}
    ) {
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay libros disponibles")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaLibros) { libro ->
                        LibroCard(libro)
                    }
                }
            }
        }
    }

    @Composable
    fun LibroCard(libro: Libro) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Título y Estado de disponibilidad
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

                    // Icono dinámico según disponibilidad
                    Icon(
                        imageVector = if (libro.disponible) Icons.Default.CheckCircle else Icons.Default.Clear,
                        contentDescription = if (libro.disponible) "Disponible" else "Agotado",
                        tint = if (libro.disponible) Color(0xFF4CAF50) else Color(0xFFE57373)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Autor y Género
                Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Género: ${libro.genero}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Detalles técnicos (Editorial, Año, Páginas) en una fila
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
