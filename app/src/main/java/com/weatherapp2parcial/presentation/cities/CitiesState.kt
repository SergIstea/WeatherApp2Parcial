package com.weatherapp2parcial.presentation.cities
import com.weatherapp2parcial.data.remote.CityDto


data class CitiesState(
    val cities: List<CityDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

