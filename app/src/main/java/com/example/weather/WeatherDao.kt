package com.example.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getWeather(): LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Update
    suspend fun update(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)
}