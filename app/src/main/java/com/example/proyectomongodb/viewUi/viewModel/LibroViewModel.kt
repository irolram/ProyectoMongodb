package com.example.proyectomongodb.viewUi.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroDaoMongo
import com.example.proyectomongodb.service.ServiceLibro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibroViewModel : ViewModel() {

    var libroSeleccionado by mutableStateOf<Libro?>(null)
        private set
    private val apiService = RetrofitObject.api
    private val libroDao = LibroDaoMongo(apiService)
    private val libroService = ServiceLibro(libroDao)

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    init {
        cargarLibros()
    }

    fun cargarLibros() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lista = libroService.getall()
                withContext(Dispatchers.Main) {
                    _libros.value = lista
                }
            } catch (e: Exception) {
                println("ERROR AL CARGAR: ${e.message}")
            }
        }
    }

    fun insertarLibro(
        titulo: String,
        autor: String,
        genero: String,
        anio: Int,
        editorial: String,
        paginas: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val nuevoLibro = Libro(
                // No pasamos ID porque el backend lo genera con el contador
                titulo = titulo,
                autor = autor,
                genero = genero,
                anioPublicacion = anio,
                editorial = editorial,
                paginas = paginas,
                disponible = true
            )

            val resultado = libroService.insert(nuevoLibro)

            if (resultado) {
                delay(500)
                cargarLibros()
                launch(Dispatchers.Main) { onSuccess() }
            } else {
                println("DEBUG: Error al insertar el libro en el servidor")
            }
        }
    }

    fun actualizarLibro(libro: Libro, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = libroService.update(libro.Id, libro)

            if (resultado) {
                cargarLibros()
                launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }    fun seleccionarLibro(libro: Libro) {
        libroSeleccionado = libro
    }

var libroEncontrado by mutableStateOf<Libro?>(null)
    private set

var errorBusqueda by mutableStateOf("")
    private set

fun limpiarBusqueda() {
    libroEncontrado = null
    errorBusqueda = ""
}
    // En tu LibroViewModel.kt
    fun buscarLibroPorId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                errorBusqueda = ""
                libroEncontrado = null

                val libro = libroService.getById(id)

                if (libro != null) {
                    libroEncontrado = libro
                } else {
                    errorBusqueda = "No se encontró ningún libro con el ID $id"
                }
            } catch (e: Exception) {
                errorBusqueda = "Error de conexión: ${e.message}"
            }
        }
    }
}



