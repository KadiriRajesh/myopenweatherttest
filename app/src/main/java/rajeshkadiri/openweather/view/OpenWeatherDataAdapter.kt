package rajeshkadiri.openweather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import rajeshkadiri.openweather.R
import rajeshkadiri.openweather.databinding.SavedWeatherDataListItemBinding
import rajeshkadiri.openweather.viewmodel.OpenWeatherDataSummaryItem


class OpenWeatherDataAdapter(val onItemSelected: (itemOpen: OpenWeatherDataSummaryItem) -> Unit)
    : RecyclerView.Adapter<OpenWeatherDataAdapter.WeatherDataViewHolder>() {

    private val weatherDataSummaries = mutableListOf<OpenWeatherDataSummaryItem>()

    fun updateList(updates: List<OpenWeatherDataSummaryItem>) {
        weatherDataSummaries.clear()
        weatherDataSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SavedWeatherDataListItemBinding>(
                inflater, R.layout.saved_weather_data_list_item, parent, false)

        return WeatherDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherDataSummaries.size
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        holder.bind(weatherDataSummaries[position])
    }

    inner class WeatherDataViewHolder(val binding: SavedWeatherDataListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemOpen: OpenWeatherDataSummaryItem) {
            binding.item = itemOpen
            binding.root.setOnClickListener { onItemSelected(itemOpen) }
            binding.executePendingBindings()
        }

    }

}