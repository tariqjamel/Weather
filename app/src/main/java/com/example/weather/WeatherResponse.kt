package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val visibility: Double,
    @SerializedName("name") val location: String,
    @SerializedName("main") val main: MainInfo,
    @SerializedName("weather") val weather: List<WeatherInfo>,
    @SerializedName("sys") val sys: SysInfo,
    @SerializedName("wind") val wind: WindInfo,
    @SerializedName("dt") val updatedAt: Long
)

data class MainInfo(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("temp_min") val minTemperature: Double,
    @SerializedName("temp_max") val maxTemperature: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
)

data class WeatherInfo(
    @SerializedName("description") val description: String,
    @SerializedName("main") val main: String
)

data class SysInfo(
    @SerializedName("sunrise") val feel_like: Double,
    //  @SerializedName("sunset") val visibilty: Double
)

data class WindInfo(
    @SerializedName("speed") val speed: Double
)
