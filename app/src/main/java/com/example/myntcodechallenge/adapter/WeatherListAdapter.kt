package com.example.myntcodechallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myntcodechallenge.CurrentWeatherFragment
import com.example.myntcodechallenge.PrevWeathersFragment
import com.example.myntcodechallenge.R
import com.example.myntcodechallenge.model.Weather
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeatherListAdapter(private val context: Context, private val weatherList: List<Weather>): RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.weather_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.loadingSpinner.visibility = View.INVISIBLE
        holder.cardMainLayout.visibility = View.VISIBLE
        holder.locationTv.text = "${weather.name} ${weather.sys?.country}"
        holder.weatherIconIv.setImageResource(R.drawable.sun)
        holder.tempTv.text = "${weather.main?.temp?.toInt()}°C"
        holder.sunriseTimeTv.text = weather.sys?.sunrise?.let {
            dateFormatConverter(
                it.toLong()
            )
        }
        holder.sunsetTimeTv.text = weather.sys?.sunset?.let {
            dateFormatConverter(
                it.toLong()
            )
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var loadingSpinner = itemView.findViewById<ProgressBar>(R.id.progress_loader)
        var cardMainLayout = itemView.findViewById<ConstraintLayout>(R.id.card_main_layout)
        var locationTv = itemView.findViewById<TextView>(R.id.location_tv)
        var weatherIconIv = itemView.findViewById<ImageView>(R.id.weather_iv)
        var tempTv = itemView.findViewById<TextView>(R.id.temp_tv)
        var sunriseTimeTv = itemView.findViewById<TextView>(R.id.sunrise_time_tv)
        var sunsetTimeTv = itemView.findViewById<TextView>(R.id.sunset_time_tv)
    }

    private fun dateFormatConverter(date: Long): String {
        return SimpleDateFormat(
            "hh:mm a",
            Locale.ENGLISH
        ).format(Date(date * 1000))
    }
}