package rajeshkadiri.openweather.model

import android.util.Log
import androidx.lifecycle.LiveData
import rajeshkadiri.openweather.network.NetworkRepository
import rajeshkadiri.openweather.network.Response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class OpenWeatherDataProcessor(val repositoryOpen: OpenWeatherDataRepository = OpenWeatherDataRepository(), val networkRepository: NetworkRepository = NetworkRepository()) {

    interface OnWeatherDataListener {
        fun onSuccess(openWeatherData: OpenWeatherData)
    }

    fun saveWeatherData(tc: OpenWeatherData) {
        repositoryOpen.saveWeatherData(tc)
    }

    fun clearWeatherData() {
        repositoryOpen.clearWeatherData()
    }

    fun mergeLocalDataList(dataListOpen: List<OpenWeatherData>) {
        repositoryOpen.mergeLocalDataList(dataListOpen)
    }

    fun loadWeatherDataByDt(createDate: Date): OpenWeatherData? {
        return repositoryOpen.loadWeatherDataByDt(createDate)
    }

    fun loadWeatherData(): LiveData<List<OpenWeatherData>> {
        return repositoryOpen.loadWeatherData()
    }

    fun getWeatherData(onWeatherDataListener: OnWeatherDataListener) {
        val call = networkRepository.weatherService.getWeather(networkRepository.id, networkRepository.appId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    onWeatherDataListener.onSuccess(OpenWeatherData(weatherItem?.name!!,
                            weatherItem.sys?.country!!,
                            weatherItem.weather?.get(0)?.description!!,
                            weatherItem.main?.tempMin!!,
                            weatherItem.main?.tempMax!!,
                            weatherItem.wind?.speed!!))
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }
}