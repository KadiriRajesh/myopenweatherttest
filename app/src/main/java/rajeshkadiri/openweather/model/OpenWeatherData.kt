package rajeshkadiri.openweather.model

import java.util.*

data class OpenWeatherData(val name: String = "",
                           val country: String = "",
                           val description: String = "",
                           val mintemp: Double = 0.0,
                           val maxtemp: Double = 0.0,
                           val windspeed: Double = 0.0,
                           val dt: String = "",
                           val dateCreated: Date = Date())