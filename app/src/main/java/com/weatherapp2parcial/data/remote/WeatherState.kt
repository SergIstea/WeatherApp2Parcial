package com.weatherapp2parcial.data.remote

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: CurrentWeatherDto? = null,
    val error: String? = null
)