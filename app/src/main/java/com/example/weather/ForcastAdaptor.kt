package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ForecastAdapter(private var forecastList: List<Forecast>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    init {
        forecastList = forecastList.take(10)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.apply {
            val date = Date(System.currentTimeMillis() + (position * 86400000))
            dateTextView.text = SimpleDateFormat("MM-dd", Locale.getDefault()).format(date)

            temperatureTextView.text = String.format("%.0f°C", forecast.main.temp - 273)

            if (forecast.weather.isNotEmpty()) {
                descriptionTextView.text = forecast.weather[0].main
            } else {
                descriptionTextView.text = "N/A"
            }
        }
    }

    override fun getItemCount(): Int = forecastList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val temperatureTextView:    TextView = view.findViewById(R.id.temperature_text_view)
        val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
    }

    fun updateList(newList: List<Forecast>) {
        val trimmedList = newList.take(10)
        forecastList = trimmedList
        notifyDataSetChanged()
    }
}
