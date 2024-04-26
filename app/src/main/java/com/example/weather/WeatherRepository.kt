package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
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

                weatherDao.insert(weather)
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
}
