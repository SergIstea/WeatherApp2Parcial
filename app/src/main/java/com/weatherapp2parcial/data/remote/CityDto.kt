package com.weatherapp2parcial.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)