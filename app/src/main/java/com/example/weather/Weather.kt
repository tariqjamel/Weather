package com.example.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val location: String,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Double,
    val humidity: Double,
    val feel_like: Double,
    val visibilty: Double,
    val windSpeed: Double,
    val description: String,
    val main: String,
    val updatedAt: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(location)
        parcel.writeDouble(temperature)
        parcel.writeDouble(minTemperature)
        parcel.writeDouble(maxTemperature)
        parcel.writeDouble(pressure)
        parcel.writeDouble(humidity)
        parcel.writeDouble(feel_like)
        parcel.writeDouble(visibilty)
        parcel.writeDouble(windSpeed)
        parcel.writeString(description)
        parcel.writeString(main)
        parcel.writeLong(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}

