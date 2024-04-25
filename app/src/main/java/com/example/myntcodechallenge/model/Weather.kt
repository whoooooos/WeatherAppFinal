package com.example.myntcodechallenge.model

data class Weather(
    val main: Main? = null,
    val name: String,
    val sys: Sys? = null,
    val weather: List<WeatherX>?= ArrayList(),
) {
    data class Main(
        val feels_like: Double? = 0.0,
        val humidity: Int? = 0,
        val pressure: Int? = 0,
        val temp: Double? = 0.0,
        val temp_max: Double? = 0.0,
        val temp_min: Double? = 0.0
    )
    
    data class Sys(
        val country: String? = "",
        val id: Int? = 0,
        val sunrise: Int? = 0,
        val sunset: Int? = 0,
        val type: Int? = 0
    )

    data class WeatherX(
        val description:  String? = "",
        val icon: String? = "",
        val id: Int? = 0,
        val main: String? = ""
    )
}

