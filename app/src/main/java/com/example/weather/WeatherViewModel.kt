package com.example.weather

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository
    val weather: LiveData<List<Weather>>
    val progressBarVisibility: MutableLiveData<Int> = MutableLiveData()

    init {
        val weatherDao = WeatherDatabase.getInstance(application).weatherDao()
        repository = WeatherRepository(weatherDao, RetrofitClient.create())
        weather = repository.weather
    }

    fun refreshWeather(location: String, apiKey: String) {
        viewModelScope.launch {
            repository.refreshWeather(location, apiKey)
            progressBarVisibility.value = View.GONE
        }
    }

    fun updateWeather(apiKey: String) {
        viewModelScope.launch {
            val weatherList = repository.weather.value
            if (weatherList != null) {
                for (weather in weatherList) {
                    repository.refreshWeather(weather.location, apiKey)
                }
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                updateWeather(apiKey)
            }
        }, 5 * 60 * 1000)
    }

    fun deleteWeather(weather: Weather) {
        viewModelScope.launch {
            repository.deleteWeather(weather)
        }
    }

    fun getForecast(location: String): LiveData<List<Forecast>> {
        return liveData {
            val forecast = repository.getForecast(location, API_KEY)
            emit(forecast)
        }
    }

}
