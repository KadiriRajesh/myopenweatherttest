package rajeshkadiri.openweather.network


import androidx.databinding.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkRepository {
    private val baseUrl = "https://api.openweathermap.org"
   // var penWeatherDataActivity = OpenWeatherDataActivity()
    val id = "745044"
    val appId = "8a8edeb83e8f1382d6cd7567c2337a"
    private val client = OkHttpClient().newBuilder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val weatherService = retrofit.create(WeatherService::class.java)
}
