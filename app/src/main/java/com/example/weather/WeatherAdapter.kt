package com.example.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val onItemClick: (Weather) -> Unit, private val viewModel: WeatherViewModel) :
    ListAdapter<Weather, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    private val cityLocations = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
        holder.itemView.setOnClickListener { onItemClick(weather) }
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)

        fun bind(weather: Weather) {
            locationTextView.text = weather.location
            temperatureTextView.text = String.format("%.0f°C", weather.temperature)
            cityLocations.add(weather.location) // store the city location
            Log.d("WeatherAdapter", "Location: ${weather.location}")
        }
    }

    class WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }
    }


    fun getCities(): List<String> {
        Log.d("Weather", "Location: ${cityLocations}")
        return cityLocations
    }
}
