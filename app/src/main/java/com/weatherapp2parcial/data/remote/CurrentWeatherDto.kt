package com.weatherapp2parcial.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("name") val cityName: String
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("humidity") val humidity: Int
)

@Serializable
data class Weather(
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)
