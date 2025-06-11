package com.weatherapp2parcial.data.remote

sealed class WeatherIntent {
    data class LoadWeather(val lat: Double, val lon: Double) : WeatherIntent()
}