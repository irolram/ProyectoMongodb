import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    val api: ApiBibliotecaService by lazy { // Usamos la interfaz de Retrofit
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiBibliotecaService::class.java)
    }
}