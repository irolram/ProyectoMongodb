package com.example.proyectomongodb.viewUi.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectomongodb.model.Libro
import com.example.proyectomongodb.repository.LibroRepositoryMongo
import com.example.proyectomongodb.service.ServiceLibro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibroViewModel : ViewModel() {

    //Variable para los libros que se hayan encontrado
    var libroEncontrado by mutableStateOf<Libro?>(null)
        private set
    //Variable para el mensaje de error
    var errorBusqueda by mutableStateOf("")
        private set
    //Variable para el libro seleccionado
    var libroSeleccionado by mutableStateOf<Libro?>(null)
        private set

    //Instancias de los repositorios y servicios
    private val apiService = RetrofitObject.api
    private val libroRepository = LibroRepositoryMongo(apiService)
    private val libroService = ServiceLibro(libroRepository)

    // Estado para la lista de libros
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    // Init para cargar los libros al inicio de ejecutar la aplicación
    init {
        cargarLibros()
    }

    /*
    Función para cargar los libros desde el repositorio actualizando el estado y contenido
     */
    fun cargarLibros() {
        //Arranca la corrutina de IO
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Llama a la función suspendida en el repositorio para obtener la lista
                val lista = libroService.getall()
                //Vuelve a la corrutina principal
                withContext(Dispatchers.Main) {
                    _libros.value = lista
                }
            } catch (e: Exception) {
                println("ERROR AL CARGAR: ${e.message}")
            }
        }
    }

    /*
    Función para insertar un libro en la base de datos @Param titulo, autor, genero, anio, editorial, paginas
     */
    fun insertarLibro(
        titulo: String,
        autor: String,
        genero: String,
        anio: Int,
        editorial: String,
        paginas: Int,
        onSuccess: () -> Unit
    ) {
        //Arranca la corrutina de IO
        viewModelScope.launch(Dispatchers.IO) {
            //Creamos un nuevo libro para insertarlo
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
            // Variable para saber si se ha insertado correctamente
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

    // Función para actualizar un libro existente
    fun actualizarLibro(libro: Libro, onSuccess: () -> Unit) {

        //Arranca la corrutina de IO
        viewModelScope.launch(Dispatchers.IO) {

            // Variable para saber si se ha actualizado correctamente
            val resultado = libroService.update(libro.Id, libro)

            if (resultado) {
                cargarLibros()
                launch(Dispatchers.Main) {
                    onSuccess()
                }
            }else{
                println("DEBUG: Error al insertar el libro en el servidor")
            }
        }
    }

    fun seleccionarLibro(libro: Libro) {
        libroSeleccionado = libro
    }

    fun limpiarBusqueda() {
        libroEncontrado = null
        errorBusqueda = ""
    }

    //Función para buscar un libro por su ID
    fun buscarLibroPorId(id: Int) {
        //Arranca la corrutina de IO
        viewModelScope.launch(Dispatchers.IO) {
            try {
                limpiarBusqueda()
                // Llama a la función suspendida en el repositorio para obtener el libro
                val libro = libroService.getById(id)

                // Si el libro es diferente d nulo, lo asignamos a la variable,sino mostramos un mensaje de error
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

    //Función para eliminar un libro por su ID
    fun eliminarLibro(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Arranca la corrutina de IO
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Llama a la función suspendida en el repositorio para eliminar el libro
                val resultado = libroService.delete(id)

                // Vuelve a la corrutina Main
                withContext(Dispatchers.Main) {
                    if (resultado) {
                        cargarLibros()
                        onSuccess()
                    } else {
                        onError("No se pudo eliminar el libro (¿Seguro que el ID $id existe?)")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error de conexión: ${e.message}")
                }
            }
        }
    }
}



