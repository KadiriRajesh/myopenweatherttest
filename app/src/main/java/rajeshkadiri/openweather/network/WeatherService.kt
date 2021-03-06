package rajeshkadiri.openweather.network


import rajeshkadiri.openweather.network.Response.WeatherResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("id") id: String, @Query("appid") appId: String): Call<WeatherResponse>
}