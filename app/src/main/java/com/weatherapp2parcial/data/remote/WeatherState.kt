package com.weatherapp2parcial.data.remote

import com.weatherapp2parcial.data.remote.ForecastEntry

data class WeatherState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val weather: CurrentWeatherDto? = null,
    val forecast: List<ForecastEntry> = emptyList()
)