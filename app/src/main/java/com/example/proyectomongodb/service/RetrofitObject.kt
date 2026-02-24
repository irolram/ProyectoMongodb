import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    // URL del servidor
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // Instancia de Retrofit
    val api: ApiBibliotecaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiBibliotecaService::class.java)
    }
}