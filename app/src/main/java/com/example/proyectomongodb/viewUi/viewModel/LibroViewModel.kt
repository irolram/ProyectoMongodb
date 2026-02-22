package com.example.proyectomongodb.viewUi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroDaoMongo
import com.example.proyectomongodb.service.ServiceLibro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibroViewModel : ViewModel() {

    private val libroService = ServiceLibro(LibroDaoMongo())

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    init {
        cargarLibros()
    }

    private fun cargarLibros() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listaDesdeMongo = libroService.getall()
                _libros.value = listaDesdeMongo
            } catch (e: Exception) {
                println("Error al cargar los libros desde MongoDB: ${e.message}")
            }
        }
    }
    fun insertarLibro(titulo: String, autor: String, genero: String, anio: Int, editorial: String, paginas: Int, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val nuevoLibro = Libro(
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
                cargarLibros()
                launch(Dispatchers.Main) { onSuccess() }
            }
        }
    }
}