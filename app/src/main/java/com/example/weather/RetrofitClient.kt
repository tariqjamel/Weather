package com.example.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val API_KEY = "6bf1d26e0c11fc57da3784996152b2be"

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
//https://api.openweathermap.org/data/2.5/weather?q=karachi&appid=6bf1d26e0c11fc57da3784996152b2be
    fun create(): WeatherApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherApi::class.java)
    }
}