package com.example.proyectomongodb.viewUi.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel


//Pantalla para actualizar un libro
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarLibroScreen(
    libro: Libro,
    viewModel: LibroViewModel,
    onBackClick: () -> Unit
) {
    // Variable para el contexto de la aplicación
    val context = LocalContext.current

    // Estados para los campos del formulario
    var titulo by remember { mutableStateOf(libro.titulo) }
    var autor by remember { mutableStateOf(libro.autor) }
    var genero by remember { mutableStateOf(libro.genero) }
    var anio by remember { mutableStateOf(libro.anioPublicacion.toString()) }
    var editorial by remember { mutableStateOf(libro.editorial) }
    var paginas by remember { mutableStateOf(libro.paginas.toString()) }
    var disponible by remember { mutableStateOf(libro.disponible) }

    //Estructura de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Libro #${libro.Id}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = genero, onValueChange = { genero = it }, label = { Text("Género") }, modifier = Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = anio,
                    onValueChange = { anio = it },
                    label = { Text("Año") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = paginas,
                    onValueChange = { paginas = it },
                    label = { Text("Páginas") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = disponible, onCheckedChange = { disponible = it })
                Text("¿Está disponible?")
            }

            //Botón para guardar los cambios
            Button(
                onClick = {
                    val libroEditado = libro.copy(
                        titulo = titulo,
                        autor = autor,
                        genero = genero,
                        anioPublicacion = anio.toIntOrNull() ?: 0,
                        editorial = editorial,
                        paginas = paginas.toIntOrNull() ?: 0,
                        disponible = disponible
                    )

                    viewModel.actualizarLibro(libroEditado) {
                        Toast.makeText(context, "Libro actualizado", Toast.LENGTH_SHORT).show()
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}