import com.example.proyectomongodb.model.Libro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiBibliotecaService {

    @GET("api/libros")
    suspend fun getLibros(): List<Libro>

    @GET("api/libros/{id}")
    suspend fun getLibrosById(@Path("id") id: Int): Response<Libro>

    @POST("api/libros")
    suspend fun insertarLibro(@Body libro: Libro): Response<Libro>
    @PUT("api/libros/{id}")
    suspend fun updateLibro(@Path("id") id: Int, @Body libro: Libro): Libro

    // El Map<String, Any> es perfecto para el PATCH, así solo envías lo que cambia
    @PATCH("api/libros/{id}")
    suspend fun patchLibro(@Path("id") id: Int, @Body campos: Map<String, Any>): Libro

    @DELETE("api/libros/{id}")
    suspend fun deleteLibro(@Path("id") id: Int)
}