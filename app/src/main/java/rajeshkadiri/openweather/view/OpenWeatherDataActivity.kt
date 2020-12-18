package rajeshkadiri.openweather.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_weather_data.*
import kotlinx.android.synthetic.main.content_weatherdata.*
import rajeshkadiri.openweather.R
import rajeshkadiri.openweather.databinding.ActivityWeatherDataBinding
import rajeshkadiri.openweather.databinding.DetailDialogBinding
import rajeshkadiri.openweather.model.OpenWeatherData
import rajeshkadiri.openweather.viewmodel.OpenWeatherDataViewModel


class OpenWeatherDataActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherDataBinding
    var textdata: String? = ""
    fun getWeatherData(view: View) {
        binding.vm?.openWeatherDataProcessor?.getWeatherData(binding.vm?.openWeatherDataListener!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_data)
        binding.vm = ViewModelProviders.of(this).get(OpenWeatherDataViewModel::class.java)
        val vm = ViewModelProviders.of(this).get(OpenWeatherDataViewModel::class.java)
        //setSupportActionBar(binding.toolbar)


        submit.setOnClickListener(View.OnClickListener {
            recyclerView.setHasFixedSize(true)
            val text = edt_city.text
            val values = text // Read List from somewhere
            val lstValues: List<String> = values.split(",").map { it -> it.trim() }

            lstValues.forEach { it ->
                Log.i("Values", "value=$it")
                textdata = it
                //Do Something
            }

            val adapter = OpenWeatherDataAdapter { }
            recyclerView.adapter = adapter

            vm.loadSavedWeatherDataSummaries().observe(this, Observer {
                if (it!!.isNotEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    textViewAdd.visibility = View.GONE
                    adapter.updateList(it)
                } else {
                    if (progressBar.visibility != View.VISIBLE)
                        textViewAdd.visibility = View.VISIBLE
                    adapter.updateList(it)
                }
            })
        })
    }

    private fun showDialog(openWeather: OpenWeatherData) {
        val dialog = Dialog(this)
        val detailDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.detail_dialog, null,
                false) as DetailDialogBinding
        detailDialogBinding.weatherData = openWeather
        dialog.setContentView(detailDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather_data_processor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                getWeatherData(binding.root)
                true
            }
            R.id.action_clear -> {
                binding.vm?.openWeatherDataProcessor?.clearWeatherData()
                binding.vm?.db?.deleteAllData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}