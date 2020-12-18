package rajeshkadiri.openweather.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mbakgun.weatherlogger.viewmodel.ObservableViewModel
import rajeshkadiri.openweather.DBHelper
import rajeshkadiri.openweather.model.OpenWeatherData
import rajeshkadiri.openweather.model.OpenWeatherDataProcessor


class OpenWeatherDataViewModel @JvmOverloads constructor(app: Application,
                                                         val openWeatherDataProcessor: OpenWeatherDataProcessor = OpenWeatherDataProcessor()) : ObservableViewModel(app),
        OpenWeatherDataProcessor.OnWeatherDataListener {
    var lastWeatherData = OpenWeatherData()
    var openWeatherDataListener: OpenWeatherDataProcessor.OnWeatherDataListener? = null
    val db by lazy { DBHelper(app) }

    init {
        openWeatherDataListener = this
        openWeatherDataProcessor.getWeatherData(this)
    }

    override fun onSuccess(openWeatherData: OpenWeatherData) {
        openWeatherDataProcessor.saveWeatherData(openWeatherData)
        updateOutputs(openWeatherData)
        db.insertData(openWeatherData)
    }

    fun updateOutputs(wd: OpenWeatherData) {
        lastWeatherData = wd
        notifyChange()
    }

    fun loadSavedWeatherDataSummaries(): LiveData<List<OpenWeatherDataSummaryItem>> {
        openWeatherDataProcessor.mergeLocalDataList(db.readData())
        return Transformations.map(openWeatherDataProcessor.loadWeatherData()) { weatherDataObjects ->
            weatherDataObjects.map {
                OpenWeatherDataSummaryItem(it.mintemp,it.maxtemp,it.description, it.windspeed)
            }
        }
    }


}