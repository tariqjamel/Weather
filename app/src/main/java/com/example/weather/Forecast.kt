package com.example.weather

data class Forecast(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double
)

data class ForecastResponse(val list: List<Forecast>)

//data class Forecast(val date: Long, val temperature: Double, val description: String)
