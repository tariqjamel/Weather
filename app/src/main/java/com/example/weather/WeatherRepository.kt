package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDao: WeatherDao, private val weatherApi: WeatherApi) {
    val weather: LiveData<List<Weather>> = weatherDao.getWeather()

    suspend fun refreshWeather(city: String, apiKey: String) {
        Log.d("WeatherRepository", "Refreshing weather for city: $city")
        try {
            withContext(Dispatchers.IO) {
                val response = weatherApi.getWeather(city,"metric" ,API_KEY)
                val mainInfo = response.main
                val weatherInfo = response.weather.firstOrNull()
                val sysInfo = response.sys
                val windInfo = response.wind

                val weather = Weather(
                    location = response.location,
                    temperature = mainInfo.temperature,
                    minTemperature = mainInfo.minTemperature,
                    maxTemperature = mainInfo.maxTemperature,
                    pressure = mainInfo.pressure,
                    humidity = mainInfo.humidity,
                    feel_like = mainInfo.maxTemperature,
                    main = weatherInfo?.main ?: "",
                    visibilty = response.visibility,
                    windSpeed = windInfo.speed,
                    description = weatherInfo?.description ?: "",
                    updatedAt = response.updatedAt
                )

                val existingWeather = weatherDao.getWeatherByLocation(city)
                if (existingWeather != null) {
                    weather.id = existingWeather.id
                    weatherDao.update(weather)
                } else {
                    weatherDao.insert(weather)
                }
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error refreshing weather: ${e.message}")
        }
    }

    suspend fun updateWeather(weather: Weather) {
        weatherDao.update(weather)
    }

    suspend fun deleteWeather(weather: Weather) {
        weatherDao.delete(weather)
    }

    suspend fun getForecast(city: String, apiKey: String): List<Forecast> {
        Log.d("WeatherRepository", "Refreshing forecast for city: $city")
        try {
            val response = weatherApi.getForecast(city,apiKey)
            return response.list
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error refreshing forecast: ${e.message}")
            return emptyList()
        }
    }

}
