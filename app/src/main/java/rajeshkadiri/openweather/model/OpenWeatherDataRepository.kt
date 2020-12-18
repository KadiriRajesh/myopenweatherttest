package rajeshkadiri.openweather.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class OpenWeatherDataRepository {

    private val savedWeatherData = mutableMapOf<Date, OpenWeatherData>()
    val liveData = MutableLiveData<List<OpenWeatherData>>()

    fun saveWeatherData(tc: OpenWeatherData) {
        savedWeatherData[tc.dateCreated] = tc
        liveData.value = savedWeatherData.values.toList()
    }

    fun clearWeatherData() {
        savedWeatherData.clear()
        liveData.value = savedWeatherData.values.toList()
    }

    fun loadWeatherDataByDt(createDate: Date): OpenWeatherData? {
        return savedWeatherData[createDate]
    }

    fun loadWeatherData(): LiveData<List<OpenWeatherData>> {
        liveData.value = savedWeatherData.values.toList()
        return liveData
    }

    fun mergeLocalDataList(dataListOpen: List<OpenWeatherData>) {
        dataListOpen.forEach { weatherData ->
            savedWeatherData[weatherData.dateCreated] = weatherData
        }
        liveData.value = savedWeatherData.values.toList()
    }
}