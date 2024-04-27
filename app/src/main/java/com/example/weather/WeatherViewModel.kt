package com.example.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository
    val weather: LiveData<List<Weather>>

    init {
        val weatherDao = WeatherDatabase.getInstance(application).weatherDao()
        repository = WeatherRepository(weatherDao, RetrofitClient.create())
        weather = repository.weather
    }

    fun refreshWeather(location: String, apiKey: String) {
        viewModelScope.launch {
            val existingWeather = repository.weather.value?.find {
                it.location == location
            }
            if (existingWeather != null) {
                repository.updateWeather(existingWeather)
            } else {
                repository.refreshWeather(location, apiKey)
            }
        }
    }

    fun updateWeather(location: String, apiKey: String) {
        viewModelScope.launch {
            repository.refreshWeather(location, apiKey)
        }
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
