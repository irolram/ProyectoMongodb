package com.example.proyectomongodb.viewUi.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertarLibroScreen(
    viewModel: LibroViewModel,
    onBackClick: () -> Unit = {}
) {
    //Variable para el contexto de la aplicación
    val context = LocalContext.current

    // Estados para los campos del formulario
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var editorial by remember { mutableStateOf("") }
    var paginas by remember { mutableStateOf("") }

    // Estructura de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Libro") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text("Género") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = editorial,
                onValueChange = { editorial = it },
                label = { Text("Editorial") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = anio,
                    onValueChange = { anio = it },
                    label = { Text("Año") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = paginas,
                    onValueChange = { paginas = it },
                    label = { Text("Páginas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val anioInt = anio.toIntOrNull() ?: 0
                    val paginasInt = paginas.toIntOrNull() ?: 0

                    if (titulo.isNotBlank() && autor.isNotBlank()) {

                        viewModel.insertarLibro(
                            titulo = titulo,
                            autor = autor,
                            genero = genero,
                            anio = anioInt,
                            editorial = editorial,
                            paginas = paginasInt,
                            onSuccess = {

                                Toast.makeText(context, "Libro guardado con éxito", Toast.LENGTH_SHORT).show()
                                onBackClick()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Título y Autor son obligatorios", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Libro")
            }
        }
    }
}