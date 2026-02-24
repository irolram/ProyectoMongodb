import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.proyectomongodb.viewUi.pantallas.LibroCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectomongodb.viewUi.viewModel.LibroViewModel

//Pantalla para obtener un libro por su ID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObtenerLibroPorIdScreen(
    viewModel: LibroViewModel,
    onBackClick: () -> Unit
) {
    // Estados para el ID del libro, el mensaje de error y el libro encontrado
    var idTexto by remember { mutableStateOf("") }
    val mensajeError = viewModel.errorBusqueda

    val libro = viewModel.libroEncontrado

    // Limpiar el campo de búsqueda al volver a la pantalla
    LaunchedEffect(Unit) {
        viewModel.limpiarBusqueda()
    }
    //Estructura de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar por ID") },
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = idTexto,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) { // Solo permitir números
                        idTexto = it
                    }
                },
                label = { Text("Introduce ID del libro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (idTexto.isNotEmpty()) {
                        IconButton(onClick = { idTexto = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Borrar")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para buscar el libro
            Button(
                onClick = {
                    val id = idTexto.toIntOrNull()
                    if (id != null) {
                        viewModel.buscarLibroPorId(id)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = idTexto.isNotEmpty()
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Buscar Libro")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (mensajeError.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensajeError,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (libro != null) {
                Text("Resultado encontrado:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))


                LibroCard(libro = libro, onClick = {})
            }
        }
    }
}