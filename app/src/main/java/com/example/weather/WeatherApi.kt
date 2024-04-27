package com.example.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): ForecastResponse
}
