package com.weatherapp2parcial.data.remote

sealed class WeatherIntent {
    data class LoadWeather(val lat: Double, val lon: Double) : WeatherIntent()
    data class LoadForecast(val lat: Double, val lon: Double) : WeatherIntent() // ← este es nuevo
}
