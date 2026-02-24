package com.example.proyectomongodb.viewUi.pantallas


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel

//Pantalla para eliminar un libro
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EliminarLibroScreen(
    viewModel: LibroViewModel,
    onBackClick: () -> Unit
) {
    // Estados para el ID del libro y el mensaje de error
    var idTexto by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    //Variable para el contexto de la aplicación
    val context = LocalContext.current

    //Estructura de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eliminar Libro") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Introduce el ID del libro que deseas eliminar de la base de datos de Atlas.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = idTexto,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        idTexto = it
                        mensajeError = ""
                    }
                },
                label = { Text("ID del Libro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val id = idTexto.toIntOrNull()
                    if (id != null) {
                        viewModel.eliminarLibro(
                            id = id,
                            onSuccess = {
                                Toast.makeText(context, "Libro $id eliminado correctamente", Toast.LENGTH_SHORT).show()
                                idTexto = ""
                            },
                            onError = { error ->
                                mensajeError = error
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = idTexto.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ELIMINAR DEFINITIVAMENTE")
            }
        }
    }
}